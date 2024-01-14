/**
 * Copyright (c) 2017-2018 THL A29 Limited, a Tencent company. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.xielong.aihub.adapter.tencent.thirdparty;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Slf4j
public class HyService {

    private static final String SIGN_HOST = "hunyuan.cloud.tencent.com";
    private static final String SIGN_PATH = "hyllm/v1/chat/completions";
    private static final String URL = "https://hunyuan.cloud.tencent.com/hyllm/v1/chat/completions";

    private final long appId;

    private final String secretId;

    private final String secretKey;
    public String signature;
    private HyRequest requestParam;

    public HyService(long appId, String secretId, String secretKey) {
        this.appId = appId;
        this.secretId = secretId;
        this.secretKey = secretKey;
    }

    /**
     * 生成请求体
     *
     * @param messages     内容
     * @param enableStream 是否流式 1：流式 0：同步
     * @return HyService
     */
    public HyService genHyRequest(Message[] messages, int enableStream) {
        if (messages == null) {
            return this;
        }
        long timestamp = System.currentTimeMillis() / 1000 + 10000;
        HyRequest requestParam = new HyRequest();
        requestParam.setAppId(this.appId);
        requestParam.setSecretId(this.secretId);
        requestParam.setTemperature(1.0f);
        requestParam.setTopP(1.0f);
        requestParam.setStream(enableStream);
        requestParam.setTimestamp(timestamp);
        requestParam.setExpired(timestamp + 24 * 60 * 60);
        requestParam.setQueryId(UUID.randomUUID().toString());
        requestParam.setMessages(messages);
        this.requestParam = requestParam;
        return this;
    }

    /**
     * 签名方法
     *
     * @return HyService
     */
    public HyService sign() {
        if (this.requestParam != null) {
            TreeMap<String, String> params = new TreeMap<>();
            params.put("app_id", String.valueOf(this.requestParam.getAppId()));
            params.put("secret_id", String.valueOf(this.requestParam.getSecretId()));
            params.put("query_id", String.valueOf(this.requestParam.getQueryId()));
            params.put("temperature",
                    BigDecimal.valueOf(this.requestParam.getTemperature()).stripTrailingZeros().toPlainString());
            params.put("top_p", BigDecimal.valueOf(this.requestParam.getTopP()).stripTrailingZeros().toPlainString());
            params.put("stream", String.valueOf(this.requestParam.getStream()));
            Message[] messages = this.requestParam.getMessages();
            StringBuilder messageStr = new StringBuilder();
            for (Message message : messages) {
                String content = message.getContent();
                messageStr.append("{\"role\":\"").append(message.getRole()).
                        append("\",\"content\":\"").append(content).append("\"},");
            }
            messageStr.deleteCharAt(messageStr.length() - 1);
            params.put("messages", "[" + messageStr + "]");
            params.put("timestamp", String.valueOf(this.requestParam.getTimestamp()));
            params.put("expired", String.valueOf(this.requestParam.getExpired()));
            String signStr = SIGN_HOST + "/" + SIGN_PATH + "?";
            for (String key : params.keySet()) {
                signStr += key + "=" + params.get(key) + "&";
            }
            signStr = signStr.substring(0, signStr.length() - 1);
            this.signature = SignUtil.base64_hmac_sha1(signStr, this.secretKey);
        }
        return this;
    }

    /**
     * 处理请求
     *
     * @return HyService
     */
    public String handle() {
        String output = "";
        if (this.signature == null || this.requestParam == null) {
            return output;
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", this.signature);
        log.info("Input:\n" + URL + " | " + headers + " | " + this.requestParam);
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL).openConnection();
            connection.setRequestMethod("POST");
            headers.forEach(connection::setRequestProperty);
            connection.setDoOutput(true);
            connection.getOutputStream().write(new Gson().toJson(this.requestParam).getBytes(StandardCharsets.UTF_8));

            log.info("Output:");
            if (this.requestParam.getStream() == 1) {
                output = handleResponseStream(connection);
            } else {
                output = handleResponse(connection);
            }
            connection.disconnect();
        } catch (IOException e) {
            log.error("Failed to post method", e);
        }
        return output;
    }

    /**
     * 处理同步结果
     *
     * @param connection connection
     * @return 结果
     * @throws IOException
     */
    private String handleResponse(HttpURLConnection connection) throws IOException {
        String output = "";
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            GsonBuilder builder = new GsonBuilder();
            builder.serializeNulls();
            HyResponse hyresponse = builder.create().fromJson(response.toString(), HyResponse.class);
            if (hyresponse != null) {
                if (hyresponse.getError() == null) {
                    for (HyResponseChoice c : hyresponse.getChoices()) {
                        log.info(c.getMessages().getContent());
                        output += c.getMessages().getContent();
                    }
                } else {
                    log.error("Error occurs", hyresponse.getError());
                }
            }
        } else {
            log.error("Error response code: " + responseCode);
        }
        return output;
    }

    /**
     * 处理流式结果
     *
     * @param connection connection
     * @return 结果
     */
    private String handleResponseStream(HttpURLConnection connection) {
        String output = "";
        try {
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    if ("".equals(line)) {
                        continue;
                    }
                    GsonBuilder builder = new GsonBuilder();
                    builder.serializeNulls();
                    HyResponse hyresponse = builder.create()
                            .fromJson(StringUtils.replace(line, "data: ", ""), HyResponse.class);
                    if (hyresponse != null) {
                        if (hyresponse.getError() == null) {
                            for (HyResponseChoice c : hyresponse.getChoices()) {
                                log.info(c.getDelta().getContent());
                                output += c.getDelta().getContent();
                                if ("stop".equals(c.getFinishReason())) {
                                    break;
                                }
                            }
                        } else {
                            log.error("Error: ", hyresponse.getError());
                        }
                    }
                }
                reader.close();
            } else {
                log.error("Error: " + responseCode);
            }
        } catch (IOException e) {
            log.error("Error: " + e);
        }
        return output;
    }
}

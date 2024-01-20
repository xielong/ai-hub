package com.github.xielong.aihub.adapter.baidu;

import com.github.xielong.aihub.adapter.openai.ChatCompletionRequest;
import com.github.xielong.aihub.adapter.openai.OpenAIInvoker;
import com.github.xielong.aihub.dao.Credential;
import com.github.xielong.aihub.dao.CredentialMapper;
import com.github.xielong.aihub.util.AIProvider;
import com.github.xielong.aihub.util.HttpClientWithRetry;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Service
@Slf4j
public class BaiduInvoker extends OpenAIInvoker {

    private static final String MODEL_ERNIE_BOT_4 = "ERNIE-Bot-4";
    private static final String MODEL_ERNIE_BOT_TURBO = "ERNIE-Bot-turbo";
    private static final String SECURITY_CREDENTIAL_KEY_API_KEY = "apiKey";
    private static final String SECURITY_CREDENTIAL_KEY_SECRET_KEY = "secretKey";
    private static final String PROVIDER_DOMAIN = "https://aip.baidubce.com/rpc/2.0/ai_custom";

    private final Gson gson = new Gson();
    @Autowired
    private CredentialMapper apiCredentialMapper;
    @Autowired
    private HttpClientWithRetry httpClientWithRetry;

    @Override
    protected HttpRequest createRequest(String model, String input) {
        String accessToken = getAccessToken();

        String requestBody = createRequestBody(model, input);

        String path = "";
        if (MODEL_ERNIE_BOT_4.equalsIgnoreCase(model)) {
            path = "/v1/wenxinworkshop/chat/completions_pro";
        } else if (MODEL_ERNIE_BOT_TURBO.equalsIgnoreCase(model)) {
            path = "/v1/wenxinworkshop/chat/eb-instant";
        }

        return HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create(URI.create(PROVIDER_DOMAIN) + path +
                        "?access_token=" + accessToken))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

    @Override
    protected String createRequestBody(String model, String input) {
        ChatCompletionRequest.Message userMessage = ChatCompletionRequest.Message.builder()
                .role("user")
                .content(input)
                .build();

        List<ChatCompletionRequest.Message> messages = List.of(userMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(model)
                .messages(messages)
                .temperature(0.8)
                .stream(true)
                .build();

        return gson.toJson(chatCompletionRequest);
    }

    private String getAccessToken() {
        Credential apiKeyCredential = apiCredentialMapper
                .findByProviderAndKey(AIProvider.BAIDU.getId(), SECURITY_CREDENTIAL_KEY_API_KEY);
        Credential secretKeyCredential = apiCredentialMapper
                .findByProviderAndKey(AIProvider.BAIDU.getId(), SECURITY_CREDENTIAL_KEY_SECRET_KEY);

        return fetchToken(apiKeyCredential.getValue(), secretKeyCredential.getValue());
    }

    private String fetchToken(String apiKey, String secretKey) {
        HttpRequest request = buildFetchTokenRequest(apiKey, secretKey);

        try {
            HttpResponse<String> response = httpClientWithRetry.execute(request);
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
            String accessToken = "access_token";
            return jsonResponse.has(accessToken) ? jsonResponse.get(accessToken).getAsString() : null;
        } catch (Exception e) {
            log.error("Failed to fetch token, the error is: ", e);
            return null;
        }
    }

    private HttpRequest buildFetchTokenRequest(String apiKey, String secretKey) {
        String url = "https://aip.baidubce.com/oauth/2.0/token";
        String params = "grant_type=client_credentials"
                + "&client_id=" + URLEncoder.encode(apiKey, StandardCharsets.UTF_8)
                + "&client_secret=" + URLEncoder.encode(secretKey, StandardCharsets.UTF_8);
        return HttpRequest.newBuilder()
                .uri(URI.create(url + "?" + params))
                .header("Content-Type", "application/json")
                .GET()
                .build();
    }

    @Override
    protected String extractContentFromLine0(String line) {
        ChatCompletionResponse chatCompletionResponse =
                new Gson().fromJson(line.substring("data: ".length()), ChatCompletionResponse.class);
        return chatCompletionResponse.getResult();
    }

}

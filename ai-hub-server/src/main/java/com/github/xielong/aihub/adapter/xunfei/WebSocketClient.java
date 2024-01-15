package com.github.xielong.aihub.adapter.xunfei;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
public class WebSocketClient extends WebSocketListener {

    private static final int STATUS_CLOSE_SUCCESS = 1000;

    private final CountDownLatch latch = new CountDownLatch(1);
    private final String appId;
    private String receivedMessage = "";

    public WebSocketClient(String appId) {
        this.appId = appId;
    }

    public String sendMessageAndWait(String url, String input) throws InterruptedException, TimeoutException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        WebSocket ws = client.newWebSocket(request, this);

        String requestBody = buildRequest(input);

        ws.send(requestBody);

        if (!latch.await(60, TimeUnit.SECONDS)) {
            throw new TimeoutException("Response not received within " + 60 + " seconds");
        }

        ws.close(STATUS_CLOSE_SUCCESS, "success");

        return receivedMessage;
    }

    private String buildRequest(String input) {
        Gson gson = new Gson();

        ChatCompletionRequest request = new ChatCompletionRequest();

        ChatCompletionRequest.Header header = new ChatCompletionRequest.Header();
        header.setAppId(appId);
        header.setUid(UUID.randomUUID().toString().substring(0, 10));
        request.setHeader(header);

        ChatCompletionRequest.Parameter parameter = new ChatCompletionRequest.Parameter();
        ChatCompletionRequest.Parameter.Chat chat = new ChatCompletionRequest.Parameter.Chat();
        chat.setDomain("generalv2");
        chat.setTemperature(0.5);
        chat.setMaxTokens(4096);
        parameter.setChat(chat);
        request.setParameter(parameter);

        ChatCompletionRequest.Payload payload = new ChatCompletionRequest.Payload();
        ChatCompletionRequest.Payload.Message message = new ChatCompletionRequest.Payload.Message();
        JsonArray text = new JsonArray();

        ChatCompletionRequest.RoleContent roleContent = new ChatCompletionRequest.RoleContent();
        roleContent.setRole("user");
        roleContent.setContent(input);
        text.add(gson.toJsonTree(roleContent));

        message.setText(text);
        payload.setMessage(message);
        request.setPayload(payload);

        return gson.toJson(request);

    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        ChatCompletionResponse response = new Gson().fromJson(text, ChatCompletionResponse.class);
        ChatCompletionResponse.Header header = response.getHeader();
        if (header.getCode() != 0) {
            log.error("Failed to read message, code: " + header.getCode() + ", sid: " + header.getSid());
            webSocket.close(STATUS_CLOSE_SUCCESS, "");
        }
        List<ChatCompletionResponse.Text> textList = response.getPayload().getChoices().getText();
        for (ChatCompletionResponse.Text txt : textList) {
            receivedMessage = receivedMessage + txt.getContent();
        }
        if (header.getStatus() == ChatCompletionResponse.STATUS_COMPLETE) {
            latch.countDown();
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        if (null != response) {
            int code = response.code();
            log.error("onFailure code:" + code + ", body" + response.body());
            if (101 != code) {
                log.error("connection failed");
            }
        }
    }

}


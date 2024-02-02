package com.github.xielong.aihub.adapter.minimax;

import com.github.xielong.aihub.adapter.AIModelInvoker;
import com.github.xielong.aihub.dao.Credential;
import com.github.xielong.aihub.dao.CredentialMapper;
import com.github.xielong.aihub.util.AIProvider;
import com.google.gson.Gson;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class MiniMaxInvoker implements AIModelInvoker {
    private static final String SECURITY_CREDENTIAL_KEY_GROUP_ID = "groupId";
    private static final String SECURITY_CREDENTIAL_KEY_API_KEY = "apiKey";
    private final Gson gson = new Gson();
    private OkHttpClient client = new OkHttpClient();
    @Autowired
    private CredentialMapper apiCredentialMapper;

    public MiniMaxInvoker() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public ChatCompletionResponse sendChatRequest(
            ChatCompletionRequest request,
            String groupId,
            String apiKey) throws IOException {
        String url = "https://api.minimax.chat/v1/text/chatcompletion_pro?GroupId=" + groupId;
        RequestBody body = RequestBody.create(
                MediaType.get("application/json; charset=utf-8"),
                gson.toJson(request)
        );

        Request httpRequest = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        try (Response response = client.newCall(httpRequest).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return gson.fromJson(response.body().string(), ChatCompletionResponse.class);
        }
    }

    @Override
    public String invoke(String model, String input) throws Exception {
        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest();
        chatCompletionRequest.setBot_setting(List.of(new ChatCompletionRequest.BotSetting(
                "MM智能助理", "MM智能助理是一款由MiniMax自研的，没有调用其他产品的接口的大型语言模型。MiniMax是一家中国科技公司，一直致力于进行大模型相关的研究。"
        )));
        chatCompletionRequest.setModel(model);
        chatCompletionRequest.setTokens_to_generate(2068);
        chatCompletionRequest.setTemperature(0.01);
        chatCompletionRequest.setTop_p(0.95);
        chatCompletionRequest.setReply_constraints(new ChatCompletionRequest.ReplyConstraints(
                "BOT", "MM智能助理"
        ));
        chatCompletionRequest.setMessages(List.of(new ChatCompletionRequest.Message(
                "USER", "小明", input
        )));

        Credential groupIdCredential = apiCredentialMapper.
                findByProviderAndKey(AIProvider.MINIMAX.getId(), SECURITY_CREDENTIAL_KEY_GROUP_ID);
        Credential apiKeyCredential = apiCredentialMapper.
                findByProviderAndKey(AIProvider.MINIMAX.getId(), SECURITY_CREDENTIAL_KEY_API_KEY);

        return sendChatRequest(chatCompletionRequest, groupIdCredential.getValue(),
                apiKeyCredential.getValue()).getReply();

    }

}

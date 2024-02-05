package com.github.xielong.aihub.adapter.moonshot;

import com.github.xielong.aihub.adapter.openai.ChatCompletionRequest;
import com.github.xielong.aihub.adapter.openai.OpenAIInvoker;
import com.github.xielong.aihub.dao.Credential;
import com.github.xielong.aihub.dao.CredentialMapper;
import com.github.xielong.aihub.util.AIProvider;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoonshotInvoker extends OpenAIInvoker {

    private static final String PROVIDER_DOMAIN = "https://api.moonshot.cn";
    private static final String SECURITY_CREDENTIAL_API_KEY = "apiKey";

    private final Gson gson = new Gson();
    @Autowired
    private CredentialMapper apiCredentialMapper;

    @Override
    protected String getProviderDomain() {
        return PROVIDER_DOMAIN;
    }

    @Override
    protected Credential getCredential() {
        return apiCredentialMapper
                .findByProviderAndKey(AIProvider.MOONSHOT.getId(), SECURITY_CREDENTIAL_API_KEY);
    }

    @Override
    protected String createRequestBody(String model, String input) {
        ChatCompletionRequest.Message systemMessage = ChatCompletionRequest.Message.builder()
                .role("system")
                .content("你是 Kimi，由 Moonshot AI 提供的人工智能助手，你更擅长中文和英文的对话。")
                .build();
        ChatCompletionRequest.Message userMessage = ChatCompletionRequest.Message.builder()
                .role("user")
                .content(input)
                .build();

        List<ChatCompletionRequest.Message> messages = List.of(systemMessage, userMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(model)
                .messages(messages)
                .stream(true)
                .build();

        return gson.toJson(chatCompletionRequest);
    }
}

package com.github.xielong.aihub.adapter.lingyi;

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
public class LingyiInvoker extends OpenAIInvoker {

    private static final String PROVIDER_DOMAIN = "https://api.lingyiwanwu.com";

    private static final String SECURITY_CREDENTIAL_KEY_API_KEY = "apiKey";
    private static final Gson GSON = new Gson();
    @Autowired
    private CredentialMapper apiCredentialMapper;

    @Override
    protected String getProviderDomain() {
        return PROVIDER_DOMAIN;
    }

    @Override
    protected Credential getCredential() {
        return apiCredentialMapper
                .findByProviderAndKey(AIProvider.LINGYI.getId(), SECURITY_CREDENTIAL_KEY_API_KEY);
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
                .temperature(0.7)
                .stream(true)
                .build();

        return GSON.toJson(chatCompletionRequest);
    }
}
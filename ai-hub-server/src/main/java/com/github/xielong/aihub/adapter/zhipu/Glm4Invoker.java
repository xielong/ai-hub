package com.github.xielong.aihub.adapter.zhipu;

import com.github.xielong.aihub.adapter.openai.ChatCompletionRequest;
import com.github.xielong.aihub.adapter.openai.ChatCompletionResponse;
import com.github.xielong.aihub.util.HttpClientWithRetry;
import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class Glm4Invoker {

    private static final String PROVIDER_DOMAIN = "https://open.bigmodel.cn";
    private static final Gson GSON = new Gson();
    @Autowired
    private HttpClientWithRetry httpClientWithRetry;

    public String invokeGlm4(String credential, String input) throws Exception {
        HttpRequest request = createRequest(credential, input);
        HttpResponse<String> response = httpClientWithRetry.execute(request);
        return processAndExtractContent(response.body());
    }

    protected HttpRequest createRequest(String credential, String input) {

        String token = TokenGenerator.generateToken(credential, 60);
        String requestBody = createRequestBody(ZhipuInvoker.MODEL_GLM_4, input);
        return HttpRequest.newBuilder()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header("Content-Type", "application/json")
                .uri(URI.create(URI.create(getProviderDomain()) + "/api/paas/v4/chat/completions"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

    protected String getProviderDomain() {
        return PROVIDER_DOMAIN;
    }

    protected String createRequestBody(String model, String input) {
        ChatCompletionRequest.Message userMessage = ChatCompletionRequest.Message.builder()
                .role("user")
                .content(input)
                .build();

        List<ChatCompletionRequest.Message> messages = List.of(userMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(model)
                .messages(messages)
                .stream(true)
                .build();

        return GSON.toJson(chatCompletionRequest);
    }

    private String processAndExtractContent(String responseBody) throws IOException {

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new StringReader(responseBody))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Optional<String> extractedContent = extractContentFromLine(line);
                extractedContent.ifPresent(content::append);
            }
        }
        return content.toString();
    }


    private Optional<String> extractContentFromLine(String line) {
        String dataPrefix = "data: {\"id\"";

        if (!line.startsWith(dataPrefix)) {
            return Optional.empty();
        }
        String content = extractContentFromLine0(line);
        return Optional.ofNullable(content);
    }

    protected String extractContentFromLine0(String line) {
        ChatCompletionResponse chatCompletionResponse =
                GSON.fromJson(line.substring("data: ".length()), ChatCompletionResponse.class);
        return chatCompletionResponse.getChoices().get(0).getDelta().getContent();
    }
}

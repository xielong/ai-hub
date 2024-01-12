package com.github.xielong.aihub.adapter.openai;

import com.github.xielong.aihub.adapter.AIModelInvoker;
import com.github.xielong.aihub.dao.Credential;
import com.github.xielong.aihub.dao.CredentialMapper;
import com.github.xielong.aihub.util.AIProvider;
import com.github.xielong.aihub.util.HttpClientWithRetry;
import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
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
@Primary
public class OpenAIInvoker implements AIModelInvoker {

    protected static final String SECURITY_CREDENTIAL_KEY_TOKEN = "token";
    private static final String PROVIDER_DOMAIN = "https://api.openai.com";
    private final Gson gson = new Gson();
    @Autowired
    private CredentialMapper apiCredentialMapper;

    @Autowired
    private HttpClientWithRetry httpClientWithRetry;

    @Override
    public String invoke(String model, String input) throws Exception {
        HttpRequest request = createRequest(model, input);
        HttpResponse<String> response = httpClientWithRetry.execute(request);
        return processAndExtractContent(response.body());
    }

    private HttpRequest createRequest(String model, String input) {
        Credential apiCredential = getCredential();

        String requestBody = createRequestBody(model, input);

        return HttpRequest.newBuilder()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiCredential.getValue())
                .header("Content-Type", "application/json")
                .uri(URI.create(URI.create(getProviderDomain()) + "/v1/chat/completions"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

    protected String getProviderDomain() {
        return PROVIDER_DOMAIN;
    }

    protected Credential getCredential() {
        return apiCredentialMapper
                .findByProviderAndKey(AIProvider.OPENAI.getId(), SECURITY_CREDENTIAL_KEY_TOKEN);
    }

    private String createRequestBody(String model, String input) {
        ChatCompletionRequest.Message systemMessage = ChatCompletionRequest.Message.builder()
                .role("system")
                .content("")
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
        ChatCompletionResponse chatCompletionResponse =
                gson.fromJson(line.substring("data: ".length()), ChatCompletionResponse.class);
        String content = chatCompletionResponse.getChoices().get(0).getDelta().getContent();
        return Optional.ofNullable(content);
    }


}

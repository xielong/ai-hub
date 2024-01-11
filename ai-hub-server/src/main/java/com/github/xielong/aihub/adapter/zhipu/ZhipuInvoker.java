package com.github.xielong.aihub.adapter.zhipu;

import com.github.xielong.aihub.adapter.AIModelInvoker;
import com.github.xielong.aihub.dao.Credential;
import com.github.xielong.aihub.dao.CredentialMapper;
import com.github.xielong.aihub.util.AIProvider;
import com.google.gson.Gson;
import com.zhipu.oapi.ClientV3;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v3.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class ZhipuInvoker implements AIModelInvoker {

    private static final String SECURITY_CREDENTIAL_KEY_TOKEN = "apiSecretKey";

    @Autowired
    private CredentialMapper apiCredentialMapper;

    @Override
    public String invoke(String model, String input) throws Exception {

        Credential credential = apiCredentialMapper
                .findByProviderAndKey(AIProvider.ZHIPU.getId(), SECURITY_CREDENTIAL_KEY_TOKEN);

        ClientV3 client = new ClientV3.Builder(credential.getValue()).build();

        ModelApiRequest modelApiRequest = new ModelApiRequest();
        modelApiRequest.setModelId(Constants.ModelChatGLMTRUBO);
        modelApiRequest.setInvokeMethod(Constants.invokeMethodSse);

        StandardEventSourceListener listener = new StandardEventSourceListener();
        listener.setIncremental(false);
        modelApiRequest.setSseListener(listener);
        modelApiRequest.setIncremental(false);

        ModelApiRequest.Prompt prompt = new ModelApiRequest.Prompt(ModelConstants.roleUser, input);
        List<ModelApiRequest.Prompt> prompts = new ArrayList<>();
        prompts.add(prompt);
        modelApiRequest.setPrompt(prompts);
        modelApiRequest.setRequestId(UUID.randomUUID().toString());

        ModelApiResponse sseModelApiResp = client.invokeModelApi(modelApiRequest);
        log.info("Call to model API completed. Method: {}, Request ID: {}",
                modelApiRequest.getInvokeMethod(), modelApiRequest.getRequestId());
        String content = sseModelApiResp.getData().getChoices().get(0).getContent();
        log.debug("Model output: {}", sseModelApiResp.getData().getChoices().get(0).getContent());
        log.info("Usage info: {}", new Gson().toJson(sseModelApiResp.getData().getUsage(), Usage.class));
        log.info("Task ID: {}", sseModelApiResp.getData().getTaskId());

        return content;

    }
}

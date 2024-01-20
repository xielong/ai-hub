package com.github.xielong.aihub.adapter.zhipu;

import com.google.gson.Gson;
import com.zhipu.oapi.ClientV3;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v3.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ChatglmTurboInvoker {
    public String invokeChatglmTurbo(String credential, String input) {
        ClientV3 client = new ClientV3.Builder(credential).build();

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
        log.debug("Model output: {}", sseModelApiResp.getData().getChoices().get(0).getContent());
        log.info("Usage info: {}", new Gson().toJson(sseModelApiResp.getData().getUsage(), Usage.class));
        log.info("Task ID: {}", sseModelApiResp.getData().getTaskId());
        return sseModelApiResp.getData().getChoices().get(0).getContent();
    }
}

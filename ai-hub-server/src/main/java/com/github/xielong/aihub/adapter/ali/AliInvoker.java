package com.github.xielong.aihub.adapter.ali;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.utils.Constants;
import com.alibaba.dashscope.utils.JsonUtils;
import com.github.xielong.aihub.adapter.AIModelInvoker;
import com.github.xielong.aihub.dao.Credential;
import com.github.xielong.aihub.dao.CredentialMapper;
import com.github.xielong.aihub.util.AIProvider;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AliInvoker implements AIModelInvoker {

    private static final String SECURITY_CREDENTIAL_KEY_TOKEN = "apiKey";

    @Autowired
    private CredentialMapper apiCredentialMapper;

    @Override
    public String invoke(String model, String input) throws Exception {
        Credential credential = apiCredentialMapper.
                findByProviderAndKey(AIProvider.ALI.getId(), SECURITY_CREDENTIAL_KEY_TOKEN);

        Constants.apiKey = credential.getValue();

        Generation gen = new Generation();
        QwenParam param = QwenParam.builder().model(model).prompt(input)
                .topP(0.8).build();
        GenerationResult result = gen.call(param);

        ChatCompletionResponse response = new Gson().fromJson(JsonUtils.toJson(result),
                ChatCompletionResponse.class);

        return response.getOutput().getText();


    }
}

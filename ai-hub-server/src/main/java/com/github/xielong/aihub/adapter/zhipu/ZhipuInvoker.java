package com.github.xielong.aihub.adapter.zhipu;

import com.github.xielong.aihub.adapter.AIModelInvoker;
import com.github.xielong.aihub.dao.Credential;
import com.github.xielong.aihub.dao.CredentialMapper;
import com.github.xielong.aihub.util.AIProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ZhipuInvoker implements AIModelInvoker {

    public static final String MODEL_GLM_4 = "glm-4";
    private static final String SECURITY_CREDENTIAL_KEY_TOKEN = "apiSecretKey";
    private static final String MODEL_CHATGLM_TURBO = "chatGLM_turbo";
    @Autowired
    private CredentialMapper apiCredentialMapper;

    @Autowired
    private ChatglmTurboInvoker chatglmTurboInvoker;

    @Autowired
    private Glm4Invoker glm4Invoker;

    @Override
    public String invoke(String model, String input) throws Exception {
        Credential credential = apiCredentialMapper
                .findByProviderAndKey(AIProvider.ZHIPU.getId(), SECURITY_CREDENTIAL_KEY_TOKEN);
        if (MODEL_CHATGLM_TURBO.equalsIgnoreCase(model)) {
            return chatglmTurboInvoker.invokeChatglmTurbo(credential.getValue(), input);
        }
        if (MODEL_GLM_4.equalsIgnoreCase(model)) {
            return glm4Invoker.invokeGlm4(credential.getValue(), input);
        }
        throw new IllegalArgumentException("Unsupported model: " + model);

    }


}

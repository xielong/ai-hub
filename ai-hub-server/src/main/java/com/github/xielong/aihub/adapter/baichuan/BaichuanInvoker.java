package com.github.xielong.aihub.adapter.baichuan;

import com.github.xielong.aihub.adapter.openai.OpenAIInvoker;
import com.github.xielong.aihub.dao.Credential;
import com.github.xielong.aihub.dao.CredentialMapper;
import com.github.xielong.aihub.util.AIProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaichuanInvoker extends OpenAIInvoker {

    private static final String PROVIDER_DOMAIN = "https://api.baichuan-ai.com";
    @Autowired
    private CredentialMapper apiCredentialMapper;

    @Override
    protected String getProviderDomain() {
        return PROVIDER_DOMAIN;
    }

    @Override
    protected Credential getCredential() {
        return apiCredentialMapper
                .findByProviderAndKey(AIProvider.BAICHUAN.getId(), SECURITY_CREDENTIAL_KEY_TOKEN);
    }
}

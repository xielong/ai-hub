package com.github.xielong.aihub.adapter.tencent;

import com.github.xielong.aihub.adapter.AIModelInvoker;
import com.github.xielong.aihub.adapter.tencent.thirdparty.HyService;
import com.github.xielong.aihub.adapter.tencent.thirdparty.Message;
import com.github.xielong.aihub.dao.Credential;
import com.github.xielong.aihub.dao.CredentialMapper;
import com.github.xielong.aihub.util.AIProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TencentInvoker implements AIModelInvoker {

    private static final String SECURITY_CREDENTIAL_KEY_APP_ID = "appId";
    private static final String SECURITY_CREDENTIAL_KEY_SECRET_ID = "secretId";
    private static final String SECURITY_CREDENTIAL_KEY_SECRET_KEY = "secretKey";

    @Autowired
    private CredentialMapper apiCredentialMapper;

    @Override
    public String invoke(String model, String input) throws Exception {
        Credential appIdCredential = apiCredentialMapper.
                findByProviderAndKey(AIProvider.TENCENT.getId(), SECURITY_CREDENTIAL_KEY_APP_ID);
        Credential secretIdCredential = apiCredentialMapper.
                findByProviderAndKey(AIProvider.TENCENT.getId(), SECURITY_CREDENTIAL_KEY_SECRET_ID);
        Credential secretKeyCredential = apiCredentialMapper.
                findByProviderAndKey(AIProvider.TENCENT.getId(), SECURITY_CREDENTIAL_KEY_SECRET_KEY);

        int enableStream = 1;
        Message[] messages = {new Message("user", input)};
        HyService hyService = new HyService(Long.parseLong(appIdCredential.getValue()),
                secretIdCredential.getValue(), secretKeyCredential.getValue())
                .genHyRequest(messages, enableStream).sign();
        return hyService.handle();

    }


}

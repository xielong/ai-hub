package com.github.xielong.aihub.adapter.bytedance;

import com.github.xielong.aihub.adapter.AIModelInvoker;
import com.github.xielong.aihub.dao.CredentialMapper;
import com.github.xielong.aihub.util.AIProvider;
import com.volcengine.helper.Const;
import com.volcengine.model.maas.api.Api;
import com.volcengine.service.maas.MaasService;
import com.volcengine.service.maas.impl.MaasServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ByteDanceInvoker implements AIModelInvoker {

    private static final String SECURITY_CREDENTIAL_KEY_ACCESS_KEY = "accessKey";
    private static final String SECURITY_CREDENTIAL_KEY_SECRET_KEY = "secretKey";
    private static final String HOST = "maas-api.ml-platform-cn-beijing.volces.com";
    private static final String REGION = "cn-beijing";

    @Autowired
    private CredentialMapper apiCredentialMapper;

    @Override
    public String invoke(String model, String input) throws Exception {

        String accessKey = apiCredentialMapper.findByProviderAndKey(AIProvider.BYTEDANCE.getId(),
                SECURITY_CREDENTIAL_KEY_ACCESS_KEY).getValue();
        String secretKey = apiCredentialMapper.findByProviderAndKey(AIProvider.BYTEDANCE.getId(),
                SECURITY_CREDENTIAL_KEY_SECRET_KEY).getValue();

        MaasService maasService = new MaasServiceImpl(HOST, REGION);

        maasService.setAccessKey(accessKey);
        maasService.setSecretKey(secretKey);

        Api.ChatReq req = Api.ChatReq.newBuilder()
                .setModel(Api.Model.newBuilder()
                        .setName("skylark-chat")
                )
                .setParameters(Api.Parameters.newBuilder()
                        .setMaxNewTokens(2000)
                        .setTemperature(1)
                )
                .addMessages(Api.Message.newBuilder().setRole(Const.MaasChatRoleOfUser).setContent(input))
                .build();

        return maasService.streamChat(req).map(resp -> resp.getChoice().getMessage().getContent())
                .collect(Collectors.joining());
    }


}

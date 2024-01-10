package com.github.xielong.aihub.service;

import com.github.xielong.aihub.dao.Credential;
import com.github.xielong.aihub.dao.CredentialMapper;
import com.github.xielong.aihub.util.AIProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    @Autowired
    private CredentialMapper apiCredentialMapper;

    public List<Credential> findCredentialsByProvider(String providerName) {
        return apiCredentialMapper.findByProvider(AIProvider.fromName(providerName).getId());
    }

    public List<Credential> findAllCredentials() {
        return apiCredentialMapper.findAll();
    }

    public void addOrUpdateCredentials(String providerName, String key, String value) {
        Credential credentialInDb = apiCredentialMapper
                .findByProviderAndKey(AIProvider.fromName(providerName).getId(), key);
        Credential credential = new Credential();
        credential.setProvider(AIProvider.fromName(providerName).getId());
        credential.setKey(key);
        credential.setValue(value);
        if (credentialInDb == null) {
            apiCredentialMapper.insert(credential);
        } else {
            apiCredentialMapper.update(credential);
        }

    }
}



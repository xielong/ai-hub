package com.github.xielong.aihub.service;

import com.github.xielong.aihub.dao.ApiCredential;
import com.github.xielong.aihub.dao.ApiCredentialMapper;
import com.github.xielong.aihub.util.AIProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiCredentialService {

    @Autowired
    private ApiCredentialMapper apiCredentialMapper;


    public List<ApiCredential> findCredentialsByProvider(String providerName) {
        return apiCredentialMapper.findByProvider(AIProvider.fromName(providerName).getId());
    }

    public List<ApiCredential> findAllCredentials() {
        return apiCredentialMapper.findAll();
    }
}



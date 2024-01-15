package com.github.xielong.aihub.service;

import com.github.xielong.aihub.dao.Credential;
import com.github.xielong.aihub.util.AIProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CredentialServiceTests {

    @Autowired
    private CredentialService apiCredentialService;

    @Test
    @Sql({"classpath:db/initial.sql"})
    public void findOpenAICredentials() {
        List<Credential> apiCredentials = apiCredentialService
                .findCredentialsByProvider(AIProvider.OPENAI.getName());
        assertEquals(1, apiCredentials.size());
        Credential apiCredential = apiCredentials.get(0);
        assertEquals("token", apiCredential.getKey());
        assertEquals("gpt_token_value", apiCredential.getValue());
    }

    @Test
    @Sql({"classpath:db/initial.sql"})
    public void findAllCredentials() {
        List<Credential> apiCredentials = apiCredentialService.findAllCredentials();
        assertEquals(7, apiCredentials.size());
    }

    @Test
    @Sql({"classpath:db/initial.sql"})
    public void addOrUpdateCredentials() {
        apiCredentialService.addOrUpdateCredentials(AIProvider.OPENAI.getName(), "key2", "value2");
        List<Credential> apiCredentials = apiCredentialService.findCredentialsByProvider(AIProvider.OPENAI.getName());
        assertEquals(2, apiCredentials.size());

        apiCredentialService.addOrUpdateCredentials(AIProvider.ALI.getName(), "key1", "value1");
        List<Credential> apiCredentials2 = apiCredentialService.findCredentialsByProvider(AIProvider.ALI.getName());
        assertEquals(1, apiCredentials2.size());

    }


}


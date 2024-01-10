package com.github.xielong.aihub.adapter;

import com.github.xielong.aihub.adapter.openai.OpenAIInvoker;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class OpenAIInvokerTests {

    @Autowired
    private OpenAIInvoker openAIInvoker;

    //@Test
    @Sql({"classpath:db/initial.sql"})
    public void invokeModel() throws Exception {
        String output = openAIInvoker.invoke("gpt-3.5-turbo", "Introduce yourself");
        assertNotNull("Model invocation should return a non-null output", output);
    }
}

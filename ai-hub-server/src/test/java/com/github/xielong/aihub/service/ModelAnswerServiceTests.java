package com.github.xielong.aihub.service;


import com.github.xielong.aihub.dao.ModelAnswer;
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
public class ModelAnswerServiceTests {

    @Autowired
    private ModelAnswerService modelAnswerService;

    @Test
    @Sql({"classpath:db/initial.sql"})
    public void findQuestions() {
        List<ModelAnswer> allQuestions = modelAnswerService.findQuestions(1);
        assertEquals(5, allQuestions.size());
    }
}

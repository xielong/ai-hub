package com.github.xielong.aihub.service;

import com.github.xielong.aihub.dao.ModelEvaluation;
import com.github.xielong.aihub.util.EvaluationScenario;
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
public class EvaluationServiceTests {

    @Autowired
    private EvaluationService evaluationService;

    @Test
    @Sql({"classpath:db/initial.sql"})
    public void getEvaluationResults() {
        List<ModelEvaluation> modelEvaluations = evaluationService.getEvaluationResults(EvaluationScenario.TRANSLATION.getId());
        assertEquals(2, modelEvaluations.size());
    }
}

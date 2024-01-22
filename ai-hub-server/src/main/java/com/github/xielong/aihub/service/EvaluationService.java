package com.github.xielong.aihub.service;

import com.github.xielong.aihub.dao.ModelEvaluation;
import com.github.xielong.aihub.dao.ModelEvaluationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationService {

    @Autowired
    private ModelEvaluationMapper modelEvaluationMapper;

    public List<ModelEvaluation> getEvaluationResults(int scenarioId) {
        return modelEvaluationMapper.findByScenario(scenarioId);
    }
}

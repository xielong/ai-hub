package com.github.xielong.aihub.controller;

import com.github.xielong.aihub.dao.ModelEvaluation;
import com.github.xielong.aihub.model.ModelEvaluationResponse;
import com.github.xielong.aihub.service.EvaluationService;
import com.github.xielong.aihub.util.AIModel;
import com.github.xielong.aihub.util.EvaluationScenario;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/evaluation")
@Slf4j
public class EvaluationController {

    private final ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private EvaluationService evaluationService;

    private static ModelEvaluationResponse convertToResponse(ModelEvaluation evaluation) {
        ModelEvaluationResponse response = new ModelEvaluationResponse();
        AIModel modelEnum = AIModel.fromId(evaluation.getModel());
        response.setModelAlias(modelEnum.getAlias());
        response.setModelVersion(modelEnum.getVersion());

        response.setRating(evaluation.getRating());
        response.setComment(evaluation.getComment());

        return response;
    }

    @GetMapping("/{scenario}")
    public ResponseEntity<List<ModelEvaluationResponse>> getEvaluationResults(
            @PathVariable String scenario
    ) throws Exception {
        int scenarioId = EvaluationScenario.fromName(scenario).getId();
        List<ModelEvaluation> modelEvaluations = evaluationService.getEvaluationResults(scenarioId);
        return ResponseEntity.ok(modelEvaluations.stream()
                .map(EvaluationController::convertToResponse)
                .collect(Collectors.toList()));
    }


}

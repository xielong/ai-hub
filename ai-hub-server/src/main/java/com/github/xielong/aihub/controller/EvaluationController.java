package com.github.xielong.aihub.controller;

import com.github.xielong.aihub.model.ChatResponse;
import com.github.xielong.aihub.service.EvaluationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/evaluation")
@Slf4j
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/{scenario}")
    public ResponseEntity<ChatResponse> invokeModel() throws Exception {
        String output = evaluationService.getEvaluationResults();
        ChatResponse response = new ChatResponse();
        response.setOutput(output);
        return ResponseEntity.ok(response);
    }
}

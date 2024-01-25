package com.github.xielong.aihub.controller;

import com.github.xielong.aihub.dao.ModelAnswer;
import com.github.xielong.aihub.model.AnswerResponse;
import com.github.xielong.aihub.model.QuestionResponse;
import com.github.xielong.aihub.service.ModelAnswerService;
import com.github.xielong.aihub.util.AIModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/history")
@Slf4j
public class HistoryController {

    @Autowired
    private ModelAnswerService modelAnswerService;

    @GetMapping("/questions")
    public ResponseEntity<List<QuestionResponse>> findAllQuestions() {
        List<ModelAnswer> modelAnswers = modelAnswerService.findQuestions(1);

        List<QuestionResponse> questionResponses = modelAnswers.stream()
                .map(modelAnswer -> {
                    QuestionResponse questionResponse = new QuestionResponse();
                    questionResponse.setHash(modelAnswer.getQuestionHash());
                    questionResponse.setQuestion(modelAnswer.getQuestion());
                    return questionResponse;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(questionResponses);

    }

    @GetMapping("/questions/{questionHash}/answers")
    public ResponseEntity<List<AnswerResponse>> findAnswers(
            @PathVariable String questionHash) {
        List<ModelAnswer> modelAnswers = modelAnswerService.findAnswersByQuestionHash(questionHash);

        List<AnswerResponse> answerResponses = modelAnswers.stream()
                .map(modelAnswer -> {
                    AnswerResponse answerResponse = new AnswerResponse();
                    answerResponse.setId(modelAnswer.getId());
                    
                    AIModel model = AIModel.fromId(modelAnswer.getModel());
                    answerResponse.setProviderName(model.getProvider());
                    answerResponse.setModelName(model.getName());
                    answerResponse.setAnswer(modelAnswer.getAnswer());
                    return answerResponse;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(answerResponses);

    }


}
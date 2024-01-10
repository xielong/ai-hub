package com.github.xielong.aihub.controller;

import com.github.xielong.aihub.model.ChatRequest;
import com.github.xielong.aihub.model.ChatResponse;
import com.github.xielong.aihub.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/models")
@Slf4j
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/{provider}/{model}:chat")
    public ResponseEntity<ChatResponse> invokeModel(
            @PathVariable String provider,
            @PathVariable String model,
            @RequestBody ChatRequest request) throws Exception {
        log.debug("Received request to invoke model. Provider: {}, ModelName: {}, Input: {}",
                provider, model, request);
        try {
            String output = chatService.invokeModel(provider, model, request.getInput());
            ChatResponse response = new ChatResponse();
            response.setOutput(output);
            log.debug("Model executed successfully. Provider: {}, ModelName: {}, Result: {}",
                    provider, model, response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error executing model. Provider: {}, ModelName: {}", provider, model, e);
            throw e;
        }

    }


}
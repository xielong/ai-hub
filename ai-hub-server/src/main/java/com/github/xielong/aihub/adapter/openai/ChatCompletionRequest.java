package com.github.xielong.aihub.adapter.openai;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatCompletionRequest {
    private String model;
    private boolean stream;

    @Builder.Default
    private double temperature = 0.8;
    private List<Message> messages;

    @Data
    @Builder
    public static class Message {
        private String role;
        private String content;
    }
}


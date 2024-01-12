package com.github.xielong.aihub.adapter.minimax;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class ChatCompletionRequest {
    private List<BotSetting> bot_setting;
    private List<Message> messages;
    private ReplyConstraints reply_constraints;
    private String model;
    private int tokens_to_generate;
    private double temperature;
    private double top_p;

    @Data
    @AllArgsConstructor
    public static class BotSetting {
        private String bot_name;
        private String content;
    }

    @Data
    @AllArgsConstructor
    public static class Message {
        private String sender_type;
        private String sender_name;
        private String text;
    }

    @Data
    @AllArgsConstructor
    public static class ReplyConstraints {
        private String sender_type;
        private String sender_name;
    }
}



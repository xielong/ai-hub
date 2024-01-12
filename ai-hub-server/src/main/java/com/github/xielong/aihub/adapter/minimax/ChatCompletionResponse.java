package com.github.xielong.aihub.adapter.minimax;

import lombok.Data;

import java.util.List;

@Data
public class ChatCompletionResponse {
    private long created;
    private String model;
    private String reply;
    private List<Choice> choices;
    private Usage usage;
    private boolean input_sensitive;
    private boolean output_sensitive;
    private String id;
    private BaseResponse base_resp;

    @Data
    public static class Choice {
        private String finish_reason;
        private List<Message> messages;
    }

    @Data
    public static class Message {
        private String sender_type;
        private String sender_name;
        private String text;
    }

    @Data
    public static class Usage {
        private int total_tokens;
    }

    @Data
    public static class BaseResponse {
        private int status_code;
        private String status_msg;
    }
}

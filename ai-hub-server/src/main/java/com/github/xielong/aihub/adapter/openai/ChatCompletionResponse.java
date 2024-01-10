package com.github.xielong.aihub.adapter.openai;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class ChatCompletionResponse {

    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;

    @Data
    public static class Choice {
        private Delta delta;
        private int index;
        @SerializedName("finish_reason")
        private String finishReason;
    }

    @Data
    public static class Delta {
        private String role;
        private String content;
    }
}


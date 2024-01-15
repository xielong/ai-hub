package com.github.xielong.aihub.adapter.xunfei;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ChatCompletionRequest {
    private Header header;
    private Parameter parameter;
    private Payload payload;

    @Data
    public static class Header {
        @SerializedName("app_id")
        private String appId;
        private String uid;

    }

    @Data
    public static class Parameter {
        private Chat chat;

        @Data
        public static class Chat {
            private String domain;
            private double temperature;
            @SerializedName("max_tokens")
            private int maxTokens;

        }

    }


    @Data
    public static class Payload {
        private Message message;

        @Data
        public static class Message {
            private JsonArray text;
        }
    }

    @Data
    public static class RoleContent {
        private String role;
        private String content;

    }


}



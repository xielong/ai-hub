package com.github.xielong.aihub.adapter.xunfei;

import lombok.Data;

import java.util.List;

@Data
public class ChatCompletionResponse {

    public static final int STATUS_COMPLETE = 2;

    private Header header;
    private Payload payload;

    @Data
    class Header {
        private int code;
        private int status;
        private String sid;
    }

    @Data
    class Payload {
        private Choices choices;
    }

    @Data
    class Choices {
        private List<Text> text;
    }

    @Data
    class Text {
        private String role;
        private String content;
    }
}

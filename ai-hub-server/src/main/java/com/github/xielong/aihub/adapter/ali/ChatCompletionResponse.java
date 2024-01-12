package com.github.xielong.aihub.adapter.ali;

import lombok.Data;

@Data
public class ChatCompletionResponse {
    private String requestId;
    private Usage usage;
    private Output output;


    @Data
    class Usage {
        private int input_tokens;
        private int output_tokens;

    }

    @Data
    class Output {
        private String text;
        private String finish_reason;
    }
}


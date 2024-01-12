package com.github.xielong.aihub.adapter.tencent;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ChatCompletionResponse {

    private String id;
    private String object;
    private long created;
    private int sentenceId;
    private boolean isEnd;
    private boolean isTruncated;
    private String result;
    private boolean needClearHistory;
    private Usage usage;

    @Data
    public static class Usage {
        @SerializedName("prompt_tokens")
        private int promptTokens;
        @SerializedName("completion_tokens")
        private int completionTokens;
        @SerializedName("total_tokens")
        private int totalTokens;
    }

}



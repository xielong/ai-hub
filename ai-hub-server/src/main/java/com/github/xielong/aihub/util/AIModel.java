package com.github.xielong.aihub.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AIModel {
    OPENAI_GPT3_5_TURBO(1, "gpt-3.5-turbo-0125", "GPT3.5", AIProvider.OPENAI.getName(), 10),
    BAIDU_ERNIE_BOT_TURBO(2, "ERNIE-Bot-turbo", "文心一言ERNIE-Bot-turbo", AIProvider.BAIDU.getName(), 10),
    ALI_QWEN_TURBO(3, "qwen-turbo", "通义千问turbo", AIProvider.ALI.getName(), 10),
    TENCENT_CHAT_STD(4, "ChatStd", "混元标准版", AIProvider.TENCENT.getName(), 10),
    BAICHUAN_BAICHUAN2_TURBO(5, "Baichuan2-Turbo", "百川智能", AIProvider.BAICHUAN.getName(), 10),
    ZHIPU_CHAT_GLM_TURBO(6, "chatGLM_turbo", "智谱chatGLM_turbo", AIProvider.ZHIPU.getName(), 10),
    MINIMAX_ABAB5_5_CHAT(7, "abab5.5-chat", "MiniMax abab5.5", AIProvider.MINIMAX.getName(), 10),
    XUNFEI_SPARK3_1(8, "Spark3.1", "讯飞星火3", AIProvider.XUNFEI.getName(), 10),
    BAIDU_ERNIE_BOT_4(9, "ERNIE-Bot-4", "文心一言ERNIE-Bot 4.0", AIProvider.BAIDU.getName(), 100),
    ALI_QWEN_PLUS(10, "qwen-plus", "通义千问plus", AIProvider.ALI.getName(), 20),
    ZHIPU_GLM_4(11, "glm-4", "智谱glm-4", AIProvider.ZHIPU.getName(), 100),
    TENCENT_CHAT_PRO(12, "ChatPro", "混元高级版", AIProvider.TENCENT.getName(), 100),
    MINIMAX_ABAB6_CHAT(13, "abab6-chat", "MiniMax abab6", AIProvider.MINIMAX.getName(), 100),
    OPENAI_GPT4_1106_PREVIEW(14, "gpt-4-0125-preview", "ChatGPT4", AIProvider.OPENAI.getName(), 100),
    MOONSHOT_V1_8K(15, "moonshot-v1-8k", "moonshot-v1", AIProvider.MOONSHOT.getName(), 10),
    XUNFEI_SPARK3_5(16, "Spark3.5", "讯飞星火3.5", AIProvider.XUNFEI.getName(), 10),
    BYTEDANCE_SKYLARK_CHAT(17, "Skylark-chat", "字节豆包", AIProvider.BYTEDANCE.getName(), 10),
    LINGYI_YI_34B_CHAT(18, "yi-34b-chat-0205", "零一万物34b", AIProvider.LINGYI.getName(), 2),
    LINGYI_YI_34B_CHAT_200k(19, "yi-34b-chat-200k", "零一万物200k", AIProvider.LINGYI.getName(), 12),
    LINGYI_YI_VL_PLUS(20, "yi-vl-plus", "零一万物plus", AIProvider.LINGYI.getName(), 6),
    DEEPSEEK_V2(21, "deepseek-chat", "DeepSeek-V2", AIProvider.LINGYI.getName(), 1),
    HUNYUAN_LITE(22, "hunyuan-lite", "Hunyuan lite", AIProvider.TENCENT.getName(), 1);

    private final int id;
    private final String name;
    private final String alias;
    private final String provider;
    private final int version;

    public static AIModel fromId(Integer id) {
        for (AIModel model : values()) {
            if (model.getId() == id) {
                return model;
            }
        }
        throw new IllegalArgumentException("Unknown provider id: " + id);
    }

    public static AIModel fromName(String name) {
        for (AIModel model : values()) {
            if (model.getName().equalsIgnoreCase(name)) {
                return model;
            }
        }
        throw new IllegalArgumentException("Unknown provider: " + name);
    }
}

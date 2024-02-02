package com.github.xielong.aihub.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AIProvider {
    OPENAI(1, "OpenAI"),
    BAICHUAN(2, "Baichuan"),
    BAIDU(3, "Baidu"),
    ALI(4, "Ali"),
    ZHIPU(5, "Zhipu"),
    TENCENT(6, "Tencent"),
    MINIMAX(7, "MiniMax"),
    XUNFEI(8, "Xunfei"),
    MOONSHOT(9, "Moonshot"),
    BYTEDANCE(10, "ByteDance");

    private final int id;
    private final String name;

    public static AIProvider fromId(Integer id) {
        for (AIProvider provider : values()) {
            if (provider.getId() == id) {
                return provider;
            }
        }
        throw new IllegalArgumentException("Unknown provider id: " + id);
    }

    public static AIProvider fromName(String name) {
        for (AIProvider provider : values()) {
            if (provider.getName().equalsIgnoreCase(name)) {
                return provider;
            }
        }
        throw new IllegalArgumentException("Unknown provider: " + name);
    }
}



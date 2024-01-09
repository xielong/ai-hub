package com.github.xielong.aihub.util;

public enum AIProvider {
    OPENAI(1, "OpenAI"),
    BAICHUAN(2, "Baichuan"),
    BAIDU(3, "Baidu"),
    ALI(4, "Ali"),
    ZHIPU(5, "Zhipu"),
    TENCENT(6, "Tencent"),
    MINIMAX(7, "MiniMax"),
    XUNFEI(8, "Xunfei");

    private final int id;
    private final String name;

    AIProvider(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static AIProvider fromName(String name) {
        for (AIProvider provider : values()) {
            if (provider.getName().equalsIgnoreCase(name)) {
                return provider;
            }
        }
        throw new IllegalArgumentException("Unknown provider: " + name);
    }

    public String getName() {
        return name;
    }

}



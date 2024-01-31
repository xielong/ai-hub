package com.github.xielong.aihub.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EvaluationScenario {

    TRANSLATION(101, "translation"),
    Coding(102, "coding"),
    Instruction(103, "instruction");
    private final int id;
    private final String name;

    public static EvaluationScenario fromId(Integer id) {
        for (EvaluationScenario provider : values()) {
            if (provider.getId() == id) {
                return provider;
            }
        }
        throw new IllegalArgumentException("Unknown provider id: " + id);
    }

    public static EvaluationScenario fromName(String name) {
        for (EvaluationScenario provider : values()) {
            if (provider.getName().equalsIgnoreCase(name)) {
                return provider;
            }
        }
        throw new IllegalArgumentException("Unknown provider: " + name);
    }
}

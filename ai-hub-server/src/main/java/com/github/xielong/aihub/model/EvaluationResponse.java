package com.github.xielong.aihub.model;

import lombok.Data;

@Data
public class EvaluationResponse {
    private int scenarioId;
    private int scenarioName;
    private int provider;
    private int providerName;
    private String modelName;
    private int rating;
}

package com.github.xielong.aihub.model;

import lombok.Data;

@Data
public class ModelEvaluationResponse {
    private String modelAlias;
    private Integer modelVersion;
    private Integer rating;
    private String comment;
}


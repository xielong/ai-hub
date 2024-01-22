package com.github.xielong.aihub.model;

import lombok.Data;

import java.util.Date;

@Data
public class ModelEvaluationResponse {
    private Integer id;
    private Integer provider;
    private String modelName;
    private Integer scenarioId;
    private Byte rating;
    private String comment;
    private Date updatedAt;
}


package com.github.xielong.aihub.dao;

import lombok.Data;

import java.util.Date;

@Data
public class ModelEvaluation {

    private Integer id;
    private Integer provider;
    private String modelName;
    private Integer scenarioId;
    private Integer rating;
    private String comment;
    private Date updatedAt;

}


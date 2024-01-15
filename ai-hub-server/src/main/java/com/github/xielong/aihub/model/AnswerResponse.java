package com.github.xielong.aihub.model;

import lombok.Data;

import java.util.Date;

@Data
public class AnswerResponse {

    private Integer id;
    private String providerName;
    private String modelName;
    private String answer;
    private Integer rating;
    private String comment;
    private Date updatedAt;

}

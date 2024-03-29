package com.github.xielong.aihub.dao;

import lombok.Data;

import java.util.Date;

@Data
public class ModelAnswer {

    private Integer id;
    private String questionHash;
    private String question;
    private Integer scenario;
    private Integer model;
    private String answer;
    private Integer rating;
    private String comment;
    private Date updatedAt;

}


package com.github.xielong.aihub.dao;

import lombok.Data;

import java.util.Date;

@Data
public class ApiCredential {

    private Integer id;

    private Integer provider;

    private String key;

    private String value;

    private Date updatedAt;
}



package com.github.xielong.aihub.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ApiCredentialMapper {

    @Select("SELECT * FROM api_credential WHERE provider = #{provider}")
    List<ApiCredential> findByProvider(Integer provider);

    @Select("SELECT * FROM api_credential")
    List<ApiCredential> findAll();
}



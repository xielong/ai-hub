package com.github.xielong.aihub.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM api_credential WHERE provider = #{provider} and `key` = #{key}")
    Credential findByProviderAndKey(Integer provider, String key);

    @Select("SELECT * FROM api_credential WHERE provider = #{provider}")
    List<Credential> findByProvider(Integer provider);

    @Select("SELECT * FROM api_credential")
    List<Credential> findAll();

    @Insert("INSERT INTO api_credential (provider, `key`, `value`) VALUES (#{provider}, #{key}, #{value})")
    void insert(Credential credential);

    @Update("UPDATE api_credential SET `value` = #{value} WHERE provider = #{provider} and `key` = #{key}")
    void update(Credential credential);


}



package com.github.xielong.aihub.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ModelAnswerMapper {

    @Select("SELECT MIN(question_hash) as question_hash, question FROM model_answer " +
            "GROUP BY question")
    List<ModelAnswer> findAllQuestions();

    @Select("SELECT * FROM model_answer WHERE question_hash = #{questionHash} order by update_at")
    List<ModelAnswer> findByQuestionHash(String questionHash);

    @Insert("INSERT INTO model_answer (`provider`, question_hash, question, model_name, answer, rating, comment) " +
            "VALUES (#{provider}, #{questionHash}, #{question}, #{modelName}, #{answer}, #{rating}, #{comment})")
    void insert(ModelAnswer modelAnswer);

}

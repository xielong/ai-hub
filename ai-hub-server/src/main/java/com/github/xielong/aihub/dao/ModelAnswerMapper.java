package com.github.xielong.aihub.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ModelAnswerMapper {

    @Select("SELECT ma.question_hash, ma.question " +
            "FROM (" +
            "    SELECT MIN(question_hash) as question_hash, question, MAX(updated_at) as max_updated_at " +
            "    FROM model_answer " +
            "    GROUP BY question " +
            ") AS ma " +
            "ORDER BY ma.max_updated_at DESC " +
            "LIMIT #{limit}  OFFSET #{offset} ")
    List<ModelAnswer> findQuestions(int offset, int limit);

    @Select("SELECT * FROM model_answer WHERE question_hash = #{questionHash} ")
    List<ModelAnswer> findByQuestionHash(String questionHash);

    @Insert("INSERT INTO model_answer (question_hash, question, model, answer, rating, comment) " +
            "VALUES (#{questionHash}, #{question}, #{model}, #{answer}, #{rating}, #{comment})")
    void insert(ModelAnswer modelAnswer);

}

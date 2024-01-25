package com.github.xielong.aihub.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ModelEvaluationMapper {
    @Select("SELECT * FROM model_evaluation WHERE scenario_id = #{scenarioId} ORDER BY rating DESC")
    List<ModelEvaluation> findByScenario(Integer scenarioId);

    @Insert("INSERT INTO model_evaluation (`provider`, `model`, `scenario_id`, `rating`, `comment`) " +
            "VALUES (#{provider}, #{model}, #{scenarioId}, #{rating}, #{comment})")
    void insert(ModelEvaluation modelEvaluation);

}

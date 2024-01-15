package com.github.xielong.aihub.service;

import com.github.xielong.aihub.dao.ModelAnswer;
import com.github.xielong.aihub.dao.ModelAnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelAnswerService {

    @Autowired
    private ModelAnswerMapper modelAnswerMapper;

    public List<ModelAnswer> findAllQuestions() {
        return modelAnswerMapper.findAllQuestions();
    }
}

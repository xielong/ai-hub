package com.github.xielong.aihub.service;

import com.github.xielong.aihub.dao.ModelAnswer;
import com.github.xielong.aihub.dao.ModelAnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelAnswerService {

    private static final int PAGE_SIZE = 20;
    @Autowired
    private ModelAnswerMapper modelAnswerMapper;

    public List<ModelAnswer> findQuestions(int currentPage) {
        int offset = (currentPage - 1) * PAGE_SIZE;
        return modelAnswerMapper.findQuestions(offset, PAGE_SIZE);
    }

    public List<ModelAnswer> findAnswersByQuestionHash(String questionHash) {
        return modelAnswerMapper.findByQuestionHash(questionHash);
    }
}

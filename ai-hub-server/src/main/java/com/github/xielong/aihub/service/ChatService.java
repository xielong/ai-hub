package com.github.xielong.aihub.service;

import com.github.xielong.aihub.adapter.AIModelInvoker;
import com.github.xielong.aihub.adapter.AIModelInvokerFactory;
import com.github.xielong.aihub.dao.ModelAnswer;
import com.github.xielong.aihub.dao.ModelAnswerMapper;
import com.github.xielong.aihub.util.AIProvider;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private AIModelInvokerFactory aiModelInvokerFactory;

    @Autowired
    private ModelAnswerMapper modelAnswerMapper;

    public String invokeModel(String provider, String model, String input) throws Exception {
        AIModelInvoker aiModelInvoker = aiModelInvokerFactory.getProviderAdapter(provider);
        String output = aiModelInvoker.invoke(model, input);

        ModelAnswer modelAnswer = new ModelAnswer();
        modelAnswer.setProvider(AIProvider.fromName(provider).getId());
        modelAnswer.setModelName(model);
        modelAnswer.setQuestion(input);
        modelAnswer.setQuestionHash(DigestUtils.sha256Hex(input));
        modelAnswer.setAnswer(output);
        modelAnswerMapper.insert(modelAnswer);

        return output;
    }
}

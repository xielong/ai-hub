package com.github.xielong.aihub.service;

import com.github.xielong.aihub.adapter.AIModelInvoker;
import com.github.xielong.aihub.adapter.AIModelInvokerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private AIModelInvokerFactory aiModelInvokerFactory;

    public String invokeModel(String provider, String model, String input) throws Exception {
        AIModelInvoker aiModelInvoker = aiModelInvokerFactory.getProviderAdapter(provider);
        return aiModelInvoker.invoke(model, input);
    }
}

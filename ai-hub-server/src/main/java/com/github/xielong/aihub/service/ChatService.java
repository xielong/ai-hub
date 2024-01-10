package com.github.xielong.aihub.service;

import com.github.xielong.aihub.adapter.AIModelInvoker;
import com.github.xielong.aihub.adapter.AIModelInvokerFactory;
import com.github.xielong.aihub.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private AIModelInvokerFactory aiModelInvokerFactory;

    public ChatResponse invokeModel(String provider, String model, String input) throws Exception {
        AIModelInvoker aiModelInvoker = aiModelInvokerFactory.getProviderAdapter(provider);
        String output = aiModelInvoker.invoke(model, input);

        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setOutput(output);
        return chatResponse;
    }
}

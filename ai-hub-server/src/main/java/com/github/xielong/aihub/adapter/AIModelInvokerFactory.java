package com.github.xielong.aihub.adapter;

import com.github.xielong.aihub.adapter.ali.AliInvoker;
import com.github.xielong.aihub.adapter.baichuan.BaichuanInvoker;
import com.github.xielong.aihub.adapter.baidu.BaiduInvoker;
import com.github.xielong.aihub.adapter.minimax.MiniMaxInvoker;
import com.github.xielong.aihub.adapter.openai.OpenAIInvoker;
import com.github.xielong.aihub.adapter.tencent.TencentInvoker;
import com.github.xielong.aihub.adapter.xunfei.XunfeiInvoker;
import com.github.xielong.aihub.adapter.zhipu.ZhipuInvoker;
import com.github.xielong.aihub.util.AIProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class AIModelInvokerFactory {

    private final ApplicationContext context;

    @Autowired
    public AIModelInvokerFactory(ApplicationContext context) {
        this.context = context;
    }

    public AIModelInvoker getProviderAdapter(String providerName) {
        AIProvider provider = AIProvider.fromName(providerName);

        switch (provider) {
            case OPENAI:
                return context.getBean(OpenAIInvoker.class);
            case BAICHUAN:
                return context.getBean(BaichuanInvoker.class);
            case ALI:
                return context.getBean(AliInvoker.class);
            case BAIDU:
                return context.getBean(BaiduInvoker.class);
            case ZHIPU:
                return context.getBean(ZhipuInvoker.class);
            case TENCENT:
                return context.getBean(TencentInvoker.class);
            case XUNFEI:
                return context.getBean(XunfeiInvoker.class);
            case MINIMAX:
                return context.getBean(MiniMaxInvoker.class);
            default:
                throw new IllegalArgumentException("Unknown provider: " + provider);
        }
    }


}

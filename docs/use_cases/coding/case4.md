## Challenge

```shell
	我需要写一个 Dockerfile，用来从源码打包，然后启动 spring boot 服务：target/ai-hub-server-1.0.0-SNAPSHOT-exec.jar

是 maven 项目，端口 9999，把 Dockerfile 内容告诉我
```

## Answers

### Minimax/abab6-chat（7.5）
完整实现需求，但是有小细节还可以更好。

### OpenAI/ChatGPT4（7）
完整完成需求，解释很清楚，还缓存了 Maven 依赖，但是又用 -DskipTests 跳过了测试。

### OpenAI/gpt-3.5-turbo（7）
完整完成需求，解释很清楚，还缓存了 Maven 依赖，但是又用 -DskipTests 跳过了测试。

### Tencent/ChatPro（7）
完整完成需求，解释很清楚，还缓存了 Maven 依赖，但是又用 -DskipTests 跳过了测试。

### Tencent/ChatStd（7）
完整实现需求，但是有几个地方是最佳实践。

### Baidu/ERNIE-Bot-4（7）
完整实现需求，但是有几个地方是最佳实践。

### Zhipu/chatGLM_turbo（6.5）
基本上实现了需求，但是多了些奇怪的命令。

### Baidu/ERNIE-Bot-turbo(5)
实现不完整。

### Ali/qwen-plus（5）
实现不完整。

### Zhipu/glm-4（5）
实现不完整。

### Baichuan/Baichuan2-Turbo(5)
实现不完整。

### Minimax/abab5.5-chat(5)
实现不完整。

### Ali/qwen-turbo（4）
命令不完整，而且写了一堆奇怪的命令。

### Xunfei/Spark3.1（4）
实现不完整，而且有错误。

### Summary
1. 注意：大型语言模型的响应基于概率性预测，因此并不保证每次都一致。只有通过广泛的测试用例和多次测试，才能更准确地评估模型的性能。

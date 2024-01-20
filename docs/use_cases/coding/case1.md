## Challenge

```java
	Gson gson = new Gson();

   	JsonObject header = new JsonObject();
    header.addProperty("app_id", appId);
    header.addProperty("uid", UUID.randomUUID().toString().substring(0, 10));

    JsonObject parameter = new JsonObject();
    JsonObject chat = new JsonObject();
    chat.addProperty("domain", "generalv2");
    chat.addProperty("temperature", 0.5);
    chat.addProperty("max_tokens", 4096);
    parameter.add("chat", chat);

    JsonObject payload = new JsonObject();
    JsonObject message = new JsonObject();
    JsonArray text = new JsonArray();

	RoleContent roleContent = new RoleContent();
    roleContent.role = "user";
    roleContent.content = input;
    text.add(gson.toJsonTree(roleContent));

    message.add("text", text);
    payload.add("message", message);

    JsonObject requestJson = new JsonObject();
    requestJson.add("header", header);
    requestJson.add("parameter", parameter);
    requestJson.add("payload", payload);


这个解析的属性放到 ChatCompletionRequest Bean里吧，注意需要嵌套 bean 来处理这个问题

```

## Answers

### OpenAI/ChatGPT4（8）
正确完成了需求。

### OpenAI/gpt-3.5-turbo（8）
正确完成了需求。

### Tencent/ChatPro（8）
正确完成了需求。

### Ali/qwen-plus（7.5）
基本正确完成了需求，但是部分属性不符合Java Bean规范。

### Zhipu/glm-4（7.5）
基本正确完成了需求，但是部分属性不符合Java Bean规范。

### Tencent/ChatStd（7.5）
基本正确完成了需求，但是部分属性不符合Java Bean规范。

### Baidu/ERNIE-Bot-4（7）
正确完成了需求。但没有使用子类。

### Minimax/abab6-chat（5.5）
基本正确完成了需求，但是RoleContent搞丢了。

### Baichuan/Baichuan2-Turbo（5）
正确定义了子类，但是ChatCompletionRequest Bean的名称忘了，自己起了个RequestJson的Bean的名称。

### Baidu/ERNIE-Bot-turbo(4)
定义了三个JsonObject属性：header，parameter 和 payload，显然没有完全理解需求.

### Ali/qwen-turbo（4）
定义了三个JsonObject属性：header，parameter 和 payload，显然没有完全理解需求.

### Minimax/abab5.5-chat（4）
定义了三个JsonObject属性：header，parameter 和 payload，显然没有完全理解需求.

### Zhipu/chatGLM_turbo（4）
定义了三个JsonObject属性：header，parameter 和 payload，显然没有完全理解需求.

### Xunfei/Spark3.1（2）
结构都定义错了。


### Summary
1. 注意：大型语言模型的响应基于概率性预测，因此并不保证每次都一致。只有通过广泛的测试用例和多次测试，才能更准确地评估模型的性能。

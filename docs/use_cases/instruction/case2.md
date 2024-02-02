## 指令

请按照以下规则分析提供的 JSON 格式的通知内容，并区分为"重要消息"和"普通消息"：

金钱相关的消息，如包含"红包"、"银行"、"资金"、"消费"等词汇，应被分类为重要消息。
提醒信息，包括但不限于恶劣天气提醒、日程提醒等，也应被视为重要消息。
用户特别指定的其他重要信息。
所有不满足以上条件的消息都被视为普通消息。
请根据这些规则，将输入的 JSON 格式通知内容分类，并按照以下格式输出：

{
  "重要消息": [
    {
      "appName": "",
      "appID": "",
      "message": ""
    }
  ],
  "普通消息": [
    {
      "appName": "",
      "appID": "",
      "message": ""
    }
  ]
}
请注意，输出的 JSON 应包含分好类的通知信息。

示例输入:

{
  "notifications": [
    {
      "appName": "日历",
      "appID": "com.rili.xiaomi",
      "title": "购票提醒",
      "message": "12306即将开启购票，请做好准备"
    },
    {
      "appName": "微信",
      "appID": "com.weixin",
      "title": "哈利波特",
      "message": "今天可能有暴雪，你注意安全，早点回家"
    },
    {
      "appName": "微信",
      "appID": "com.weixin",
      "title": "加菲猫",
      "message": "[链接]虚拟现实：开启未来的无限可能"
    },
    {
      "appName": "美团外卖",
      "appID": "com.sankuai.meituan.takeoutnew",
      "title": "您的外卖订单正在进行中",
      "message": "可前往订单详情页查看具体进展"
    }
  ]
}


接下来，根据上述示例的分析规则，输出的 JSON 格式为：

{
  "importantMessages": [
    {
      "appName": "日历",
      "appID": "com.rili.xiaomi",
      "message": "购票提醒，12306即将开启购票，请做好准备"
    },
    {
      "appName": "微信",
      "appID": "com.weixin",
      "message": "来自'哈利'，提醒你今天可能有暴雪"
    }
  ],
  "regularMessages": [
    {
      "appName": "微信",
      "appID": "com.weixin",
      "message": "来自'哈利'，发送一篇虚拟现实的文章链接"
    },
    {
      "appName": "美团外卖",
      "appID": "com.sankuai.meituan.takeoutnew",
      "message": "外卖订单进行中，可查看详情"
    }
  ]
}

以下是来自用户的内容，请你按照这个规则进行整理反馈。

{
  "notifications": [
    {
      "appName": "招商银行",
      "appID": "cmb.pb",
      "title": "招商银行信用卡通知：",
      "message": "您尾号7789的招行信用卡消费28.74人民币。"
    },
    {
      "appName": "美团外卖",
      "appID": "com.sankuai.meituan.takeoutnew",
      "title": "您的准时宝和放心吃保障已生效",
      "message": "点击查看保障详情>>"
    },
    {
      "appName": "京东金融",
      "appID": "jd.finance",
      "message": "您的京东白条本月账单已出，需还款金额为500.00元，请于还款日前完成还款。"
    },
    {
      "appName": "微信支付",
      "appID": "com.tencent.mm.wallet",
      "message": "您的微信红包已到账，金额20.00元，快去微信钱包查看吧！"
    },
    {
      "appName": "支付宝",
      "appID": "com.eg.android.AlipayGphone",
      "title": "你有1个包裹物流更新啦",
      "message": "点击查看详情>>"
    },
    {
      "appName": "微信",
      "appID": "com.weather",
      "message": "预警提醒：明天您所在地区将迎来强降雨，请做好防范措施。"
    },
    {
      "appName": "日历提醒",
      "appID": "com.calendar",
      "message": "您设定的会议日程提醒：明天上午10点，关于年度营销计划的讨论会。"
    },
    {
      "appName": "环境监测",
      "appID": "com.enviroment",
      "message": "空气质量警报：今日PM2.5指数超标，建议减少户外活动。"
    },
    {
      "appName": "旅行助手",
      "appID": "com.travel",
      "message": "出行提醒：由于近期山区滑坡风险增加，计划前往该区域的行程请谨慎安排。"
    },
    {
      "appName": "社交平台",
      "appID": "com.social",
      "message": "您的好友小明刚刚更新了动态，快去看看吧！"
    },
    {
      "appName": "在线视频",
      "appID": "com.video",
      "message": "您关注的频道发布了新视频：《探索宇宙的奥秘》，现在就可以观看！"
    },
    {
      "appName": "健康管理",
      "appID": "com.health",
      "message": "今日步数达到3000步，距离目标还有7000步，继续加油！"
    },
    {
      "appName": "图书推荐",
      "appID": "com.books",
      "message": "根据您的阅读喜好，推荐《时间的秩序》这本书，相信您会喜欢！"
    }
  ]
}

不要做任何其他输出和解释，仅输出json即可。不要写代码分析，仅用大语言模型来分析。

## 评分

### OpenAI/ChatGPT4（10）
格式正确，内容正确。

### Baichuan/Baichuan2-Turbo（10）
格式正确，内容正确。

### Ali/qwen-plus（10）
格式正确，内容正确。

### Moonshot/moonshot-v1-8k（10）
格式正确，内容正确。

### Zhipu/glm-4（10）
格式正确，内容正确。

### Baidu/ERNIE-Bot-4（10）
格式正确，内容正确。

### Minimax/abab6-chat（10）
格式正确，内容正确。

### Minimax/abab5.5-chat（6）
格式正确，某条难以区分的普通消息被归类到重要消息了。

### Zhipu/chatGLM_turbo（6）
格式正确，某条难以区分的普通消息被归类到重要消息了。

### Tencent/ChatPro（4）
格式正确，普通消息被归类到重要消息。

### Ali/qwen-turbo（4）
格式正确，普通消息被归类到重要消息。

### Tencent/ChatStd（4）
格式正确，出现重复消息。

### OpenAI/gpt-3.5-turbo （3）
格式正确，有些重要消息被漏掉了。

### Xunfei/Spark3.5（3）
格式正确，有些重要消息被漏掉了。

### Xunfei/Spark3.1（2）
格式正确，重要消息基本上都遗漏了。

### ByteDance/Skylark-chat（2）
除了 JSON，多了一堆解释。

### Baidu/ERNIE-Bot-turbo （0）
格式，内容都是错的。


### Summary
1. 注意：大型语言模型的响应基于概率性预测，因此并不保证每次都一致。只有通过广泛的测试用例和多次测试，才能更准确地评估模型的性能。
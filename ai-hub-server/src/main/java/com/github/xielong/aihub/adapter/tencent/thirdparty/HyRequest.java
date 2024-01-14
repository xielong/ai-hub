/**
 * Copyright (c) 2017-2018 THL A29 Limited, a Tencent company. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.xielong.aihub.adapter.tencent.thirdparty;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * HyRequest 请求参数
 */
@Data
public class HyRequest {

    //腾讯云账号的 APPID
    @SerializedName("app_id")
    private long appId;

    //官网 SecretId
    @SerializedName("secret_id")
    private String secretId;

    //当前 UNIX 时间戳，单位为秒
    @SerializedName("timestamp")
    private long timestamp;

    //签名的有效期
    @SerializedName("expired")
    private long expired;

    //请求 ID，用于问题排查
    @SerializedName("query_id")
    private String queryId;

    //默认1.0，取值区间为[0.0, 2.0] 非必要不建议使用, 不合理的取值会影响效果
    @SerializedName("temperature")
    private double temperature;

    //默认1.0，取值区间为[0.0, 1.0]，非必要不建议使用, 不合理的取值会影响效果
    @SerializedName("top_p")
    private double topP;

    //0：同步，1：流式 （默认，协议：SSE)
    @SerializedName("stream")
    private int stream;

    //会话内容
    @SerializedName("messages")
    private Message[] messages;
    
}

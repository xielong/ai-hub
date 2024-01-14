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
 * 响应
 */
@Data
public class HyResponse {

    //unix 时间戳的字符串
    @SerializedName("created")
    private String created;

    //会话 id
    @SerializedName("id")
    private String id;

    //注释
    @SerializedName("note")
    private String note;

    //唯一请求 ID
    @SerializedName("req_id")
    private String reqId;

    @SerializedName("choices")
    private HyResponseChoice[] choices;

    @SerializedName("usage")
    private HyResponseUsage usage;

    @SerializedName("error")
    private HyResponseError error;

}

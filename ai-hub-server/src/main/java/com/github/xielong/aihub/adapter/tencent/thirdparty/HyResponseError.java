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
 * 错误信息
 */
@Data
public class HyResponseError {

    //错误提示信息
    @SerializedName("message")
    private String message;

    //错误码
    @SerializedName("code")
    private Integer code;

}

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

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * SignUtil 签名工具
 */

@Slf4j
public class SignUtil {

    private static final String REQUEST_ENCODE = "UTF-8";
    private static final String SIGN_TYPE = "HmacSHA1"; // 采用的签名算法

    /**
     * 使用HMAC-SHA1算法对拼接字符串签名，并将生成的签名串 使用Base64进行编码，返回编码结果。
     *
     * @param originalText 需要被签名的字符串
     * @param secretKey    秘钥字符串
     * @return 签名和编码之后的字符串
     */
    public static String base64_hmac_sha1(String originalText, String secretKey) {
        try {
            Mac hmac = Mac.getInstance(SIGN_TYPE);
            hmac.init(new SecretKeySpec((secretKey).getBytes(REQUEST_ENCODE), SIGN_TYPE)); // 初始化
            byte[] hash = hmac.doFinal((originalText).getBytes(REQUEST_ENCODE)); // 签名
            return Base64.encodeBase64String(hash); // 编码
        } catch (Exception e) {
            log.error("Errors: ", e);
            return ""; // 签名编码失败。
        }
    }
}

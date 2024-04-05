package com.nowcoder.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * @Author: yyp
 * @Date: 2024/4/5 - 04 - 05 - 11:01
 * @Description: com.nowcoder.community.util
 * @version: 1.0
 */
public class CommunityUtil {

    // 生成随机字符串
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // MD5 加密
    // 只能加密不能解密, hello -> abc123def456. 每次加密都是这个值
    // hello + 3e4a8, 这就是加盐 hello + 3e4a8 -> abc123def456abc, 加盐可以提高安全性, 防止字典破解
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }


}

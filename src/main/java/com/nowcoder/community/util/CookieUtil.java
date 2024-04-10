package com.nowcoder.community.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: yyp
 * @Date: 2024/4/10 - 04 - 10 - 15:07
 * @Description: com.nowcoder.community.util
 * @version: 1.0
 */
public class CookieUtil {

    public static String getValue(HttpServletRequest request, String name) {
        if (request == null || name == null) {
            throw new IllegalArgumentException("参数为空!");

        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();

                }
            }
        }

        return null;
    }

}

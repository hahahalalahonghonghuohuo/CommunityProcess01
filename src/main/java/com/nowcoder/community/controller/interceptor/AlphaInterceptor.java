package com.nowcoder.community.controller.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: yyp
 * @Date: 2024/4/10 - 04 - 10 - 14:10
 * @Description: com.nowcoder.community.controller.interceptor
 * @version: 1.0
 */

@Component
public class AlphaInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AlphaInterceptor.class);


    // 在 Controller 之前执行
    // Object handler 是我们要拦截的目标
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("preHandle: " + handler.toString());
        return true;
    }

    // 在 Controller 之后, TemplateEngine 之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.debug("postHandle: " + handler.toString());
    }

    // 在 TemplateEngine 之后执行(最后执行)
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.debug("afterCompletion: " + handler.toString());
    }
}

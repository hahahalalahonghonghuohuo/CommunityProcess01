package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: yyp
 * @Date: 2023/7/9 - 07 - 09 - 2:04
 * @Description: com.nowcoder.community.controller
 * @version: 1.0
 */
@Controller
@RequestMapping("/alpha")
public class AlphaController {

    // controller 依赖于 service，所以先将 service 注入给 controller
    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello Spring Boot";
    }

    // 模拟处理查询请求的方法，直接将查到的数据返回
    // 为了在浏览器上访问，要声明一下路径
    @RequestMapping("/data")
    // @ResponseBody注解，可以将方法的返回值直接输出到HTTP响应中，而不进行视图解析。
    // 一般用于返回JSON、XML或其他格式的数据。
    @ResponseBody
    public String getData() {
        return alphaService.find();
    }
}

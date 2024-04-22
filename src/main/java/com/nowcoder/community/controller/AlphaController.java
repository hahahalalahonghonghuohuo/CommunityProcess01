package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

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


    // 如何在 Spring MVC 框架下获得请求对象和响应对象
    // 请求对象中封装请求数据，响应对象中封装响应数据，分别用于处理请求和响应
    // 对底层组件进行封装后，从而可以实现相对复杂的方法
    @RequestMapping("/http")
    // 通过 response 对象可以向浏览器直接输出任何数据，所以没有返回值
    // DispatcherServlet 在调用该方法时会自动把 request 对象和 response 对象传入
    public void http(HttpServletRequest request, HttpServletResponse response) {

        /**
         * 通过 request 对象获取相关数据
         */

        // 获取请求数据
        // 这里取到数据后直接输出

        // 请求行
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());


        // 因为请求行有很多数据，所以这里进行封装
        // 请求行是 k-v 结构。这里通过 request.getHeaderNames() 获取所有请求行的 key
        // 存入 Enumeration 迭代器中
        Enumeration<String> enumeration = request.getHeaderNames();
        // 用 while 遍历迭代器中的内容


        // 请求的消息头(若干行的数据)
        while (enumeration.hasMoreElements()) {
            // 获取当前值(key，也即当前请求行的名字)
            String name = enumeration.nextElement();
            // 通过 request 获取 name 对应的 value
            String value = request.getHeader(name);
            System.out.println(name + ": " + value);
            System.out.println("----------------");
        }

        // 请求体中包含了业务数据，其实是包含了参数
        // 如果我们传入参数 code，这里就可以通过 request 获取这个参数

        // 如果访问 http://localhost:8080/community/alpha/http，打印 null null，因为没传参
        // 如果访问 http://localhost:8080/community/alpha/http?code=xxx，打印xxx null
        // 如果访问 http://localhost:8080/community/alpha/http?code=xxx&name=yyy，打印xxx yyy
        System.out.println(request.getParameter("code"));
        System.out.println(request.getParameter("name"));

        /**--------------------------------------------------*/

        /**
         * response 是用于向浏览器做出响应的对象(给浏览器返回响应数据的对象)
         */


        // 返回响应数据

        // 首先设置返回的数据的类型
        // 这里是返回网页类型的文本，且使用 utf-8 字符集，从而使得网页支持中文
        response.setContentType("text/html;charset=utf-8");

        // 使用 response 向浏览器响应网页，其实就是通过 response 中封装的输出流向浏览器输出

        try (
            // 获取输出流
            // 注意，这里需要处理异常，用 try-catch 包围
            PrintWriter writer = response.getWriter();
        ) {
            // 使用 writer 向浏览器打印一个网页
            // 这里输出了一级标题 "牛客网"
            writer.write("<h1>牛客网</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 这里有一个细节，PrintWriter writer = response.getWriter(); 在小括号()中
        // 编译时会自动加上一个 finally，把流关闭掉(调用 writer 的 close() 方法)

        // 总体来说，使用 request 和 response 的方法比较复杂，有更简单的方式
    }


    /**
     * 处理浏览器请求分为两个方面
     * 一个是接收请求的数据，基于 request
     * 一个是向浏览器返回响应数据，基于 response
     * 因此需要学习两方面内容，即如何接收请求数据，如何返回响应数据
     */


    /**
     * 请求的数据如何处理
     *
     */

    // (1) GET 请求

    // /students?current=1&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // /student/123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {
        System.out.println(id);
        return "a student";
    }

    // (2) POST 请求
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    // 响应 HTML 数据

    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "张三");
        mav.addObject("age", 30);
        mav.setViewName("/demo/view");
        return mav;
    }

    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model) {
        model.addAttribute("name", "北京大学");
        model.addAttribute("age", 80);
        return "/demo/view";
    }

    // 响应 JSON 数据（异步请求）
    // Java 对象 --> JSON 字符串 --> JS 对象
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmp() {
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 30);
        emp.put("salary", 8000.00);
        return emp;
    }

    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "李四");
        emp.put("age", 24);
        emp.put("salary", 9000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "王五");
        emp.put("age", 25);
        emp.put("salary", 10000.00);
        list.add(emp);

        return list;
    }

    // cookie 示例
    @RequestMapping(path = "/cookie/set", method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response) {
        // 创建一个 cookie
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
        // 设置 cookie 的生效的范围
        cookie.setPath("/community/alpha");
        // 设置 cookie 的生存时间
        cookie.setMaxAge(60 * 10);
        // 发送 cookie
        response.addCookie(cookie);

        return "set cookie";
    }

    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code) {
        System.out.println(code);
        return "get cookie";
    }

    // session 示例
    @RequestMapping(path = "/session/set", method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session) {
        session.setAttribute("id", 1);
        session.setAttribute("name", "Test");
        return "set session";
    }

    // 尤其在分布式部署场景下, 能用 cookie 就不用 session, 敏感数据存到数据库中, 然后给数据库做集群即可
    // 或者加上 Redis

    @RequestMapping(path = "/session/get", method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session) {
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get session";
    }

    /** ajax 示例 */
    @RequestMapping(path = "/ajax", method = RequestMethod.POST)
    @ResponseBody
    public String testAjax(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return CommunityUtil.getJSONString(0, "操作成功!");
    }


}

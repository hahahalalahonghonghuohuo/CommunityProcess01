package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Author: yyp
 * @Date: 2023/7/9 - 07 - 09 - 2:32
 * @Description: com.nowcoder.community.service
 * @version: 1.0
 */
// 演示 Spring 容器如何管理 bean 的生命周期，例如初始化、销毁等
@Service
// 默认是 @Scope("singleton")
@Scope("prototype")
public class AlphaService {

    // controller处理浏览器的请求，在这个过程中会调用业务组件service处理当前业务，
    // 业务组件会调用dao访问数据库。这种依赖关系就可以用依赖注入的方式来实现。

    // 将 AlphaDao 注入给 AlphaService
    @Autowired
    private AlphaDao alphaDao;

    // 构造器
    public AlphaService() {
        System.out.println("实例化AlphaService");
    }

    // 要想让容器管理这个方法，只需让容器在合适的时候自动调用这个方法
    // 所以要加上注解 @PostConstruct
    // 标明这个方法会在构造器之后调用
    // 注意：初始化方法通常在构造后调用，用于初始化某些数据
    @PostConstruct
    // bean 的初始化方法 init()
    public void init() {
        System.out.println("初始化AlphaService");
    }

    // Spring 容器还能管理 bean 的销毁方法
    // 该注解能让容器自动调用它
    // 调用时间在对象被销毁前
    @PreDestroy
    public void destroy() {
        // 如果不是为了 demo 演示方便，这里的代码应该是释放某些资源
        System.out.println("销毁AlphaService");
    }

    // 在注入了 AlphaDao，写一个 find() 方法模拟实现查询业务
    public String find() {
        // service 依赖于 dao，controller 依赖于 service
        // 这里是一个简单的 demo，直接将查询结果返回
        return alphaDao.select();
    }
}

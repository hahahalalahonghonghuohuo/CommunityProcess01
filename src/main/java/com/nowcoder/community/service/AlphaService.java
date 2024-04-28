package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;

/**
 * @Author: yyp
 * @Date: 2023/7/9 - 07 - 09 - 2:32
 * @Description: com.nowcoder.community.service
 * @version: 1.0
 */
// 演示 Spring 容器如何管理 bean 的生命周期，例如初始化、销毁等
@Service
// 默认是 @Scope("singleton")
//@Scope("prototype")
public class AlphaService {

    private static final Logger logger = LoggerFactory.getLogger(AlphaService.class);

    // controller处理浏览器的请求，在这个过程中会调用业务组件service处理当前业务，
    // 业务组件会调用dao访问数据库。这种依赖关系就可以用依赖注入的方式来实现。

    // 将 AlphaDao 注入给 AlphaService
    @Autowired
    private AlphaDao alphaDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

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

    // 注意事务的传播级别
    // REQUIRED: 支持当前事务(外部事务), 如果不存在则创建新事务
    // REQUIRES_NEW: 创建一个新事务, 并且暂停当前事务(外部事务)
    // NESTED: 如果当前存在事务(外部事务), 则嵌套在该事务中执行(独立的提交和回滚), 否则就和 REQUIRED 一样
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Object save1() {
        // 先增加用户, 再增加帖子
        // 1. 增加用户
        User user = new User();
        user.setUsername("alpha");
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5("123" + user.getSalt()));
        user.setEmail("alpha@qq.com");
        user.setHeaderUrl("http://image.nowcoder.com/head/99t.png");
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        // 2. 增加帖子
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle("Hello");
        post.setContent("新人报到!");
        post.setCreateTime(new Date());
        discussPostMapper.insertDiscussPost(post);

        Integer.valueOf("abc");

        return "ok";
    }

    public Object save2() {
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        return transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                // 先增加用户, 再增加帖子
                // 1. 增加用户
                User user = new User();
                user.setUsername("beta");
                user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
                user.setPassword(CommunityUtil.md5("123" + user.getSalt()));
                user.setEmail("beta@qq.com");
                user.setHeaderUrl("http://image.nowcoder.com/head/999t.png");
                user.setCreateTime(new Date());
                userMapper.insertUser(user);

                // 2. 增加帖子
                DiscussPost post = new DiscussPost();
                post.setUserId(user.getId());
                post.setTitle("你好");
                post.setContent("我是新人!");
                post.setCreateTime(new Date());
                discussPostMapper.insertDiscussPost(post);

                Integer.valueOf("abc");
                return "ok";
            }
        });
    }

    // @Async 让该方法在多线程的环境下被异步地调用
    @Async
    public void execute1() {
        logger.debug("execute1");
    }


    @Scheduled(initialDelay = 10000, fixedRate = 1000)
    public void execute2() {
        logger.debug("execute2");
    }


}

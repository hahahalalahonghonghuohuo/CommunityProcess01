package com.nowcoder.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * @Author: yyp
 * @Date: 2023/7/9 - 07 - 09 - 2:29
 * @Description: com.nowcoder.community.dao
 * @version: 1.0
 */
// MyBatis 比 Hibernate 在一些方面更有优势
// 如果我们想把 Hibernate 替换为 MyBatis
// 注意原本 Hibernate 的实现类我们没有删除，这就是 Spring 容器管理 Bean 的好处
@Repository
// @Primary 注解的目的在于标明这是我们期望的 bean，
// 这样 applicationContext.getBean() 通过类型获取时候就能获取到 我们期望的 bean 了 (具有更高优先级，会被优先装配)
@Primary
public class AlphaDaoMyBatisImpl implements AlphaDao{
    @Override
    public String select() {
        return "MyBatis";
    }
}

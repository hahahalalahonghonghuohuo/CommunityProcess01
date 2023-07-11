package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

/**
 * @Author: yyp
 * @Date: 2023/7/9 - 07 - 09 - 2:29
 * @Description: com.nowcoder.community.dao
 * @version: 1.0
 */
// AlphaDao 的一个实现类，采用 Hibernate 技术进行查询
// 为了让 Spring 容器扫描到这个 bean，得加上注解 @Repository("alphaHibernate")
@Repository("alphaHibernate")
public class AlphaDaoHibernateImpl implements AlphaDao{
    @Override
    public String select() {
        return "Hibernate";
    }
}

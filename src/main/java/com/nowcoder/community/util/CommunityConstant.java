package com.nowcoder.community.util;

/**
 * @Author: yyp
 * @Date: 2024/4/5 - 04 - 05 - 15:11
 * @Description: com.nowcoder.community.util
 * @version: 1.0
 */
public interface CommunityConstant {

    /**
     * 激活成功
     */
    // 常量通常用大写字母命名
    int ACTIVATION_SUCCESS = 0;

    /**
     * 重复激活
     */
    int ACTIVATION_REPEAT = 1;

    /**
     * 激活失败
     */
    int ACTIVATION_FAILURE = 2;

    /**
     * 默认状态的登录凭证的超时时间
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    /**
     * 记住状态下的登录凭证超时时间(100 天)
     */
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;

    /**
     * 实体类型: 帖子
     */
    int ENTITY_TYPE_POST = 1;

    /**
     * 实体类型: 评论
     */
    int ENTITY_TYPE_COMMENT = 2;


    /**
     * 实体类型: 用户
     */
    int ENTITY_TYPE_USER = 3;



}

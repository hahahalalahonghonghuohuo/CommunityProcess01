package com.nowcoder.community.dao;

import com.nowcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @Author: yyp
 * @Date: 2024/4/10 - 04 - 10 - 9:34
 * @Description: com.nowcoder.community.dao
 * @version: 1.0
 */

// @Mapper 注解表示是一个数据访问的对象, 需要容器来管理
@Mapper
@Deprecated
public interface LoginTicketMapper {

    @Insert({
          "insert into login_ticket(user_id,ticket,status,expired) ",
          "values(#{userId}, #{ticket}, #{status}, #{expired})"
    })
    // 声明 SQL 相关的机制。主键自动生成, 并且将生成的值注入到 id 中
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id, user_id, ticket, status, expired ",
            "from login_ticket where ticket=#{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

    // 注解中的 SQL 也支持动态 SQL
    @Update({
            "<script>",
            "update login_ticket set status=#{status} where ticket=#{ticket} ",
            "<if test=\"ticket!=null\"> ",
            "and 1=1 ",
            "</if>",
            "</script>"
    })
    // 实际应用中很少删除数据，通常是修改状态
    int updateStatus(String ticket, int status);


}

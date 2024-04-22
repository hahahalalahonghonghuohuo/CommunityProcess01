package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: yyp
 * @Date: 2024/3/13 - 03 - 13 - 17:01
 * @Description: com.nowcoder.community.dao
 * @version: 1.0
 */

@Mapper
public interface DiscussPostMapper {

    /** 查询帖子的方法 */
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);


    // @Param 注解用于给参数取别名
    // 如果方法只有一个参数, 并且在 <if> 中使用, 就必须加别名
    int selectDiscussPostRows(@Param("userId") int userId);

    /** 增加帖子的方法 */
    int insertDiscussPost(DiscussPost discussPost);








}

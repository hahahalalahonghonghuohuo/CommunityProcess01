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
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit, int orderMode);


    // @Param 注解用于给参数取别名
    // 如果方法只有一个参数, 并且在 <if> 中使用, 就必须加别名
    int selectDiscussPostRows(@Param("userId") int userId);

    /** 增加帖子的方法 */
    int insertDiscussPost(DiscussPost discussPost);

    /** 根据帖子 id 查询帖子的详细信息 */
    DiscussPost selectDiscussPostById(int id);

    int updateCommentCount(int id, int commentCount);

    /** 修改帖子的类型 */
    int updateType(int id, int type);

    /** 修改帖子的状态 */
    int updateStatus(int id, int status);

    /** 修改帖子的分数 */
    int updateScore(int id, double score);



}

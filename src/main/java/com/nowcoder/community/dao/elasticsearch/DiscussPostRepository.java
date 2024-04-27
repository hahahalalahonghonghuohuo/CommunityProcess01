package com.nowcoder.community.dao.elasticsearch;

import com.nowcoder.community.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: yyp
 * @Date: 2024/4/27 - 04 - 27 - 10:35
 * @Description: com.nowcoder.community.dao.elasticsearch
 * @version: 1.0
 */

@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost, Integer> {




}

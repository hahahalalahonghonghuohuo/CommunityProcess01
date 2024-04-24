package com.nowcoder.community.util;

/**
 * @Author: yyp
 * @Date: 2024/4/24 - 04 - 24 - 11:34
 * @Description: com.nowcoder.community.util
 * @version: 1.0
 */


public class RedisKeyUtil {

    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";

    // 生成某个实体的赞的 key
    // like:entity:entityType:entityId -> set(userId)
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

}

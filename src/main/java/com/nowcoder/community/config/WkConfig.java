package com.nowcoder.community.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @Author: yyp
 * @Date: 2024/4/29 - 04 - 29 - 8:40
 * @Description: com.nowcoder.community.config
 * @version: 1.0
 */

@Configuration
public class WkConfig {

    private static final Logger logger = LoggerFactory.getLogger(WkConfig.class);

    @Value("${wk.image.storage}")
    private String wkImageStorage;

    @PostConstruct
    public void init() {
        // 创建 WK 图片目录
        File file = new File(wkImageStorage);
        if (!file.exists()) {
            file.mkdir();
            logger.info("创建 WK 图片目录: " + wkImageStorage);
        }
    }



}

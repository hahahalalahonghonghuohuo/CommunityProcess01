package com.nowcoder.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: yyp
 * @Date: 2024/4/28 - 04 - 28 - 20:20
 * @Description: com.nowcoder.community.config
 * @version: 1.0
 */

@Configuration
@EnableScheduling
@EnableAsync
public class ThreadPoolConfig {
}

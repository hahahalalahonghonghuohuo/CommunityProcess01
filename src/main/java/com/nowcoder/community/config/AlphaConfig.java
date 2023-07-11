package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * @Author: yyp
 * @Date: 2023/7/9 - 07 - 09 - 2:25
 * @Description: com.nowcoder.community.config
 * @version: 1.0
 */
// 该注解用于标识配置类，程序入口的配置类用 @SpringBootApplication
@Configuration
public class AlphaConfig {

    // 定义第三方的 bean，需要在方法之前加上 @Bean 注解
    @Bean
    // 这里我们想把 Java 自带的 SimpleDateFormat 装配到容器当中
    // 这样我们把 SimpleDateFormat 实例化一次，装配到 Bean 中，就可以反复使用了
    // 这里的方法名 simpleDateFormat 就是 Bean 的名字
    public SimpleDateFormat simpleDateFormat() {
        // 该方法返回的对象将被装配到容器里
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

}

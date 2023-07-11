package com.nowcoder.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// CommunityApplication 就是一个配置文件
@SpringBootApplication
public class CommunityApplication {

	public static void main(String[] args) {
		// 底层启动了 tomcat 等
		// 且自动创建了 Spring 容器
		// 在 Web 中不需要主动创建 Spring 容器
		// 然后自动扫描某些包下的某些 bean，并装配到 Spring 容器中
		// CommunityApplication 本质上是一个配置文件
		SpringApplication.run(CommunityApplication.class, args);
	}

}

package com.zwx.shop.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = "com.zwx.shop.common.domain")
public class UserStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserStartApplication.class, args);
    }
}

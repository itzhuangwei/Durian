package com.zwx.shop.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient // 开启服务注册与发现
@EnableFeignClients // 开启OpenFeign
@EntityScan("com.zwx.shop.common.domain")
public class OrderStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderStartApplication.class, args);
    }
}

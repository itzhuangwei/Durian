package com.zwx.shop.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = "com.zwx.shop.common.domain")
public class ProductStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductStartApplication.class, args);
    }
}

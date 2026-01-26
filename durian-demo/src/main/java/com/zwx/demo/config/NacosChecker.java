package com.zwx.demo.config;

import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.stereotype.Component;

@Component
public class NacosChecker implements CommandLineRunner {

    @Resource
    private Registration registration;
    @Override
    public void run(String... args) throws Exception {
        if (registration != null) {
            System.out.println("服务信息: " + registration.getServiceId());
            System.out.println("主机: " + registration.getHost());
            System.out.println("端口: " + registration.getPort());
        } else {
            System.out.println("⚠️ Registration bean 未找到！");
        }
    }
}

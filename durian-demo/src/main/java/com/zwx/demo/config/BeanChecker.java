package com.zwx.demo.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class BeanChecker {

    @Resource
    private ApplicationContext context;

    @PostConstruct
    public void checkBeans() {
        // 检查是否有DiscoveryClient
        boolean hasDiscovery = context.containsBean("nacosServiceDiscovery");
        System.out.println("是否有NacosServiceDiscovery: " + hasDiscovery);

        // 列出所有相关Bean
        String[] beanNames = context.getBeanNamesForType(org.springframework.cloud.client.discovery.DiscoveryClient.class);
        System.out.println("DiscoveryClient beans: " + Arrays.toString(beanNames));
    }
}

package com.zwx.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 测试@Value注解获取Nacos配置信息
 * @RefreshScope: 刷新配置, 属于spring cloud的注解,用于标记需要动态刷新配置的Bean.
 * 当配置中心的配置发生变化时,被此注解标记的Bean会自动重新创建实例,从而获取最新的配置值,实现配置的热更新功能.
 */
@RestController
@RequestMapping("/nacos/annotation")
@RefreshScope
public class ValueAnnotationExample {

    @Value("${spring.cloud.nacos.config.serverAddr:}")
    private String serverAddr;

    @Value("${spring.cloud.nacos.config.prefix:}")
    private String prefix;

    @Value("${spring.cloud.nacos.config.group:}")
    private String group;

    @Value("${spring.cloud.nacos.config.namespace:}")
    private String namespace;

    @GetMapping
    public Map<String, String> getConfigInfo() {
        Map<String, String> result = new HashMap<>(4);
        result.put("serverAddr", serverAddr);
        result.put("prefix", prefix);
        result.put("group", group);
        result.put("namespace", namespace);
        return result;
    }
}

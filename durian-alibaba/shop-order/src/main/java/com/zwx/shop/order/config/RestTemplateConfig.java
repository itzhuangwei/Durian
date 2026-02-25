package com.zwx.shop.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    /**
     * 使用方式一:
     * 创建RestTemplate实例 : 传统阻塞模型
     *
     * @return
     */
    @LoadBalanced // 负载均衡
    @Bean
    public RestTemplate restTemplate () {
        return new RestTemplate();
    }

    /**
     * 使用方式二:
     * RestClient , spring6 推出来的新标准
     * 推荐使用,同步调用方式
     *
     * @return
     */
    @LoadBalanced // 负载均衡
    @Bean
    public RestClient.Builder restClientBuilder () {
        return RestClient.builder();
    }

    /**
     * 使用方式三: OpenFeign(声明式)
     * 这是开发效率最高的方式, SCA对Feign进行了封装
     * 1. 引入依赖 spring-cloud-starter-openfeign
     * 2. 创建接口, 添加@FeignClient注解
     * 3. 启动类添加@EnableFeignClients注解,开启注解
     */
}

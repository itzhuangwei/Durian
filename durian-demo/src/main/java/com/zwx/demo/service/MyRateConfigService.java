package com.zwx.demo.service;


import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import org.springframework.stereotype.Service;

@Service
public class MyRateConfigService {
    @NacosConfigListener(dataId = "durian-demo.yml", groupId = "DEFAULT_GROUP")
    public void rate(String rateConfig) {
        System.out.println("监听到配置文件更新: " + rateConfig);
    }
}

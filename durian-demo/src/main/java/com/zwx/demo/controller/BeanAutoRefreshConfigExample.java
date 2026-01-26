package com.zwx.demo.controller;

import com.zwx.demo.model.NacosConfigInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/nacos/bean")
public class BeanAutoRefreshConfigExample {

    @Resource
    private NacosConfigInfo nacosConfigInfo;

    @GetMapping
    public Map<String, String> getConfig() {
        return Map.of(
                "serverAddr", nacosConfigInfo.getServerAddr(),
                "prefix", nacosConfigInfo.getPrefix(),
                "group", nacosConfigInfo.getGroup(),
                "namespace", nacosConfigInfo.getNamespace()
        );
    }
}

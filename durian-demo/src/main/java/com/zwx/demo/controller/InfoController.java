package com.zwx.demo.controller;

import com.alibaba.cloud.nacos.annotation.NacosConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.zwx.demo.model.NacosConfigInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/demo")
@RefreshScope
public class InfoController {

    @Resource
    private Environment environment;
    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${logging.file.name}")
    private String logFileName;

    @Resource
    private NacosConfigInfo nacosConfigInfo;

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> getInfo() {
        // 获取服务名称
        //String applicationName = environment.getProperty("spring.application.name", "unknown");
        // 获取服务端口号（如果需要）
        String port = environment.getProperty("server.port", "8080");
        Map<String, String> infoMap = Map.of(
                "applicationName", applicationName,
                "port", port,
                "logFileName", logFileName
        );
        return ResponseEntity.ok(infoMap);
    }


    @GetMapping("/bean1")
    public Map<String, String> getConfigInfo() {
        Map<String, String> result = new HashMap<>();
        result.put("serverAddr", nacosConfigInfo.getServerAddr());
        result.put("prefix", nacosConfigInfo.getPrefix());
        result.put("group", nacosConfigInfo.getGroup());
        result.put("namespace", nacosConfigInfo.getNamespace());
        return result;
    }

    @Value("${rate}")
    private String rate;
    @NacosConfig(dataId = "durian-demo.yml", group = "DEFAULT_GROUP", key = "rate2")
    private String rate2;

    /**
     * 测试读取配置文件 - 两种方式: @Value 与 @NacosConfig
     * <p>
     * 1. @Value: 不支持动态更新,配合@RefreshScope支持动态更新
     * 2. @NacosConfig: 支持动态更新 spring cloud alibaba 2023.0.1.3+ 版本开始支持
     * 3. @NacosValue: 这个是老版本的配置方式, 不建议使用, SB3.X 以后不支持
     *
     * @return
     */
    @GetMapping("testReadConfigValue")
    public Map<String, String> testReadConfigValue() {
        Map<String, String> result = new HashMap<>();
        result.put("rate", rate);
        result.put("rate2", rate2);
        return result;
    }

    /**
     * 测试读取完整的配置文件
     *
     * 由这个,可以联想到类似于@ConfigurationProperties将配置文件内容映射成对象的功能
     */
    @NacosConfig(dataId = "durian-demo.yml", group = "DEFAULT_GROUP")
    private String fullConfigContent;

    @GetMapping("testReadFullConfig")
    public String testReadFullConfig() throws JsonProcessingException {
        System.out.println("fullConfigContent: " + fullConfigContent);
        // 将 yaml 配置内容转成json
        ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
        Map map = yamlMapper.readValue(fullConfigContent, Map.class);

        ObjectMapper jsonMapper = new ObjectMapper();
        return jsonMapper.writeValueAsString(map);
    }
}

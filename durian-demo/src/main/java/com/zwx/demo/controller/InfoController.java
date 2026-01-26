package com.zwx.demo.controller;

import com.zwx.demo.model.NacosConfigInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/demo")
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
}

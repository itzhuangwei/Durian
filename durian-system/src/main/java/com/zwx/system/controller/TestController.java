package com.zwx.system.controller;

import com.zwx.system.feign.TestFeign;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private TestFeign testFeign;

    @GetMapping("/get-rest")
    public String getInfoRest() {
        // 通过服务名调用 durian-demo 服务
        String url = "http://durian-demo/durian-demo/demo/info";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    @GetMapping("/get-feign")
    public ResponseEntity<Map<String, String>> getInfoFeign() {
        // 通过服务名调用 durian-demo 服务
        ResponseEntity<Map<String, String>> info = testFeign.getInfo();
        return info;
    }
}

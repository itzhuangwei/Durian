package com.zwx.system.feign.impl;

import com.zwx.system.feign.TestFeign;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Map;

public class TestFeignFallBack implements TestFeign {
    @Override
    public ResponseEntity<Map<String, String>> getInfo() {
        Map<String, String> map = Map.of(
                "message", "调用失败",
                "status", "500"
        );
        return ResponseEntity.ok(map);
    }
}

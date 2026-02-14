package com.zwx.system.feign;

import com.zwx.system.feign.impl.TestFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "durian-demo-serve", fallback = TestFeignFallBack.class)
public interface TestFeign {

    @GetMapping("/durian-demo/demo/info")
    ResponseEntity<Map<String, String>> getInfo();
}

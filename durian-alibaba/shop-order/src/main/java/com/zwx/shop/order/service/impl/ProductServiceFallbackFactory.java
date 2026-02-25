package com.zwx.shop.order.service.impl;

import com.zwx.shop.common.domain.Product;
import com.zwx.shop.order.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * 商品服务降级处理
 * @author zwx
 *
 * java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard.jar
 */
@Slf4j
@Component
public class ProductServiceFallbackFactory implements FallbackFactory<ProductService> {
    @Override
    public ProductService create(Throwable cause) {
        return new ProductService() {
            @Override
            public ResponseEntity<Product> get(Integer id) {
                log.error("商品服务调用失败:{}", cause.getMessage());
                // 这里编写降级的逻辑,比如返回缓存数据或友好提示
                ResponseEntity<Product> entity = ResponseEntity.status(500).build();
                return entity;
            }
        } ;
    }
}

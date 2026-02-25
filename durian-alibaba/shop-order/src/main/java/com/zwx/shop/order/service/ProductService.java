package com.zwx.shop.order.service;

import com.zwx.shop.common.domain.Product;
import com.zwx.shop.order.service.impl.ProductServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "shop-product-serve", path = "/shop-product", fallbackFactory = ProductServiceFallbackFactory.class)
public interface ProductService {

    // http://shop-product-serve/shop-product/product/get/
    @GetMapping("/product/get/{id}")
    public ResponseEntity<Product> get(@PathVariable("id") Integer id);
}

package com.zwx.shop.product.service.impl;

import com.zwx.shop.common.domain.Product;
import com.zwx.shop.product.dao.ProductDao;
import com.zwx.shop.product.service.ProductService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductDao productDao;

    @Override
    public Product findProductById(Integer id) {
        Optional<Product> optional = productDao.findById(id);
        return optional.orElse(null);
    }
}

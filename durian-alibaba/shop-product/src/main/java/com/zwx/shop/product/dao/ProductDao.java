package com.zwx.shop.product.dao;

import com.zwx.shop.common.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Integer> {
}

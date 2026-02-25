package com.zwx.shop.order.dao;

import com.zwx.shop.common.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order, Integer> {
}

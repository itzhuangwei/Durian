package com.zwx.shop.order.service.impl;

import com.zwx.shop.common.domain.Order;
import com.zwx.shop.order.dao.OrderDao;
import com.zwx.shop.order.service.OrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Override
    public void createOrder(Order order) {
        orderDao.save(order);
    }
}

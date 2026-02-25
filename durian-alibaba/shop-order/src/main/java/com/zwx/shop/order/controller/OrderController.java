package com.zwx.shop.order.controller;

import com.zwx.shop.common.domain.Order;
import com.zwx.shop.common.domain.Product;
import com.zwx.shop.common.dto.user.UserDTO;
import com.zwx.shop.order.service.OrderService;
import com.zwx.shop.order.service.ProductService;
import com.zwx.shop.order.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private OrderService orderService;
    @Resource
    private RestClient.Builder restClient;
    @Resource
    private ProductService productService;
    @Resource
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(Integer pid){
        // 进行商品查询
        // ResponseEntity<Product> productResponseEntity = restTemplate.getForEntity("http://127.0.0.1:28081/shop-product/product/get/" + pid, Product.class);
        //ResponseEntity<Product> productResponseEntity = restTemplate.getForEntity("http://shop-product-serve/shop-product/product/get/" + pid, Product.class);
        ResponseEntity<Product> productResponseEntity = productService.get(pid);

        Product product = null;
        if (productResponseEntity.getStatusCode().is2xxSuccessful()) {
            product = productResponseEntity.getBody();
        } else {
            return ResponseEntity.status(productResponseEntity.getStatusCode()).build();
        }

        // 用户查询
        //UserDTO body = restClient.build().get().uri("http://shop-user-serve/shop-user/user/get/" + 1).retrieve().body(UserDTO.class);

        //ResponseEntity<UserDTO> userResponseEntity = restTemplate.getForEntity("http://shop-user-serve/shop-user/user/get/" + 1, UserDTO.class);

        ResponseEntity<UserDTO> userResponseEntity = userService.get(1);

        if (userResponseEntity.getStatusCode().is2xxSuccessful()) {
            UserDTO userDTO = userResponseEntity.getBody();

            // 创建订单
            Order order = new Order();
            order.setUid(userDTO.getId());
            order.setPid(product.getPid());
            order.setPname(product.getPname());
            order.setPprice(Double.valueOf(product.getPprice()));
            order.setNumber(1);
            orderService.createOrder(order);
            return ResponseEntity.ok(order);
        }

        return ResponseEntity.status(userResponseEntity.getStatusCode()).build();
    }
}

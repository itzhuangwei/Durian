package com.zwx.shop.order.service;

import com.zwx.shop.common.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "shop-user-serve", path = "/shop-user")
public interface UserService {

    @GetMapping("/user/get/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable Integer id);
}

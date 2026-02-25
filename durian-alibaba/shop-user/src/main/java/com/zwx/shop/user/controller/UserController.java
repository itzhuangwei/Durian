package com.zwx.shop.user.controller;

import com.zwx.shop.common.domain.User;
import com.zwx.shop.common.dto.user.UserDTO;
import com.zwx.shop.user.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable Integer id) {
        User user = userService.queryUserById(id);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getUid());
        userDTO.setUsername(user.getUsername());
        userDTO.setTelephone(user.getTelephone());

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody User user) {
        userService.add(user);
        return ResponseEntity.ok("添加成功");
    }
}

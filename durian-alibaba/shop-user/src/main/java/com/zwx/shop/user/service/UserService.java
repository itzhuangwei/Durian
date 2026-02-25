package com.zwx.shop.user.service;

import com.zwx.shop.common.domain.User;

public interface UserService {

    User queryUserById(Integer id);

    void add(User user);
}

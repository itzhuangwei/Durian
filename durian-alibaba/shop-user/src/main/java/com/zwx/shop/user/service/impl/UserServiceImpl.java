package com.zwx.shop.user.service.impl;

import com.zwx.shop.common.domain.User;
import com.zwx.shop.user.dao.UserDao;
import com.zwx.shop.user.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *  用户服务实现类
 *
 * @author zwx
 * @date 2026/2/24 10:05
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User queryUserById(Integer id) {
        return userDao.getReferenceById(id);
    }

    @Override
    public void add(User user) {
        userDao.save(user);
    }
}

package com.zwx.shop.user.dao;

import com.zwx.shop.common.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {
}

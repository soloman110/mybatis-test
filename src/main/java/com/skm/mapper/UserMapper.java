package com.skm.mapper;

import java.util.List;

import com.skm.dao.entity.User;

public interface UserMapper {
    User findById(int id);
    List<User> findAll();
}
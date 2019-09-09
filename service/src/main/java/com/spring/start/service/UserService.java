package com.spring.start.service;

import com.spring.start.entity.User;

/**
 * Created by 50935 on 2019/9/9.
 */
public interface UserService {
    User getById(Long id);

    void update(User user);

    void insert(User user);
}

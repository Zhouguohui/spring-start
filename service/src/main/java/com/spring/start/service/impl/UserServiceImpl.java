package com.spring.start.service.impl;

import com.spring.start.entity.User;
import com.spring.start.mapper.UserDao;
import com.spring.start.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 50935 on 2019/9/9.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public void update(User user) {
       userDao.update(user);
    }

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }
}

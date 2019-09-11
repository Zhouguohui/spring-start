package com.spring.start.mapper;

import com.spring.start.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by 50935 on 2019/9/9.
 */
@Repository
public interface UserDao {

    User getById(Long id);

    void insert(User user);

    void update(User user);

}

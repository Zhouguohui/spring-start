package com.spring.start.mapper;

import com.spring.start.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by 50935 on 2019/9/9.
 */
@Mapper
public interface UserDao {

    User getById(Long id);

    void insert(User user);

    void update(User user);

}

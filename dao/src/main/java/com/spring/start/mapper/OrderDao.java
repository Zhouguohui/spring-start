package com.spring.start.mapper;

import com.spring.start.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 50935 on 2019/9/19.
 */
@Repository
public interface OrderDao {
    Order getById(Long id);

    void insert(Order order);

    List<Order> getAll();
}

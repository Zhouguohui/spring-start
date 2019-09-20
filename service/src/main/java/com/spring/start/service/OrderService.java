package com.spring.start.service;

import com.spring.start.entity.Order;

import java.util.List;

/**
 * Created by 50935 on 2019/9/19.
 */
public interface OrderService {
    Order getById(Long id);

    void insert(Order order);

    List<Order> getAll();
}

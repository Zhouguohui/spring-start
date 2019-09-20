package com.spring.start.service.impl;

import com.spring.start.entity.Order;
import com.spring.start.mapper.OrderDao;
import com.spring.start.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 50935 on 2019/9/19.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public Order getById(Long id) {
        return orderDao.getById(id);
    }

    @Override
    public void insert(Order order) {
        orderDao.insert(order);
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }
}

package com.spring.start.api.order;

import com.spring.start.cache.impl.TopUP;
import com.spring.start.entity.Order;
import com.spring.start.enums.RedisTypeEnum;
import com.spring.start.redis.impl.RedisUp;
import com.spring.start.result.DataReulst;
import com.spring.start.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 50935 on 2019/9/19.
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/insert")
    public DataReulst insert(){

        for(int i=0;i<10;i++){
            Order order = new Order();
            order.setOrderId(i+1);
            order.setOrderName("ordername"+i);
            orderService.insert(order);
        }
        return DataReulst.Success(1);
    }

    @GetMapping(value = "/getAll")
    public DataReulst getAll(){
        RedisUp.upFactory(RedisTypeEnum.test).set("123","456");
        System.out.println(RedisUp.upFactory(RedisTypeEnum.test).get("123"));
        RedisUp.upFactory(RedisTypeEnum.test).del("123");
        System.out.println(RedisUp.upFactory(RedisTypeEnum.test).get("123"));
        System.out.println(TopUP.upConfig("9527100010001"));
        System.out.println(TopUP.upInfo("2000100010001"));
       List<Order> list =  orderService.getAll();
       return DataReulst.Success(list);
    }

    @GetMapping(value = "/getById")
    public DataReulst getById(long id){
        Order order =  orderService.getById(id);
        return DataReulst.Success(order);
    }
}

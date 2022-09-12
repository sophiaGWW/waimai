package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Orders;

/**
 * @create 2022-09-11 12:29
 */
public interface OrderService extends IService<Orders> {
    void submit(Orders orders);

}

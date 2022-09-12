package com.example.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.OrderDetail;
import com.example.mapper.OrderDetailMapper;
import com.example.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @create 2022-09-11 12:30
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}

package com.example.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.ShoppingCart;
import com.example.mapper.ShoppingCartMapper;
import com.example.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @create 2022-09-10 21:12
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}

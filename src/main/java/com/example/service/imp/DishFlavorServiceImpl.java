package com.example.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.DishFlavor;
import com.example.mapper.DishFlavorMapper;
import com.example.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @create 2022-09-07 10:36
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}

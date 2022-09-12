package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.R;
import com.example.dto.DishDto;
import com.example.entity.Dish;

/**
 * @create 2022-09-06 2:14
 */
public interface DishService extends IService<Dish> {

    void saveWithFlavor(DishDto dishDto);

    DishDto getWithFlavor(Long id);

    void upDateWithFlavor(DishDto dishDto);
}

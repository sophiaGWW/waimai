package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.SetmealDto;
import com.example.entity.SetmealDish;

import java.util.List;

/**
 * @create 2022-09-09 20:03
 */
public interface SetmealDishService extends IService<SetmealDish> {
    void saveWithSetmealDish(SetmealDto setmealDto);

    void removeWithDish(List<Long> ids);
}

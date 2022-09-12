package com.example.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.DishDto;
import com.example.entity.Dish;
import com.example.entity.DishFlavor;
import com.example.mapper.DishMapper;
import com.example.service.DishFlavorService;
import com.example.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @create 2022-09-06 2:16
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //把dish表的数据存入表内
        this.save(dishDto);
        Long id = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        //把flavor表中的dishId存上
        for (DishFlavor flavor:flavors
             ) {
            flavor.setDishId(id);

        }

        //把dishflavor的数据存入表内
        dishFlavorService.saveBatch(flavors);
        //开启事务
    }

    @Override
    public DishDto getWithFlavor(Long id) {
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        //获取flavor数据
        //通过dishidselect
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId,id);
        List<DishFlavor> list = dishFlavorService.list(lqw);
        dishDto.setFlavors(list);


        return dishDto;

    }

    @Override
    @Transactional
    public void upDateWithFlavor(DishDto dishDto) {
        this.updateById(dishDto);

        //dish_flavor表的dish_id字段删除

        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(lqw);

        //传过来的flavor数据再insert进去
        List<DishFlavor> flavors = dishDto.getFlavors();
        //把flavor表中的dishId存上
        for (DishFlavor flavor:flavors
        ) {
            flavor.setDishId(dishDto.getId());

        }
        //insert
        dishFlavorService.saveBatch(flavors);


    }
}

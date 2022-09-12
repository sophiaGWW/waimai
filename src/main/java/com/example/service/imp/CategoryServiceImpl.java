package com.example.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.CustomerException;
import com.example.entity.Category;
import com.example.entity.Dish;
import com.example.entity.Setmeal;
import com.example.mapper.CategoryMapper;
import com.example.service.CategoryService;
import com.example.service.DishService;
import com.example.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @create 2022-09-05 22:17
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;


    @Override
    public boolean removeById(Serializable id) {
        //通过id判断改分类是否有菜品
        //select count(*) from dish where category_id = ?
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(lqw);
        if (count1 > 0){
            throw new CustomerException("当前分类下关联了菜品，不能删除");
        }
        //判断该分类是否有套餐
        LambdaQueryWrapper<Setmeal> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(lqw2);
        if (count2 > 0){
            throw new CustomerException("当前分类下关联了套餐，不能删除");
        }
        //调用remove
        super.removeById(id);
        return true;
    }
}

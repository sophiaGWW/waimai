package com.example.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.CustomerException;
import com.example.dto.SetmealDto;
import com.example.entity.Setmeal;
import com.example.entity.SetmealDish;
import com.example.mapper.SetmealDishMapper;
import com.example.service.SetmealDishService;
import com.example.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @create 2022-09-09 20:04
 */
@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {

    @Autowired
    private SetmealService setmealService;

    @Override
    @Transactional
    public void saveWithSetmealDish(SetmealDto setmealDto) {
        //把setmeal数据填入
        setmealService.save(setmealDto);
        //把setmeal_dish表填入
        //获取setmealdish表数据
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        //把每个条的setmealid填入
        for (SetmealDish item:setmealDishes
             ) {
            item.setSetmealId(setmealDto.getId());

        }

        this.saveBatch(setmealDishes);

    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        //查一下是否是售卖中，售卖中不能删除
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.in(Setmeal::getId,ids);
        lqw.eq(Setmeal::getStatus,1);
        int count = setmealService.count(lqw);
        if (count > 0){
            throw new CustomerException("套餐正在售卖中，不能删除");
        }

        setmealService.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> lqw1= new LambdaQueryWrapper<>();
        lqw1.in(SetmealDish::getSetmealId,ids);
        this.remove(lqw1);
    }
}

package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.CustomerException;
import com.example.common.R;
import com.example.dto.SetmealDto;
import com.example.entity.Category;
import com.example.entity.Employee;
import com.example.entity.Setmeal;
import com.example.entity.SetmealDish;
import com.example.service.CategoryService;
import com.example.service.SetmealDishService;
import com.example.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @create 2022-09-09 20:05
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealService setmealService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
            setmealDishService.saveWithSetmealDish(setmealDto);
        return R.success("添加成功");
    }

    /**
     * 套餐分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page (int page, int pageSize, String name){


        //分页构造器
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        //过滤构造器
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.hasText(name),Setmeal::getName,name);
        //排序
        lqw.orderByDesc(Setmeal::getUpdateTime);
        //执行查询
        setmealService.page(pageInfo,lqw);

        BeanUtils.copyProperties(pageInfo,dtoPage,"records");

        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item) ->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Category category = categoryService.getById(item.getCategoryId());
            String categoryName = category.getName();
            setmealDto.setCategoryName(categoryName);
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);

        return R.success(dtoPage);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){

        setmealDishService.removeWithDish(ids);

        return R.success("删除成功");
    }

  /*  @PostMapping("/{status}")
    public R<String> update(@PathVariable Integer status,@RequestParam List<Long> ids){
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();

       return null;
    }*/

    @GetMapping("/list")
    public R<List<Setmeal>> get(Long categoryId,Integer status){
        //根据categoryid查询套餐集合
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(categoryId !=null,Setmeal::getCategoryId,categoryId);
        lqw.eq(Setmeal::getStatus,status);
        lqw.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(lqw);

        return R.success(list);

    }
}

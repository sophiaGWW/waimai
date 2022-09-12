package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.dto.DishDto;
import com.example.entity.Category;
import com.example.entity.Dish;
import com.example.entity.DishFlavor;
import com.example.entity.Employee;
import com.example.service.CategoryService;
import com.example.service.DishFlavorService;
import com.example.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @create 2022-09-07 10:47
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 菜品分页查询
     *
     * @return
     */
    @GetMapping("/page")
    public R<Page> page (int page,int pageSize,String name){


        //分页构造器
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        //过滤构造器
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.like(name!=null,Dish::getName,name);

        //排序
        lqw.orderByDesc(Dish::getUpdateTime);
        //执行查询
        dishService.page(pageInfo,lqw);
        //复制pageInfo数据到dishDto中
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        //获取Dish集合
        List<Dish> records = pageInfo.getRecords();
       List<DishDto> list = records.stream().map((item) ->{

           DishDto dishDto = new DishDto();
           BeanUtils.copyProperties(item,dishDto);

           //通过Dish中的categoryId查询categoryName
           Long categoryId = item.getCategoryId();
           Category category = categoryService.getById(categoryId);
           if (category != null){
               String categoryName = category.getName();
               dishDto.setCategoryName(categoryName);

           }
           return dishDto;
       }).collect(Collectors.toList());




        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("添加成功");
    }

    //根据id获取菜品
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto = dishService.getWithFlavor(id);
        return R.success(dishDto);

    }
    //根据id获取菜品
    @GetMapping("/list")
    public R<List<DishDto>> getMeal( Dish dish){
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();

            lqw.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId());

            lqw.like(dish.getName() !=null,Dish::getName,dish.getName());

            lqw.eq(dish.getStatus() != null,Dish::getStatus,1);

        List<Dish> list = dishService.list(lqw);

        //List<DishDto> dtoList = new ArrayList<>();
      //  BeanUtils.copyProperties(list,dtoList);


        List<DishDto> dtoList=  list.stream().map((item) ->{
           Long dishId = item.getId();
           LambdaQueryWrapper<DishFlavor> lqw1 = new LambdaQueryWrapper<>();
           lqw1.eq(dishId != null, DishFlavor::getDishId,dishId);

           List<DishFlavor> dishFlavors = dishFlavorService.list(lqw1);
           DishDto dishDto = new DishDto();
           BeanUtils.copyProperties(item,dishDto);
           dishDto.setFlavors(dishFlavors);
            return dishDto;
       }).collect(Collectors.toList());




        return R.success(dtoList);

    }
    //将DishDto数据存入数据表中
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.upDateWithFlavor(dishDto);

        return R.success("更新成功");
    }

}

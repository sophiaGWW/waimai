package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.BaseContext;
import com.example.common.R;
import com.example.entity.ShoppingCart;
import com.example.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @create 2022-09-10 19:55
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService cartService;

    @GetMapping("/list")
    public R<List<ShoppingCart>> get(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.get());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = cartService.list(queryWrapper);

        return R.success(list);
    }
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){

        Long userId = BaseContext.get();
        shoppingCart.setUserId(userId);

        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> lqw =new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,userId);
        if (dishId != null){
            lqw.eq(ShoppingCart::getDishId,dishId);

        }else{
            lqw.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }

        ShoppingCart one = cartService.getOne(lqw);

        if (one != null){
            //number＋1
            one.setNumber((one.getNumber()+1));
            cartService.updateById(one);
        }else{
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            cartService.save(shoppingCart);
            one=shoppingCart;

        }



        return R.success(one);

    }

    @DeleteMapping("/clean")
    public R<String> clean(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.get());
        cartService.remove(queryWrapper);
        return R.success("删除成功");

    }

    @PostMapping("/sub")
    public R<List<ShoppingCart>> sub(@RequestBody ShoppingCart shoppingCart){
        Long dishId = shoppingCart.getDishId();
        Long userId = BaseContext.get();
        shoppingCart.setUserId(userId);
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();

        lqw.eq(ShoppingCart::getUserId,userId);

        if (dishId !=null){
            lqw.eq(ShoppingCart::getDishId,dishId);

        }else{
            lqw.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        //根据id查询一条数据，再查询数量，若大于1，则－1，若=1，则删除
        ShoppingCart one = cartService.getOne(lqw);
        Integer number = one.getNumber();
        if (number >1){
            one.setNumber(number-1);
            cartService.updateById(one);
        }else{
            cartService.remove(lqw);
        }

        R<List<ShoppingCart>> listR = this.get();
        return listR;
    }
}

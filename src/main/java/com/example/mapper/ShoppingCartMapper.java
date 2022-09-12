package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
 * @create 2022-09-10 21:10
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}

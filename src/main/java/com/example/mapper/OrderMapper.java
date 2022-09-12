package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @create 2022-09-11 12:28
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}

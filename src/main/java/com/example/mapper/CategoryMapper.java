package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @create 2022-09-05 22:15
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}

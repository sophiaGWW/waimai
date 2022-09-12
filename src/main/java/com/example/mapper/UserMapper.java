package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @create 2022-09-10 16:56
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}

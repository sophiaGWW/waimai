package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @create 2022-08-30 9:45
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}

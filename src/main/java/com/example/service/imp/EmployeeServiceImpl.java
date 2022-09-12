package com.example.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Employee;
import com.example.mapper.EmployeeMapper;
import com.example.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @create 2022-09-01 18:51
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}

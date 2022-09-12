package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.entity.Employee;
import com.example.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @create 2022-09-01 18:53
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login (HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //根据用户名查询数据库
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(lqw);
        //无用户名，返回失败结果
        if (emp == null){
            return R.error("用户名错误");
        }
        //有用户名，对比密码
        if(!(emp.getPassword().equals(password))){
            return R.error("密码错误");
        }
        //查看员工状态
        if (emp.getStatus()==0){
            return R.error("员工已禁用");
        }
        request.getSession().setAttribute("employee",emp.getId());

        return R.success(emp);

    }
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");

    }

    /**
     * 新镇员工
     * @param request
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        String password = DigestUtils.md5DigestAsHex("123456".getBytes());
        employee.setPassword(password);
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        Long id  = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(id);
//        employee.setUpdateUser(id);

        employeeService.save(employee);

        return R.success("新增员工成功");
    }

    /**
     * 员工信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page (int page,int pageSize,String name){
        log.info("page={},pageSize={},name={}",page,pageSize,name);

        //分页构造器
        Page pageInfo = new Page(page,pageSize);
        //过滤构造器
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.hasText(name),Employee::getName,name);
        //排序
        lqw.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(pageInfo,lqw);
        return R.success(pageInfo);
    }

    /**
     * 通过id更新员工信息
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
       /* employee.setUpdateTime(LocalDateTime.now());
        Long id  = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateUser(id);*/
        long id = Thread.currentThread().getId();
        log.info("线程id为：{}",id);
        employeeService.updateById(employee);
        return R.success("修改成功");
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("根据id查询员工信息");

        Employee employee = employeeService.getById(id);
        if (employee!= null){

            return R.success(employee);
        }
        return R.error("没有查到对应员工信息");

    }

}

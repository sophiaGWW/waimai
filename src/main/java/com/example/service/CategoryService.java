package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Category;

import java.io.Serializable;

/**
 * @create 2022-09-05 22:16
 */
public interface CategoryService extends IService<Category> {

    @Override
    boolean removeById(Serializable id);
}

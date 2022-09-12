package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @create 2022-09-10 17:48
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}

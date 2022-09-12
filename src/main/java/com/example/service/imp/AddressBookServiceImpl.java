package com.example.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.AddressBook;
import com.example.mapper.AddressBookMapper;
import com.example.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @create 2022-09-10 17:49
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}

package com.yuan.bookshop.service.impl;

import com.yuan.bookshop.dao.AccountMapper;
import com.yuan.bookshop.model.Account;
import com.yuan.bookshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    AccountMapper accountMapper;

    @Override
    public Account selectByPrimaryKey(Long id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    @Override
    public Account selectByName(String userName) {
        return accountMapper.selectByName(userName);
    }

    @Override
    public Account selectByEmail(String email) {
        return accountMapper.selectByEmail(email);
    }

    @Override
    public int insertSelective(Account record) {
        return accountMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Account record) {
        return accountMapper.updateByPrimaryKeySelective(record);
    }
}

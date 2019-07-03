package com.yuan.bookshop.service;

import com.yuan.bookshop.model.Account;

import java.util.List;

public interface UserService {
    Account selectByPrimaryKey(Long id);
    Account selectByName(String userName);
    Account selectByEmail(String email);
    int insertSelective(Account record);
    int updateByPrimaryKeySelective(Account record);
}

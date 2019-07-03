package com.yuan.bookshop.dao;

import com.yuan.bookshop.model.Account;
import org.apache.ibatis.annotations.Select;

public interface AccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Long id);

    @Select("SELECT * FROM account WHERE username = #{username}")
    Account selectByName(String username);

    @Select("SELECT * FROM account WHERE email = #{email}")
    Account selectByEmail(String email);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);
}
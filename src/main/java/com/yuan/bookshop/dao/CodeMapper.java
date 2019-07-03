package com.yuan.bookshop.dao;

import com.yuan.bookshop.model.Code;
import org.apache.ibatis.annotations.Select;

public interface CodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Code record);

    int insertSelective(Code record);

    Code selectByPrimaryKey(Long id);

    @Select("SELECT * FROM code WHERE email=#{email} AND type=#{type}")
    Code selectByEmailAndType(String email, int type);

    int updateByPrimaryKeySelective(Code record);

    int updateByPrimaryKey(Code record);
}
package com.yuan.bookshop.dao;

import com.yuan.bookshop.model.Wish;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WishMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Wish record);

    int insertSelective(Wish record);

    Wish selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Wish record);

    int updateByPrimaryKey(Wish record);

    @Select("select * from wanted order by create_time desc limit #{offset}, #{size}")
    List<Wish> selectRecentPage(int offset, int size);

    @Select("select Count(*) from wanted")
    int countAll();
}
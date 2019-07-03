package com.yuan.bookshop.dao;

import com.yuan.bookshop.model.Catalog;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CatalogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Catalog record);

    int insertSelective(Catalog record);

    Catalog selectByPrimaryKey(Long id);

    @Select("select * from catalog where level = #{level}")
    List<Catalog> selectByLevel(int level);

    @Select("select * from catalog where father_id = #{father}")
    List<Catalog> selectChildCatalog(long father);

    int updateByPrimaryKeySelective(Catalog record);

    int updateByPrimaryKey(Catalog record);
}
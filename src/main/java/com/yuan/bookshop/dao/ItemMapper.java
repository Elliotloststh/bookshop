package com.yuan.bookshop.dao;

import com.yuan.bookshop.model.Item;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Item record);

    int insertSelective(Item record);

    Item selectByPrimaryKey(Long id);

    @Select("select * from item where state=1 order by create_time desc limit #{offset}, #{size}")
    List<Item> selectRecentPage(int offset, int size);

    @Select("select * from item where catalog_id=#{catalogId} and state=1 order by create_time desc limit #{offset}, #{size}")
    List<Item> selectByCatalogPage(Long catalogId, int offset, int size);

    @Select("select * from item where father_catalog_id=#{catalogId} and state=1 order by create_time desc limit #{offset}, #{size}")
    List<Item> selectByFatherCatalogPage(Long catalogId, int offset, int size);

    @Select("select * from item where title like CONCAT('%',#{key},'%') and state=1 order by create_time desc limit #{offset}, #{size}")
    List<Item> searchByItemPage(String key, int offset, int size);

    @Select("select * from item where seller_id=#{sellerId} and state!=3 order by create_time desc limit #{offset}, #{size}")
    List<Item> selectBySellerPage(long sellerId, int offset, int size);

    @Select("select Count(*) from item where seller_id=#{sellerId} and state!=3")
    int countSeller(Long sellerId);

    @Select("select Count(*) from item where state=1")
    int countAll();

    @Select("select Count(*) from item where catalog_id=#{catalogId} and state=1")
    int countCatalog(Long catalogId);

    @Select("select Count(*) from item where title like CONCAT('%',#{key},'%') and state=1")
    int countSearch(String key);

    @Select("select Count(*) from item where father_catalog_id=#{catalogId} and state=1")
    int countFatherCatalog(Long catalogId);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);
}
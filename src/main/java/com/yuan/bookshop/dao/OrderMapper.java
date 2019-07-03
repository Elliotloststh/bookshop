package com.yuan.bookshop.dao;

import com.yuan.bookshop.model.Order;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    @Select("select * from tb_order where buyer_id=#{buyerId} order by create_time desc limit #{offset}, #{size}")
    List<Order> selectByBuyerPage(Long buyerId, int offset, int size);

    @Select("select Count(*) from tb_order where buyer_id=#{buyerId}")
    int countBuyer(Long buyerId);

    @Select("select * from tb_order where seller_id=#{sellerId} order by create_time desc limit #{offset}, #{size}")
    List<Order> selectBySellerPage(Long sellerId, int offset, int size);

    @Select("select Count(*) from tb_order where seller_id=#{sellerId}")
    int countSeller(Long sellerId);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}
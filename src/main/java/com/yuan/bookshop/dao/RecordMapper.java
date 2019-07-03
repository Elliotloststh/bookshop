package com.yuan.bookshop.dao;

import com.yuan.bookshop.model.Record;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Record record);

    int insertSelective(Record record);

    Record selectByPrimaryKey(Long id);

    @Select("select * from record where (sender_id=#{selfId} and receiver_id=#{oppositeId}) or (sender_id=#{oppositeId} and receiver_id=#{selfId}) order by time ASC")
    List<Record> selectAllMsg(Long selfId, Long oppositeId);

    @Select("select * from record where sender_id=#{senderId} and receiver_id=#{receiverId}")
    List<Record> selectBySenderAndReceiver(Long senderId, Long receiverId);

    int updateByPrimaryKeySelective(Record record);

    @Select("select count(*) from record where receiver_id=#{selfId} and is_read=0")
    int countAllUnread(Long selfId);

    @Select("select distinct sender_id from record where receiver_id=#{selfId}")
    List<Long> selectAllSenderId(Long selfId);

    @Select("select distinct receiver_id from record where sender_id=#{selfId}")
    List<Long> selectAllReceiverId(Long selfId);

    @Select("select count(*) from record where sender_id=#{oppositeId} and receiver_id=#{selfId} and is_read=0")
    int countUnread(Long selfId, Long oppositeId);

    int updateByPrimaryKey(Record record);
}
package com.yuan.bookshop.service;

import com.yuan.bookshop.dao.RecordMapper;
import com.yuan.bookshop.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {
    @Autowired
    RecordMapper recordMapper;

    public int insertSelective(Record record) {
        return recordMapper.insertSelective(record);
    }

    public Record selectByPrimaryKey(Long id) {
        return recordMapper.selectByPrimaryKey(id);
    }

    public List<Record> selectAllMsg(Long selfId, Long oppositeId) {
        return recordMapper.selectAllMsg(selfId,oppositeId);
    }

    public List<Record> selectBySenderAndReceiver(Long senderId, Long receiverId) {
        return recordMapper.selectBySenderAndReceiver(senderId, receiverId);
    }

    public int updateByPrimaryKeySelective(Record record) {
        return recordMapper.updateByPrimaryKeySelective(record);
    }

    public int countAllUnread(Long selfId) {
        return recordMapper.countAllUnread(selfId);
    }

    public int countUnread(Long selfId, Long oppositeId) {
        return recordMapper.countUnread(selfId, oppositeId);
    }

    public List<Long> selectAllSenderId(Long selfId) {
        return recordMapper.selectAllSenderId(selfId);
    }

    public List<Long> selectAllReceiverId(Long selfId) {
        return recordMapper.selectAllReceiverId(selfId);
    }
}

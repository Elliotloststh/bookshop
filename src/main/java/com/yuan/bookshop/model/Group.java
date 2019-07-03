package com.yuan.bookshop.model;

import lombok.Data;

import java.util.List;

@Data
public class Group {
    Long selfId;
    Long oppositeId;
    List<Record> allMsg;
    List<Record> receiveMsg;
    List<Record> sendMsg;
}

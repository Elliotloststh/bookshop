package com.yuan.bookshop.model;

import lombok.Data;

@Data
public class Record {
    private Long id;

    private Long senderId;

    private Long receiverId;

    private String content;

    private Long time;

    private Integer isRead;
}
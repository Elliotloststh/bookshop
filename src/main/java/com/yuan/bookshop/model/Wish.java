package com.yuan.bookshop.model;

import lombok.Data;

@Data
public class Wish {
    private Long id;

    private Long userId;

    private String title;

    private String message;

    private Long createTime;
}
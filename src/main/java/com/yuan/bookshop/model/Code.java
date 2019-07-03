package com.yuan.bookshop.model;

import lombok.Data;

@Data
public class Code {
    private Long id;

    private String email;

    private Integer type;

    private String code;

    private Long createTime;

    private Long updateTime;
}
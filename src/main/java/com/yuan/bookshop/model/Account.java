package com.yuan.bookshop.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Account implements Serializable {
    static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    private String avatar;

    private Integer gender;

    private String description;

    private String address;

    private Long createTime;
}
package com.yuan.bookshop.model;

import lombok.Data;

@Data
public class Catalog {
    private Long id;

    private String name;

    private Long fatherId;

    private Integer level;
}
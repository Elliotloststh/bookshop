package com.yuan.bookshop.model;

import lombok.Data;

@Data
public class Item {
    private Long id;

    private Long sellerId;

    private String isbn;

    private String title;

    private String author;

    private String press;

    private String cover;

    private Double originPrice;

    private Double price;

    private String description;

    private Long catalogId;

    private Long fatherCatalogId;

    private String brief;

    private String link;

    private String dictionary;

    private Integer deliveryType;

    private String address;

    private Integer state;

    private Long createTime;

    private Long updateTime;
}
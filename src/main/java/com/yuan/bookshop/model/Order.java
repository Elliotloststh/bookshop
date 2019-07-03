package com.yuan.bookshop.model;

import lombok.Data;

@Data
public class Order {
    private Long id;

    private Long itemId;

    private Long buyerId;

    private Long sellerId;

    private Double payment;

    private Integer paymentType;

    private String address;

    private Integer state;

    private String shippingName;

    private String shippingCode;

    private Long createTime;

    private Long updateTime;
}
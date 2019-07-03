package com.yuan.bookshop.controller;

import com.yuan.bookshop.Utils.ApiResponse;
import com.yuan.bookshop.model.Account;
import com.yuan.bookshop.model.Item;
import com.yuan.bookshop.model.Order;
import com.yuan.bookshop.service.ItemService;
import com.yuan.bookshop.service.OrderService;
import com.yuan.bookshop.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @GetMapping("/orderInfo/{id}")
    public ApiResponse getOrderInfo(@PathVariable("id") Long id) {
        Order order = orderService.selectByPrimaryKey(id);
        ApiResponse response = ApiResponse.ok();
        Account account = userService.selectByPrimaryKey(order.getSellerId());
        response.putDataValue("sellerName", account.getUsername());
        response.putDataValue("shippingName", order.getShippingName());
        response.putDataValue("shippingCode", order.getShippingCode());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        response.putDataValue("createTime",simpleDateFormat.format(new Date(order.getCreateTime()*1000)));
        return response;
    }

    @GetMapping("/orderInfo2/{id}")
    public ApiResponse getOrderInfo2(@PathVariable("id") Long id) {
        Order order = orderService.selectByPrimaryKey(id);
        ApiResponse response = ApiResponse.ok();
        Account account = userService.selectByPrimaryKey(order.getBuyerId());
        response.putDataValue("buyerName", account.getUsername());
        response.putDataValue("shippingName", order.getShippingName());
        response.putDataValue("shippingCode", order.getShippingCode());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        response.putDataValue("createTime",simpleDateFormat.format(new Date(order.getCreateTime()*1000)));
        return response;
    }

    @PostMapping("/confirmReceive")
    public ApiResponse confirmReceive(@RequestBody confirmReceiveInput input) {
        long orderId = input.getOrderId();
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        Order order = orderService.selectByPrimaryKey(orderId);
        if(!order.getBuyerId().equals(userId)) {
            ApiResponse response = ApiResponse.forbidden();
            response.putDataValue("msg","你没有权限");
        }
        Order record = new Order();
        record.setId(order.getId());
        record.setState(2);
        if(orderService.updateByPrimaryKeySelective(record) ==1 ) {
            return ApiResponse.ok();
        } else {
            ApiResponse response = ApiResponse.serverInternalError();
            response.putDataValue("msg","服务器开小差了，请重试~");
            return response;
        }
    }

    @PostMapping("/sendGood")
    public ApiResponse sendGood(@RequestBody sendGoodInput input) {
        long orderId = input.getOrderId();
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        Order order = orderService.selectByPrimaryKey(orderId);
        if(!order.getSellerId().equals(userId)) {
            ApiResponse response = ApiResponse.forbidden();
            response.putDataValue("msg","你没有权限");
        }
        Order record = new Order();
        record.setShippingName(input.getShippingName());
        record.setShippingCode(input.getShippingCode());
        record.setId(order.getId());
        record.setState(1);
        if(orderService.updateByPrimaryKeySelective(record) ==1 ) {
            return ApiResponse.ok();
        } else {
            ApiResponse response = ApiResponse.serverInternalError();
            response.putDataValue("msg","服务器开小差了，请重试~");
            return response;
        }
    }

    @PostMapping("/buyBook")
    public ApiResponse buyBook(@RequestBody buyBookInput input) {
        Item item = itemService.selectByPrimaryKey(input.getItemId());
        Order order = new Order();
        order.setItemId(item.getId());
        order.setSellerId(item.getSellerId());
        order.setBuyerId(Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName()));
        order.setPayment(input.getPayment());
        order.setPaymentType(input.getDeliveryType());
        order.setAddress(input.getAddress());
        order.setState(input.getDeliveryType()==1 ? 0 : 1);
        order.setCreateTime(System.currentTimeMillis()/1000);
        order.setUpdateTime(System.currentTimeMillis()/1000);
        if (orderService.insert(order) == 1) {
            return ApiResponse.ok();
        } else {
            ApiResponse response = ApiResponse.serverInternalError();
            response.putDataValue("msg", "哎呀，服务器开小差了");
            return response;
        }
    }

    @Data
    public static class buyBookInput {
        Long itemId;
        Double payment;
        Integer deliveryType;
        String address;
    }

    @Data
    public static class confirmReceiveInput {
        Long orderId;
    }

    @Data
    public static class sendGoodInput {
        Long orderId;
        String shippingName;
        String shippingCode;
    }
}

package com.yuan.bookshop.Constant;

import java.util.HashMap;
import java.util.Map;

public class ItemConstant {
    public static Map<Integer, String> deliveryTypeMap;
    public static Map<Integer, String> stateTypeMap;
    static
    {
        deliveryTypeMap = new HashMap<>();
        deliveryTypeMap.put(1,"寄送");
        deliveryTypeMap.put(2,"线下交易");
        deliveryTypeMap.put(3,"寄送/线下交易");

        stateTypeMap = new HashMap<>();
        stateTypeMap.put(1,"上架");
        stateTypeMap.put(2,"下架");
        stateTypeMap.put(3,"删除");
    }
}

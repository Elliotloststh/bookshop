package com.yuan.bookshop.Constant;

import java.util.HashMap;
import java.util.Map;

public class OrderConstant {
    public static Map<Integer, String> paymentTypeMap;
    public static Map<Integer, String> stateTypeMap;
    static
    {
        paymentTypeMap = new HashMap<>();
        paymentTypeMap.put(1,"寄送");
        paymentTypeMap.put(2,"线下交易");

        stateTypeMap = new HashMap<>();
        stateTypeMap.put(0,"未发货");
        stateTypeMap.put(1,"配送中");
        stateTypeMap.put(2,"已收货");
    }
}

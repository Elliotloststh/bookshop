package com.yuan.bookshop.Constant;

public enum ValidationCode {
    REGISTER("注册账号验证码",0),
    RETRIEVE("找回密码验证码",1),
    ;
    private String desc;
    private int value;

    ValidationCode(String desc, int type) {
        this.desc = desc;
        this.value = type;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

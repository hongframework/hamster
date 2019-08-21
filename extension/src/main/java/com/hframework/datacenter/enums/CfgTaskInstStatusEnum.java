package com.hframework.datacenter.enums;

/**
 * Created by zhangquanhong on 2016/10/12.
 */
public enum CfgTaskInstStatusEnum {

    INIT((byte)0,"未启用"),
    RUNNING((byte)1,"正常"),
    SUSPEND((byte)2,"暂停"),
    EXCEPTION((byte)3,"执行异常"),
    STOP((byte)-1,"关闭");
    Byte value;
    String text;

    CfgTaskInstStatusEnum(Byte value, String text) {
        this.value = value;
        this.text = text;
    }

    public Byte getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}

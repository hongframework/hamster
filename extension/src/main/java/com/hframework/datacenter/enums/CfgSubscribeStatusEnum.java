package com.hframework.datacenter.enums;

/**
 * Created by zhangquanhong on 2016/10/12.
 */
public enum CfgSubscribeStatusEnum {

    INIT(1,"未启用"),
    RUNNING(2,"启用"),
    SUSPEND(3,"暂停"),
    STOP(4,"关闭");
    int value;
    String text;

    CfgSubscribeStatusEnum(int value,String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}

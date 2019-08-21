package com.hframework.datacenter.enums;

/**
 * Created by zhangquanhong on 2016/10/12.
 */
public enum CfgSubscribeDataOperTypeEnum {

    DML(1,"Insert/Update/Delete"),
    INSERT(2,"Insert"),
    UPDATE(3,"Update"),
    DELETE(4,"Delete"),
    INSERT_UPDATE(5,"Insert/Update");
    int value;
    String text;

    CfgSubscribeDataOperTypeEnum(int value, String text) {
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

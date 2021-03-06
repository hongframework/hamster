package com.hframe.hamster.node.cannal.bean;

/**
 * Created by zhangquanhong on 2016/9/29.
 */
public enum SyncMode {
    /** 行记录 */
    ROW("R"),
    /** 字段记录 */
    FIELD("F");

    private String value;

    SyncMode(String value){
        this.value = value;
    }

    public static SyncMode valuesOf(String value) {
        SyncMode[] modes = values();
        for (SyncMode mode : modes) {
            if (mode.value.equalsIgnoreCase(value)) {
                return mode;
            }
        }

        throw new RuntimeException("unknow SyncMode : " + value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isRow() {
        return this.equals(SyncMode.ROW);
    }

    public boolean isField() {
        return this.equals(SyncMode.FIELD);
    }
}
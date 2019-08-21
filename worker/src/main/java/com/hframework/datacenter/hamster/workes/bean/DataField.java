package com.hframework.datacenter.hamster.workes.bean;

public class DataField {
    private int index;
    private int columnType;
    private String columnName;
//    private String columnValue; //timestamp,Datetime是一个long型的数字.
    private boolean isNull;
    private boolean isKey;
    private String function;
    private String dftVal;

    public DataField() {
    }

    public DataField(String columnName, boolean isKey) {
        this.columnName = columnName;
        this.isKey = isKey;
    }

    public DataField(String columnName, int columnType, boolean isKey) {
        this.columnType = columnType;
        this.columnName = columnName;
        this.isKey = isKey;
    }

    public DataField(String columnName, int columnType, boolean isKey, String function) {
        this.columnType = columnType;
        this.columnName = columnName;
        this.isKey = isKey;
        this.function = function;
    }

    public DataField(String columnName, int columnType, boolean isKey, String function, String dftVal) {
        this.columnType = columnType;
        this.columnName = columnName;
        this.isKey = isKey;
        this.function = function;
        this.dftVal = dftVal;
    }

    public DataField(int index, int columnType, String columnName, boolean isNull, boolean isKey) {
        this.index = index;
        this.columnType = columnType;
        this.columnName = columnName;
//        this.columnValue = columnValue;
        this.isNull = isNull;
        this.isKey = isKey;
    }

    public DataField(int index, int columnType, String columnName, boolean isNull, boolean isKey, String function, String dftVal) {
        this.index = index;
        this.columnType = columnType;
        this.columnName = columnName;
        this.isNull = isNull;
        this.isKey = isKey;
        this.function = function;
        this.dftVal = dftVal;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getColumnType() {
        return columnType;
    }

    public void setColumnType(int columnType) {
        this.columnType = columnType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    public boolean isKey() {
        return isKey;
    }

    public void setKey(boolean key) {
        isKey = key;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getDftVal() {
        return dftVal;
    }

    public void setDftVal(String dftVal) {
        this.dftVal = dftVal;
    }
}

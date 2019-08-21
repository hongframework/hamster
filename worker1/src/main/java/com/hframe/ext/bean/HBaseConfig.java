package com.hframe.ext.bean;

public class HBaseConfig {
    private long configId;

    private String zkList;

    private String zkPort;

    private String tableName;

    private String rowKeyword;

    private Long rowKeywordConfigId;

    public long getConfigId() {
        return configId;
    }

    public void setConfigId(long configId) {
        this.configId = configId;
    }

    public String getZkList() {
        return zkList;
    }

    public void setZkList(String zkList) {
        this.zkList = zkList;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRowKeyword() {
        return rowKeyword;
    }

    public void setRowKeyword(String rowKeyword) {
        this.rowKeyword = rowKeyword;
    }

    public Long getRowKeywordConfigId() {
        return rowKeywordConfigId;
    }

    public void setRowKeywordConfigId(Long rowKeywordConfigId) {
        this.rowKeywordConfigId = rowKeywordConfigId;
    }

    public String getZkPort() {
        return zkPort;
    }

    public void setZkPort(String zkPort) {
        this.zkPort = zkPort;
    }
}
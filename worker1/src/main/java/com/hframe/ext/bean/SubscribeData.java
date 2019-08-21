package com.hframe.ext.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hframe.hamster.node.cannal.bean.EventType;

import java.util.Map;

/**
 * Created by zhangquanhong on 2016/10/21.
 */
public class SubscribeData {

    private String schemaName;
    private String tableName;
    private EventType eventType;
    private String executeTime;
    private String tablePosition;
    private String subscribeId;

    private Long subscribeDetailId;
    private String loadTime;

    private Map<String, DataItem> columns;

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    public String getTablePosition() {
        return tablePosition;
    }

    public void setTablePosition(String tablePosition) {
        this.tablePosition = tablePosition;
    }

    public String getSubscribeId() {
        return subscribeId;
    }

    public void setSubscribeId(String subscribeId) {
        this.subscribeId = subscribeId;
    }

    public Map<String, DataItem> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, DataItem> columns) {
        this.columns = columns;
    }

    public static class DataItem{
        private String columnName;
        private int columnType;
        private String columnValue;
        private boolean update;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String oldColumnValue;

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public int getColumnType() {
            return columnType;
        }

        public void setColumnType(int columnType) {
            this.columnType = columnType;
        }

        public String getColumnValue() {
            return columnValue;
        }

        public void setColumnValue(String columnValue) {
            this.columnValue = columnValue;
        }

        public boolean isUpdate() {
            return update;
        }

        public void setUpdate(boolean update) {
            this.update = update;
        }

        public String getOldColumnValue() {
            return oldColumnValue;
        }

        public void setOldColumnValue(String oldColumnValue) {
            this.oldColumnValue = oldColumnValue;
        }
    }

    public String getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(String loadTime) {
        this.loadTime = loadTime;
    }

    public Long getSubscribeDetailId() {
        return subscribeDetailId;
    }

    public void setSubscribeDetailId(Long subscribeDetailId) {
        this.subscribeDetailId = subscribeDetailId;
    }
}

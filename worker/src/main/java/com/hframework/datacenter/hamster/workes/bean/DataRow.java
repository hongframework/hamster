package com.hframework.datacenter.hamster.workes.bean;

import com.hframe.hamster.node.cannal.bean.EventType;

import java.util.ArrayList;
import java.util.List;

public class DataRow {
    private List<String> values;
    private List<String> oldValues;
    private List<Boolean> updates;
    private EventType eventType;//变更数据的业务类型(I/U/D/C/A/E),与canal中的EntryProtocol中定义的EventType一致.
    private long executeTime;//变更数据的业务时间
    private String binLogPosition;

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = new ArrayList<>(values);
    }

    public List<String> getOldValues() {
        return oldValues;
    }

    public void setOldValues(List<String> oldValues) {
        this.oldValues = new ArrayList<>(oldValues);
    }

    public List<Boolean> getUpdates() {
        return updates;
    }

    public void setUpdates(List<Boolean> updates) {
        this.updates = new ArrayList<>(updates);
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }

    public String getBinLogPosition() {
        return binLogPosition;
    }

    public void setBinLogPosition(String binLogPosition) {
        this.binLogPosition = binLogPosition;
    }

    public DataRow clone(){
        DataRow row = new DataRow();
        row.setEventType(eventType);
        row.setExecuteTime(executeTime);
        row.setBinLogPosition(binLogPosition);
        row.setValues(values);
        row.setOldValues(oldValues);
        row.setUpdates(updates);
        return row;
    }
}




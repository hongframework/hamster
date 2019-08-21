package com.hframework.datacenter.hamster.workes.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BatchDataSet {
    private Long batchId;
    private Date createTime;
    private Long binlogStartTs;                         //binlog start时间
    private Long binlogEndTs;                           //binlog end时间
    private String binlogStart;                         //binlog start位置
    private String binlogEnd;                           //binlog end位置
    private List<DataSet> datas = new ArrayList<>();

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<DataSet> getDatas() {
        return datas;
    }

    public void setDatas(List<DataSet> datas) {
        this.datas = datas;
    }

    public DataSet getOrCreateDataSet(long tableId, String tableName, String schemaName) {
        for (DataSet data : datas) {
            if(data.getTableId() == tableId && data.getTableName().equals(tableName)
                    && data.getSchemaName().equals(schemaName)) {
                return data;
            }
        }
        DataSet newDataSet = new DataSet(tableId, tableName, schemaName);
        datas.add(newDataSet);
        return newDataSet;
    }

    public String getBinlogStart() {
        return binlogStart;
    }

    public void setBinlogStart(String binlogStart) {
        this.binlogStart = binlogStart;
    }

    public String getBinlogEnd() {
        return binlogEnd;
    }

    public void setBinlogEnd(String binlogEnd) {
        this.binlogEnd = binlogEnd;
    }

    public BatchDataSet clone(){
        BatchDataSet batchDataSet = new BatchDataSet();
        batchDataSet.setBatchId(batchId);
        batchDataSet.setCreateTime(createTime);
        batchDataSet.setBinlogStart(binlogStart);
        batchDataSet.setBinlogEnd(binlogEnd);
        batchDataSet.setBinlogStartTs(binlogStartTs);
        batchDataSet.setBinlogEndTs(binlogEndTs);
        batchDataSet.setDatas(datas);
        return batchDataSet;
    }

    public Long getBinlogStartTs() {
        return binlogStartTs;
    }

    public void setBinlogStartTs(Long binlogStartTs) {
        this.binlogStartTs = binlogStartTs;
    }

    public Long getBinlogEndTs() {
        return binlogEndTs;
    }

    public void setBinlogEndTs(Long binlogEndTs) {
        this.binlogEndTs = binlogEndTs;
    }
}

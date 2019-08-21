package com.hframe.ext.bean;

public class MQConfig {
    private long configId;

    private String brokerAddrList;

    private String cfgTopicCode;

    private Byte partitions;

    private Byte partitionStrategy;

    private String partitionKey;

    public MQConfig() {
        super();
    }

    public MQConfig(long configId, String brokerAddrList, String cfgTopicCode, Byte partitions, Byte partitionStrategy, String partitionKey) {
        this.configId = configId;
        this.brokerAddrList = brokerAddrList;
        this.cfgTopicCode = cfgTopicCode;
        this.partitions = partitions;
        this.partitionStrategy = partitionStrategy;
        this.partitionKey = partitionKey;
    }

    public long getConfigId() {
        return configId;
    }

    public void setConfigId(long configId) {
        this.configId = configId;
    }


    public Byte getPartitionStrategy() {
        return partitionStrategy;
    }

    public void setPartitionStrategy(Byte partitionStrategy) {
        this.partitionStrategy = partitionStrategy;
    }

    public String getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    public String getBrokerAddrList() {
        return brokerAddrList;
    }

    public void setBrokerAddrList(String brokerAddrList) {
        this.brokerAddrList = brokerAddrList;
    }

    public String getCfgTopicCode() {
        return cfgTopicCode;
    }

    public void setCfgTopicCode(String cfgTopicCode) {
        this.cfgTopicCode = cfgTopicCode;
    }

    public Byte getPartitions() {
        return partitions;
    }

    public void setPartitions(Byte partitions) {
        this.partitions = partitions;
    }
}
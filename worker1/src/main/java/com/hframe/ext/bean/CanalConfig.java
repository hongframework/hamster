package com.hframe.ext.bean;

import com.alibaba.otter.canal.instance.manager.model.CanalParameter;

/**
 * Created by zhangquanhong on 2016/10/9.
 */
public class CanalConfig {

    private Long canalId;
    private String destination;
    private String filter = "*.*";
    private Integer batchSize = 20;
    private Long batchTimeout  = 3 * 1000L;

    private short clientId ;

    private String prototypeKey;

    private CanalParameter canalParameter;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public Long getBatchTimeout() {
        return batchTimeout;
    }

    public void setBatchTimeout(Long batchTimeout) {
        this.batchTimeout = batchTimeout;
    }

    public CanalParameter getCanalParameter() {
        return canalParameter;
    }

    public void setCanalParameter(CanalParameter canalParameter) {
        this.canalParameter = canalParameter;
    }

    public Long getCanalId() {
        return canalId;
    }

    public void setCanalId(Long canalId) {
        this.canalId = canalId;
    }

    public short getClientId() {
        return clientId;
    }

    public void setClientId(short clientId) {
        this.clientId = clientId;
    }

    public String getPrototypeKey() {
        return prototypeKey;
    }

    public void setPrototypeKey(String prototypeKey) {
        this.prototypeKey = prototypeKey;
    }
}

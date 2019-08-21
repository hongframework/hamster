package com.hframe.hamster.node.cannal.bean;

public class HeartBreak {

    private volatile Long fetchTimeStamp = -1L;
    private volatile Long executeTimeStamp = -1L;
    private volatile Long delayOccurTimeStamp = -1L;

    public Long getFetchTimeStamp() {
        return fetchTimeStamp;
    }

    public void setFetchTimeStamp(Long fetchTimeStamp) {
        this.fetchTimeStamp = fetchTimeStamp;
    }

    public Long getExecuteTimeStamp() {
        return executeTimeStamp;
    }

    public void setExecuteTimeStamp(Long executeTimeStamp) {
        this.executeTimeStamp = executeTimeStamp;
    }

    public void reset() {
        fetchTimeStamp = -1L;
        executeTimeStamp = -1L;
    }

    @Override
    public String toString() {
        return "HeartBreak{" +
                "fetchTimeStamp=" + fetchTimeStamp +
                ", executeTimeStamp=" + executeTimeStamp +
                '}';
    }

    public void setDelay(){
        if (!isDelay()) {
            this.delayOccurTimeStamp = fetchTimeStamp;
        }
    }

    public void removeDelay(){
        this.delayOccurTimeStamp = -1L;
    }

    public Boolean isDelay() {
        return delayOccurTimeStamp > 0;
    }

    public Long getDelayOccurTimeStamp() {
        return delayOccurTimeStamp;
    }
}

package com.hframe.hamster.node.monitor.bean;

/**
 * Created by zhangquanhong on 2016/9/26.
 */
public class MainStemData {

    private boolean active = false;
    private Long nodeId;
    private PrototypeKey prototypeKey;
    private MainStemStatus mainStemStatus ;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public PrototypeKey getPrototypeKey() {
        return prototypeKey;
    }

    public void setPrototypeKey(PrototypeKey prototypeKey) {
        this.prototypeKey = prototypeKey;
    }

    public MainStemStatus getMainStemStatus() {
        return mainStemStatus;
    }

    public void setMainStemStatus(MainStemStatus mainStemStatus) {
        this.mainStemStatus = mainStemStatus;
    }

    public enum MainStemStatus{
        /**已追上*/
        OVERTAKE,
        /**追赶中*/
        TAKEING;

        public boolean isOverTake(){
            return OVERTAKE.equals(this);
        }
    }
}

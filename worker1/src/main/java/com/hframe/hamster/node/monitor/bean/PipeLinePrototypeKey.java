package com.hframe.hamster.node.monitor.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangquanhong on 2016/9/29.
 */
public class PipeLinePrototypeKey implements PrototypeKey {

    private Long pipeLineId;

//    private String taskKey;
    private PipeLinePrototypeKey(){}

    private PipeLinePrototypeKey(Long pipeLineId){
        this.pipeLineId = pipeLineId;
    }

    public static PipeLinePrototypeKey value(Long pipeLineId) {
        return new PipeLinePrototypeKey(pipeLineId);
    }

    @Override
    public String toString() {
        return String.valueOf(pipeLineId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PipeLinePrototypeKey) {
            PipeLinePrototypeKey target = (PipeLinePrototypeKey) obj;
            return this.pipeLineId.equals(target.getPipeLineId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return pipeLineId.hashCode();
    }

    public Long getPipeLineId() {
        return pipeLineId;
    }

    public void setPipeLineId(Long pipeLineId) {
        this.pipeLineId = pipeLineId;
    }

    @Override
    public String value() {
        return String.valueOf(pipeLineId);
    }

    public static void main(String[] args) {
        Map map = new HashMap<>();
        map.put(PipeLinePrototypeKey.value(12L),"");
        map.put(PipeLinePrototypeKey.value(12L),"");
        map.put(PipeLinePrototypeKey.value(12L),"");
        System.out.println(map.size());

    }
}

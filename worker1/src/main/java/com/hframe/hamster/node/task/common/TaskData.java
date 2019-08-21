package com.hframe.hamster.node.task.common;

import com.hframe.hamster.node.cannal.Message;
import com.hframe.hamster.node.monitor.bean.ProcessEventData;
import com.hframe.hamster.node.monitor.bean.SharableEventData;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public class TaskData<T> {
    private T message;
    private Date createTime;
    private SharableEventData eventData;
    private List<String> jobs;

    public TaskData(){
    }

    public TaskData(T message, SharableEventData eventData) {
        this.message = message;
        this.eventData = eventData;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public ProcessEventData getEventData() {
        return eventData;
    }

    public void setEventData(SharableEventData eventData) {
        this.eventData = eventData;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isEmpty(){
        if(this.getMessage() == null) return true;
        if(this.getMessage() instanceof Message) {
            if(((Message)this.getMessage()).getDatas() == null) return true;
            if(((Message)this.getMessage()).getDatas().size() == 0)return true;
        }
        return false;
    }

    public List<String> getJobs() {
        return jobs;
    }

    public void setJobs(List<String> jobs) {
        this.jobs = jobs;
    }
}

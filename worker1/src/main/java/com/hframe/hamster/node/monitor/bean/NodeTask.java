package com.hframe.hamster.node.monitor.bean;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public class NodeTask{
    private TaskEvent taskEvent;
    private TaskType taskType;
    private FlowKey flowKey;
    private PrototypeKey prototypeKey;

    public TaskEvent getTaskEvent() {
        return taskEvent;
    }

    public void setTaskEvent(TaskEvent taskEvent) {
        this.taskEvent = taskEvent;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public FlowKey getFlowKey() {
        return flowKey;
    }

    public void setFlowKey(FlowKey flowKey) {
        this.flowKey = flowKey;
    }

    public PrototypeKey getPrototypeKey() {
        return prototypeKey;
    }

    public void setPrototypeKey(PrototypeKey prototypeKey) {
        this.prototypeKey = prototypeKey;
    }

    public boolean isCreate() {
        return taskEvent.isCreate();
    }
    public boolean isDelete() {
        return taskEvent.isDelete();
    }


    @Override
    public boolean equals(Object obj) {
        NodeTask target = (NodeTask) obj;
        return target.getTaskEvent().equals(this.getTaskEvent())
                && target.getTaskType().equals(this.getTaskType())
                && target.getFlowKey().equals(this.getFlowKey())
                && target.getPrototypeKey().equals(this.getPrototypeKey()) ;
    }

    public static class TaskType {

        private Class clazz;
        public TaskType(Class clazz) {
            this.clazz = clazz;
        }

        public Class getClazz() {
            return clazz;
        }

        public void setClazz(Class clazz) {
            this.clazz = clazz;
        }

        @Override
        public boolean equals(Object obj) {
            return this.clazz == ((TaskType)obj).getClazz();
        }

        @Override
        public int hashCode() {
            return clazz.hashCode();
        }
    }

    public enum TaskEvent{
        CREATE, DELETE;

        public boolean isCreate(){
            return this.equals(CREATE);
        }

        public boolean isDelete(){
            return this.equals(DELETE);
        }

    }

}
/*
 * Copyright (C) 2010-2101 Alibaba Group Holding Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hframe.hamster.node.monitor.bean;

import java.util.Map;

/**
 * 基于process数据的信号对象
 * 
 * @author ** 2011-8-16 下午08:19:16
 */
public class ProcessEventData extends PrototypeKeyEventData {

    private static final long serialVersionUID = 3384175022262480571L;
    private Long processId;                              // 同步进程id
    private Long startTime;                              // 同步开始时间
    private Long endTime;                                // 同步结束时间
    private Long firstTime;                              // 第一条记录的时间
    private Long lastTime;                              // 最后一条记录的时间

    private String binlogStart;                         //binlog start位置
    private String binlogEnd;                           //binlog end位置


    private Long batchId;                                // 批处理Id，对应一批处理的数据
    private Long number;                                 // 对应调度的记录数
    private Long size;                                   // 对应调度的数据大小
    private Map exts;                                   // 对应的扩展数据

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Long firstTime) {
        this.firstTime = firstTime;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Map getExts() {
        return exts;
    }

    public void setExts(Map exts) {
        this.exts = exts;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
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

    public Long getLastTime() {
        return lastTime;
    }

    public void setLastTime(Long lastTime) {
        this.lastTime = lastTime;
    }
}

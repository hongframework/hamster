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

import com.hframe.hamster.node.cannal.bean.EventData;

/**
 * process node节点使用的数据对象
 * 
 * @author ** 2011-12-1 下午06:23:40
 * @version 4.0.0
 */
public class ProcessNodeEventData extends EventData {

    private static final long serialVersionUID = -7622558087796345197L;

    public enum Status {
        /** 已使用 */
        USED,
        /** 未使用 */
        UNUSED;

        public boolean isUsed() {
            return this == USED;
        }

        public boolean isUnUsed() {
            return this == UNUSED;
        }
    }

    private Long nid;
    private Status        status;
    private ArbitrateMode mode = ArbitrateMode.ZOOKEEPER;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getNid() {
        return nid;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    public ArbitrateMode getMode() {
        return mode;
    }

    public void setMode(ArbitrateMode mode) {
        this.mode = mode;
    }

    public static enum ArbitrateMode {
        /** 内存调度 */
        MEMORY,
        /** rpc调度 */
        RPC,
        /** zk watcher调度 */
        ZOOKEEPER,
        /** 自动选择 */
        AUTOMATIC;

        public boolean isMemory() {
            return this.equals(ArbitrateMode.MEMORY);
        }

        public boolean isRpc() {
            return this.equals(ArbitrateMode.RPC);
        }

        public boolean isZookeeper() {
            return this.equals(ArbitrateMode.ZOOKEEPER);
        }

        public boolean isAutomatic() {
            return this.equals(ArbitrateMode.AUTOMATIC);
        }
    }
}

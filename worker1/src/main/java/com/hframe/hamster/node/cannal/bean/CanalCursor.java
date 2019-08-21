package com.hframe.hamster.node.cannal.bean;

import java.util.HashMap;

/**
 * @author songge
 * @version 2018-12-04
 */
public class CanalCursor {

    public static class Identity {

        public static class SourceAddress {
            private volatile String address;
            private volatile Long port;

            public String getAddress() {
                return this.address;
            }

            public Long getPort() {
                return this.port;
            }
        }

        private volatile Long slaveId;
        private volatile SourceAddress sourceAddress;

        public Long getSlaveId() {
            return this.slaveId;
        }

        public String getAddress() {
            return this.sourceAddress.getAddress();
        }

        public Long getPort() {
            return this.sourceAddress.getPort();
        }
    }

    public static class Postion {
        private volatile Boolean included;
        private volatile String journalname;
        private volatile Long position;
        private volatile Long serverId;
        private volatile Long timestamp;

        public Boolean getIncluded() {
            return this.included;
        }

        public String getJournalname() {
            return this.journalname;
        }

        public Long getPosition() {
            return this.position;
        }

        public Long getServerId() {
            return this.serverId;
        }

        public Long getTimestamp() {
            return this.timestamp;
        }
    }

    private volatile Identity identity;
    private volatile Postion postion;

    public Identity getIdentity() {
        return this.identity;
    }

    public Postion getPostion() {
        return this.postion;
    }

    public Long getTimestamp() {
        return this.postion.getTimestamp();
    }
}

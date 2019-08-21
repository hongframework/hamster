package com.hframe.hamster.node.monitor.bean;

public class SequenceData {
        private Long lastSequence;

        private Long lastProcessId;

    public SequenceData() {
    }

    public SequenceData(Long lastSequence, Long lastProcessId) {
        this.lastSequence = lastSequence;
        this.lastProcessId = lastProcessId;
    }

    public Long getLastProcessId() {
            return lastProcessId;
        }

        public void setLastProcessId(Long lastProcessId) {
            this.lastProcessId = lastProcessId;
        }

        public Long getLastSequence() {
            return lastSequence;
        }

        public void setLastSequence(Long lastSequence) {
            this.lastSequence = lastSequence;
        }
    }
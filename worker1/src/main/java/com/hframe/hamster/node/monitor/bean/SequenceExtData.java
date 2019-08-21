package com.hframe.hamster.node.monitor.bean;

import java.util.Map;

public class SequenceExtData extends SequenceData {


    private Map<String, Long> extSequences;

    public SequenceExtData() {
    }

    public SequenceExtData(Long lastSequence, Long lastProcessId) {
        super(lastSequence, lastProcessId);
    }

    public Map<String, Long> getExtSequences() {
        return extSequences;
    }

    public void setExtSequences(Map<String, Long> extSequences) {
        this.extSequences = extSequences;
    }
}
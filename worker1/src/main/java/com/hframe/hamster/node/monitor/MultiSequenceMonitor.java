package com.hframe.hamster.node.monitor;

import com.alibaba.fastjson.JSON;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.monitor.bean.SequenceExtData;
import com.hframe.hamster.node.zk.ZKPathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 监听zk的数据变化，针对于数据变化调用listener进行通知
 * Created by zhangquanhong on 2016/9/25.
 */
public class MultiSequenceMonitor extends SequenceMonitor implements Monitor{
    private static final Logger logger = LoggerFactory.getLogger(MultiSequenceMonitor.class);

    public MultiSequenceMonitor(String taskStory, PrototypeKey prototypeKey) {
        super(taskStory, prototypeKey, new SequenceExtData(-1L, 0L));
    }

    public Map<String, Long> waitForMultiSequence(Long processId) throws InterruptedException {
        logger.info("wait sequence for {}", processId);
        logger.info("last sequence by {}", this.sequenceData.getLastProcessId() );
        if(this.sequenceData.getLastProcessId() + 1 == processId){
            return ((SequenceExtData) this.sequenceData).getExtSequences();
        }else {
            synchronized (mutex) {
                if(this.sequenceData.getLastProcessId() + 1 != processId){
                    mutex.set(false);
                }
            }
            mutex.get();
            return waitForMultiSequence(processId);
        }

    }

    public void updateSequence(Long processId, Map<String, Long> extSequences) throws InterruptedException {
        SequenceExtData sequenceData = new SequenceExtData();
        sequenceData.setLastProcessId(processId);
        sequenceData.setExtSequences(extSequences);
        zkClientx.writeData(ZKPathUtils.getProcessRootPath(taskStory, prototypeKey.value()), JSON.toJSONString(sequenceData));
    }

}

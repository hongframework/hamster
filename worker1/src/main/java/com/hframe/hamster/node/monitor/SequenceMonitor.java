package com.hframe.hamster.node.monitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.common.utils.BooleanMutex;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.monitor.bean.SequenceData;
import com.hframe.hamster.node.monitor.listener.PermitListener;
import com.hframe.hamster.node.zk.ZKPathUtils;
import org.I0Itec.zkclient.IZkDataListener;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 监听zk的数据变化，针对于数据变化调用listener进行通知
 * Created by zhangquanhong on 2016/9/25.
 */
public class SequenceMonitor extends PrototypeMonitor implements Monitor{
    private static final Logger logger = LoggerFactory.getLogger(SequenceMonitor.class);

    protected SequenceData sequenceData = new SequenceData(-1L, 0L);

    protected BooleanMutex mutex = new BooleanMutex(false);

    private List<PermitListener> listeners = Collections.synchronizedList(new ArrayList<PermitListener>());


    private IZkDataListener sequenceChangeListener = new IZkDataListener() {
        @Override
        public void handleDataChange(String dataPath, Object data) throws Exception {
            initSequence((byte[]) data);
        }

        @Override
        public void handleDataDeleted(String dataPath) throws Exception {
        }
    };



    public SequenceMonitor(String taskStory, PrototypeKey prototypeKey) {
        this(taskStory, prototypeKey, null);
    }

    public SequenceMonitor(String taskStory, PrototypeKey prototypeKey, SequenceData sequenceData) {
        super(taskStory, prototypeKey);
        if(sequenceData != null) {
            this.sequenceData = sequenceData;
        }
        zkClientx.subscribeDataChanges(ZKPathUtils.getProcessRootPath(taskStory, prototypeKey.value()), sequenceChangeListener);

        initSequence();
    }


    public void reload(){
        initSequence();
    }

    @Override
    public void startInternal() {

    }

    public void destroy(){
        zkClientx.unsubscribeDataChanges(ZKPathUtils.getProcessRootPath(taskStory, prototypeKey.value()), sequenceChangeListener);
    }

    @Override
    public void destroyInternal() {

    }

    private void initSequence() {
        String path = ZKPathUtils.getProcessRootPath(taskStory, prototypeKey.value());
        try {
            initSequence((byte[]) zkClientx.readData(path));
        }catch (Exception e) {
            logger.error("init Main Stem State error, {}", ExceptionUtils.getFullStackTrace(e));

        }

    }

    private void initSequence(byte[] data) {
        this.sequenceData =  JSON.parseObject(data, sequenceData.getClass());
        mutex.set(true);
    }

    public long waitForSequence(Long processId) throws InterruptedException {
        logger.info("wait sequence for {}", processId);
        logger.info("last sequence by {}", sequenceData.getLastProcessId() );
        if(sequenceData.getLastProcessId() + 1 == processId){
            return sequenceData.getLastSequence() + 1;
        }else {
            synchronized (mutex) {
                if(sequenceData.getLastProcessId() + 1 != processId){
                    mutex.set(false);
                }
            }
            mutex.get();
            return waitForSequence(processId);
        }

    }

    public void updateSequence(Long processId, Long sequence) throws InterruptedException {
        SequenceData sequenceData = new SequenceData();
        sequenceData.setLastProcessId(processId);
        sequenceData.setLastSequence(sequence);
        zkClientx.writeData(ZKPathUtils.getProcessRootPath(taskStory, prototypeKey.value()), JSON.toJSONString(sequenceData));
    }

}

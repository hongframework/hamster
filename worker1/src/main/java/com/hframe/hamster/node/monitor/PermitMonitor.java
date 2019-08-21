package com.hframe.hamster.node.monitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.common.utils.BooleanMutex;
import com.hframe.hamster.node.monitor.bean.MainStemData;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.monitor.listener.PermitListener;
import com.hframe.hamster.node.zk.ZKPathUtils;
import org.I0Itec.zkclient.IZkDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 监听zk的数据变化，针对于数据变化调用listener进行通知
 * Created by zhangquanhong on 2016/9/25.
 */
public class PermitMonitor extends PrototypeMonitor implements Monitor {
    private static final Logger logger = LoggerFactory.getLogger(PermitMonitor.class);

    private PrototypeStatus prototypeStatus = PrototypeStatus.STOP;
    private MainStemData.MainStemStatus mainStemStatus = MainStemData.MainStemStatus.TAKEING;

    private BooleanMutex channelMutex = new BooleanMutex(false);
    private BooleanMutex permitMutex = new BooleanMutex(false);

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final long DEFAULT_DELAY_TIME = 5;

    private List<PermitListener> listeners = Collections.synchronizedList(new ArrayList<PermitListener>());

    private ExecutorService executorService;


    private IZkDataListener prototypeChangeListener = new IZkDataListener() {
        @Override
        public void handleDataChange(String dataPath, Object data) throws Exception {
            initStoryPrototypeState((byte[]) data);
        }

        @Override
        public void handleDataDeleted(String dataPath) throws Exception {
            prototypeStatus = PrototypeStatus.STOP;
        }
    };

    private IZkDataListener mainStemChangeListener = new IZkDataListener() {
        @Override
        public void handleDataChange(String dataPath, Object data) throws Exception {
            initMainStemState((byte[]) data);
        }

        @Override
        public void handleDataDeleted(String dataPath) throws Exception {
            mainStemStatus = MainStemData.MainStemStatus.TAKEING;
        }
    };

    public PermitMonitor(String taskStory, PrototypeKey prototypeKey) {
        super(taskStory, prototypeKey);

        zkClientx.subscribeDataChanges(ZKPathUtils.getStoryInstanceRootPath(taskStory, prototypeKey.value()), prototypeChangeListener);
        zkClientx.subscribeDataChanges(ZKPathUtils.getMainStemPath(taskStory, prototypeKey.value()), mainStemChangeListener);

        initStoryPrototypeState();
        initMainStemState();
    }

    private void initMainStemState(byte[] data) {
        MainStemData mainStemData = JSON.parseObject(data, MainStemData.class);
        if(!mainStemStatus.equals( mainStemData.getMainStemStatus())) {
            mainStemStatus = mainStemData.getMainStemStatus();
            permitSem();
        }

    }

    public PrototypeStatus getPrototypeStatus() {
        return prototypeStatus;
    }




    public void reload(){
        initStoryPrototypeState();
        initMainStemState();
    }

    @Override
    public void startInternal() {

    }

    public void destroy(){
        logger.info("permit stem monitor destroy ..");
        scheduler.shutdownNow();
        zkClientx.unsubscribeDataChanges(ZKPathUtils.getStoryInstanceRootPath(taskStory, prototypeKey.value()), prototypeChangeListener);
        zkClientx.unsubscribeDataChanges(ZKPathUtils.getMainStemPath(taskStory, prototypeKey.value()), mainStemChangeListener);
    }

    @Override
    public void destroyInternal() {

    }

    private void initMainStemState() {
//        String path = ZKPathUtils.getMainStemPath(taskStory, prototypeKey.value());
//        try {
//            initMainStemState((byte[]) zkClientx.readData(path));
//        }catch (Exception e) {
//            logger.error("init Main Stem State error, {}", ExceptionUtils.getFullStackTrace(e));
//            scheduler.schedule(new Runnable() {
//                @Override
//                public void run() {
//                    initMainStemState();
//                }
//            }, DEFAULT_DELAY_TIME, TimeUnit.SECONDS);
//        }

    }

    private void initStoryPrototypeState() {
        String path = ZKPathUtils.getStoryInstanceRootPath(taskStory, prototypeKey.value());
        while (!zkClientx.exists(path)){
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        initStoryPrototypeState((byte[]) zkClientx.readData(path));

    }

    private void initStoryPrototypeState(byte[] data) {
        PrototypeStatus newPrototypeStatus = JSON.parseObject(data, PrototypeStatus.class);
        if(newPrototypeStatus != prototypeStatus) {
            prototypeStatus = newPrototypeStatus;
            permitSem();
        }
    }

    private void permitSem() {
        channelMutex.set(prototypeStatus.isStart());
        boolean isPermit = prototypeStatus.isStart() /*&& mainStemStatus.isOverTake() TODO */ ;
        permitMutex.set(isPermit);
        notifyListener(isPermit);
    }

    private void notifyListener(final boolean isPermit) {
        for (final PermitListener listener : listeners) {
//            listener.processChange(isPermit);
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    listener.processChange(isPermit);
                }
            });
        }
    }

    public void waitPermit() throws InterruptedException {
        permitMutex.get();
    }

    public void addListener(PermitListener listener) {
        listeners.add(listener);
    }

    public enum PrototypeStatus {
        /**运行中*/
        START,
        /**暂停中*/
        PAUSE,
        /**已停止**/
        STOP;

        public boolean isStart(){
            return START.equals(this);
        }
    }


}

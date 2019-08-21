package com.hframe.hamster.node.monitor.listener;

import java.util.List;

/**
 * Created by zhangquanhong on 2016/9/26.
 */
public interface TaskProcessListener {

    /**
     * process内容批量变化列表
     * @param processIds
     */
    public void processChange(List<Long> processIds);

    /**
     * process单个变化
     * @param processId
     * @param currentStages
     */
    public void processChange(Long processId, List<String> currentStages) throws Exception;

    /**
     * process删除
     * @param processId
     */
    public void processDeleted(Long processId);


}

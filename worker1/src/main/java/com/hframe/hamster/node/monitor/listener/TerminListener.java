package com.hframe.hamster.node.monitor.listener;

import java.util.List;

/**
 * Created by zhangquanhong on 2016/9/26.
 */
public interface TerminListener {

    /**
     * process内容批量变化列表
     * @param processIds
     */
    public void processChange(List<Long> processIds);

}

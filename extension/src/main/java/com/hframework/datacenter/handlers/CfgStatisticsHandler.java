package com.hframework.datacenter.handlers;


import com.hframework.web.extension.AbstractBusinessHandler;
import com.hframework.web.extension.annotation.AfterCreateHandler;
import com.hframework.web.extension.annotation.AfterDeleteHandler;
import com.hframework.web.extension.annotation.BeforeUpdateHandler;
import com.hframework.datacenter.enums.CfgSubscribeStatusEnum;
import com.hframework.datacenter.enums.CfgTaskInstStatusEnum;
import com.hframework.hamster.cfg.domain.model.*;
import com.hframework.hamster.cfg.service.interfaces.ICfgNodeSV;
import com.hframework.hamster.cfg.service.interfaces.ICfgNodeTaskRelatSV;
import com.hframework.hamster.cfg.service.interfaces.ICfgTaskDefSV;
import com.hframework.hamster.cfg.service.interfaces.ICfgTaskInstSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangquanhong on 2016/9/23.
 */
@Service
public class CfgStatisticsHandler extends AbstractBusinessHandler<CfgStatistics> {

    private static final Logger logger = LoggerFactory.getLogger(CfgStatisticsHandler.class);

    @Resource
    private ICfgTaskInstSV cfgTaskInstSV;

    @Resource
    private ICfgNodeSV cfgNodeSV;

    @Resource
    private ICfgNodeTaskRelatSV cfgNodeTaskRelatSV;

    @Resource
    private ICfgTaskDefSV cfgTaskDefSV;

    @AfterCreateHandler
    public boolean create(CfgStatistics cfgStatistics) throws Exception {
        CfgTaskDef cfgTaskDef = cfgTaskDefSV.getCfgTaskDefByPK(2L);
        CfgTaskInst taskInst = new CfgTaskInst();
        taskInst.setCfgTaskDefId(2L);
        if(cfgStatistics.getStatus() == CfgSubscribeStatusEnum.INIT.getValue()) {
            taskInst.setStatus(CfgTaskInstStatusEnum.INIT.getValue());
        }else if(cfgStatistics.getStatus() == CfgSubscribeStatusEnum.RUNNING.getValue()) {
            taskInst.setStatus(CfgTaskInstStatusEnum.RUNNING.getValue());
        }else if(cfgStatistics.getStatus() == CfgSubscribeStatusEnum.SUSPEND.getValue()) {
            taskInst.setStatus(CfgTaskInstStatusEnum.SUSPEND.getValue());
        }else{
            taskInst.setStatus(CfgTaskInstStatusEnum.STOP.getValue());
        }
        taskInst.setCreatorId(cfgStatistics.getCreatorId());
        taskInst.setCreateTime(cfgStatistics.getCreateTime());
        taskInst.setParamValue1(String.valueOf(cfgStatistics.getCfgStatisticsId()));
        taskInst.setParamValueRemark1(cfgStatistics.getCfgStatisticsName());
        taskInst.setCfgTaskInstDesc("【" + cfgTaskDef.getCfgTaskDefName() + "】" + cfgStatistics.getCfgStatisticsName());
        int i = cfgTaskInstSV.create(taskInst);

        Long cfgTaskInstId = taskInst.getCfgTaskInstId();
        List<CfgNode> cfgNodeAll = cfgNodeSV.getCfgNodeAll();
        if(cfgNodeAll != null) {
            for (CfgNode cfgNode : cfgNodeAll) {
                CfgNodeTaskRelat cfgNodeTaskRelat = new CfgNodeTaskRelat();
                cfgNodeTaskRelat.setCfgNodeId(cfgNode.getCfgNodeId());
                cfgNodeTaskRelat.setCfgTaskInstId(cfgTaskInstId);
                cfgNodeTaskRelat.setCreatorId(cfgStatistics.getCreatorId());
                cfgNodeTaskRelat.setCreateTime(cfgStatistics.getCreateTime());
                cfgNodeTaskRelatSV.create(cfgNodeTaskRelat);
            }
        }

        return true;
    }


    @BeforeUpdateHandler(attr = "status", orig = "!2", target = "2")
    public boolean active(CfgStatistics cfgStatistics, CfgStatistics origCfgStatistics) throws Exception {
        updateTask(cfgStatistics, CfgTaskInstStatusEnum.RUNNING);
        return true;
    }


    @BeforeUpdateHandler(attr = "status", orig = "2", target = "3")
    public boolean suspend(CfgStatistics cfgStatistics, CfgStatistics origCfgStatistics) throws Exception {
        updateTask(cfgStatistics, CfgTaskInstStatusEnum.SUSPEND);
        return true;
    }

    @AfterDeleteHandler()
    @BeforeUpdateHandler(attr = "status", target = "4")
    public boolean stop(CfgStatistics cfgStatistics, CfgStatistics origCfgStatistics) throws Exception {
        updateTask(cfgStatistics, CfgTaskInstStatusEnum.STOP);
        return true;
    }

    private void updateTask(CfgStatistics cfgStatistics, CfgTaskInstStatusEnum cfgTaskInstStatus) throws Exception {
        CfgTaskInst_Example example = new CfgTaskInst_Example();
        example.createCriteria()
                .andParamValue1EqualTo(String.valueOf(cfgStatistics.getCfgStatisticsId()))
                .andCfgTaskDefIdEqualTo(2L);
        List<CfgTaskInst> cfgTaskInstList = cfgTaskInstSV.getCfgTaskInstListByExample(example);
        if(cfgTaskInstList != null && cfgTaskInstList.size() > 0 ) {
            CfgTaskInst taskInst = new CfgTaskInst();
            taskInst.setCfgTaskInstId(cfgTaskInstList.get(0).getCfgTaskInstId());
            taskInst.setStatus(cfgTaskInstStatus.getValue());
            cfgTaskInstSV.update(taskInst);
        }
    }

}

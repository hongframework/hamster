package com.hframe.hamster.node.task.statistics;

import com.hframe.ext.bean.HBaseConfig;
import com.hframe.ext.bean.HBaseData;
import com.hframe.ext.service.CfgStatisticsService;
import com.hframe.hamster.node.HamsterContextInitializer;
import com.hframe.hamster.node.cannal.Message;
import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.task.AbstractReduceTask;
import com.hframe.hamster.node.task.common.FlowEndNodeTask;
import com.hframe.hamster.node.task.common.FlowTask;
import com.hframework.common.client.hbase.HBaseClient;
import com.hframework.common.util.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by zhangquanhong on 2017/3/22.
 */
public class MonthReduceTask extends AbstractReduceTask implements FlowEndNodeTask {
    private static final Logger logger = LoggerFactory.getLogger(MonthReduceTask.class);

    public MonthReduceTask(FlowKey flowKey, PrototypeKey prototypeKey) {
        super(flowKey, prototypeKey);
    }

    private static final String HBASE_STATISTIC_RUNTIME_TABLE = "hamster_statistics_runtime";

    @Override
    protected List<HBaseConfig> getLoadConfigs(FlowKey flowKey, PrototypeKey prototypeKey) throws Exception {
        return HamsterContextInitializer.getBean(
                CfgStatisticsService.class).getSystemHBaseConfig();
        //HamsterContextInitializer.getBean(
        //      CfgStatisticsService.class).getLoadConfigs(Long.valueOf(prototypeKey.value()));


    }

    @Override
    protected String tableName() throws Exception {
        return "hamster_statistics_month";
    }

    @Override
    public boolean ask(Message newMessage) {
        HBaseClient hBaseClient = HBaseClient.getInstance(hBaseConfigs.get(0).getZkList(), hBaseConfigs.get(0).getZkPort());
        List<HBaseData> hbaseDatas = newMessage.getHbaseDatas();
        for (HBaseData hbaseData : hbaseDatas) {
            String remark = hbaseData.getRemark();
            if(StringUtils.isNotBlank(remark)) {
                String[] rowKeys = remark.split(",");
                for (String rowKey : rowKeys) {
                    try {
                        hBaseClient.delete(HBASE_STATISTIC_RUNTIME_TABLE, rowKey);
                    } catch (Exception e) {
                        logger.error("month reduce task ask error ! {}", ExceptionUtils.getFullStackTrace(e));
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Class<? extends FlowTask> prevTask() {
        return DateReduceTask.class;
    }

}

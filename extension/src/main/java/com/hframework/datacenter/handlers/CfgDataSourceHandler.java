package com.hframework.datacenter.handlers;


import com.hframework.beans.exceptions.BusinessException;
import com.hframework.common.util.StringUtils;
import com.hframework.web.extension.AbstractBusinessHandler;
import com.hframework.web.extension.annotation.BeforeCreateHandler;
import com.hframework.web.extension.annotation.BeforeUpdateHandler;
import com.hframework.datacenter.utils.DBUtils;
import com.hframework.hamster.cfg.domain.model.CfgDatasource;
import com.hframework.hamster.cfg.service.interfaces.ICfgNodeSV;
import com.hframework.hamster.cfg.service.interfaces.ICfgNodeTaskRelatSV;
import com.hframework.hamster.cfg.service.interfaces.ICfgTaskInstSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangquanhong on 2016/9/23.
 */
@Service
public class CfgDataSourceHandler extends AbstractBusinessHandler<CfgDatasource> {

    private static final Logger logger = LoggerFactory.getLogger(CfgDataSourceHandler.class);

    @Resource
    private ICfgTaskInstSV cfgTaskInstSV;

    @Resource
    private ICfgNodeSV cfgNodeSV;

    @Resource
    private ICfgNodeTaskRelatSV cfgNodeTaskRelatSV;

    @BeforeCreateHandler
    @BeforeUpdateHandler
    public boolean create(CfgDatasource cfgDatasource) throws Exception {
        if(StringUtils.isNotBlank(cfgDatasource.getUrl())) {
            try{
                DBUtils.testConnection("jdbc:mysql://" + cfgDatasource.getUrl() + "/" + cfgDatasource.getDb() + "?useUnicode=true", cfgDatasource.getUsername(), cfgDatasource.getPassword());
            }catch (Exception e) {
                throw new BusinessException("数据源连接失败，请检查！");
            }

        }
        return true;
    }


}

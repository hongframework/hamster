package com.hframework.datacenter.dynscript;



import com.hframework.beans.exceptions.BusinessException;
import com.hframework.datacenter.utils.DBUtils;
import com.hframework.hamster.cfg.domain.model.CfgDatasource;
import com.hframework.hamster.cfg.domain.model.CfgDatasource_Example;
import com.hframework.hamster.cfg.service.interfaces.ICfgDatasourceSV;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangquanhong on 2017/3/14.
 */
public abstract class ScriptContext implements  Script{

    protected Map<String, Object> objectProperties;

    protected boolean isTestModel = false;

    @Resource
    private ICfgDatasourceSV cfgDatasourceSV;

    public Double dbQuery(String dbCode, String tableName, String selectColumn, String condition) throws Exception {
        CfgDatasource_Example cfgDatasourceExample = new CfgDatasource_Example();
        cfgDatasourceExample.createCriteria().andDbEqualTo(dbCode);
        List<CfgDatasource> cfgDatasourceList = cfgDatasourceSV.getCfgDatasourceListByExample(cfgDatasourceExample);
        if(cfgDatasourceList == null || cfgDatasourceList.size() == 0) {
            throw new BusinessException("未找到"+ dbCode +"对应的数据源配置信息！");
        }else if(cfgDatasourceList.size() > 1) {
            throw new BusinessException(""+ dbCode +"对应的数据源配置不唯一！");
        }

        List<Map> result = DBUtils.query(cfgDatasourceList.get(0), "select " + selectColumn + " from " + tableName + " where " + condition);

        if(isTestModel) {
            if(result == null || result.size() == 0 ) {
                return 1d;
            }else if(result.size() > 1) {
                return 1d;
            }
        }else {
            if(result == null || result.size() == 0 ) {
                throw new BusinessException("未查询到数据[" + dbCode + ", " + tableName + ", " + selectColumn + ", " + condition + "]！" );
            }else if(result.size() > 1) {
                throw new BusinessException("数据查询结果不唯一[" + dbCode + ", " + tableName + ", " + selectColumn + ", " + condition + "]！" );
            }
        }


        Object selectValue = result.get(0).get(result.get(0).keySet().iterator().next());

        return Double.valueOf(String.valueOf(selectValue));
    }

    public BigDecimal parseBigDecimal(Map<String, Object> objectProperties, String key) {
        return new BigDecimal(String.valueOf(objectProperties.get(key)));
    }

    public Long parseLong( String key) {
        return Long.valueOf(String.valueOf(objectProperties.get(key)));
    }
    public Double parseDouble( String key) {
        return Double.valueOf(String.valueOf(objectProperties.get(key)));
    }

    public static void main(String[] args) {
        double d1 = 1.23d;
        double d2 = 1.23d;
        System.out.println(d1/d2);
    }

    public boolean isTestModel() {
        return isTestModel;
    }

    public void setIsTestModel(boolean isTestModel) {
        this.isTestModel = isTestModel;
    }
}

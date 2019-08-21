package com.hframe.ext.bean;

import com.hframework.common.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhangquanhong on 2017/3/15.
 */
public class TransformConfig {

    private long configId;
    private String dbObjectName;

    private String dbObjectDatas;

    private List<String> columnsInfo;

    public long getConfigId() {
        return configId;
    }

    public void setConfigId(long configId) {
        this.configId = configId;
    }

    public String getDbObjectName() {
        return dbObjectName;
    }

    public void setDbObjectName(String dbObjectName) {
        this.dbObjectName = dbObjectName;
    }

    public String getDbObjectDatas() {
        return dbObjectDatas;
    }

    public void setDbObjectDatas(String dbObjectDatas) {
        this.dbObjectDatas = dbObjectDatas;
    }

    public List<String> getColumnsInfo() {
        if(columnsInfo == null) {
            synchronized (this) {
                if(columnsInfo == null) {
                    if(StringUtils.isNotBlank(dbObjectDatas)) {
                        String[] dbObjectDataArray = dbObjectDatas.trim().split("[ ]*,[ ]*");
                        columnsInfo = Arrays.asList(dbObjectDataArray);
                    }else {
                        columnsInfo = new ArrayList<>();
                    }
                }
            }
        }
        return columnsInfo;
    }
}

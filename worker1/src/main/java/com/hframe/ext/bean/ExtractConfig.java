package com.hframe.ext.bean;

import com.hframework.common.util.RegexUtils;
import com.hframework.common.util.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Created by zhangquanhong on 2017/3/15.
 */
public class ExtractConfig {

    private long configId;
    private String dbObjectName;

    private String dataFilterExpression;

    private String[] dataFilterVars;

    private Byte dbObjectOperateType;

    public long getConfigId() {
        return configId;
    }

    public void setConfigId(long configId) {
        this.configId = configId;
    }

    public String getDataFilterExpression() {
        return dataFilterExpression;
    }

    public void setDataFilterExpression(String dataFilterExpression) {
        this.dataFilterExpression = dataFilterExpression;
    }

    public Byte getDbObjectOperateType() {
        return dbObjectOperateType;
    }

    public void setDbObjectOperateType(Byte dbObjectOperateType) {
        this.dbObjectOperateType = dbObjectOperateType;
    }

    public String getDbObjectName() {
        return dbObjectName;
    }

    public void setDbObjectName(String dbObjectName) {
        this.dbObjectName = dbObjectName;
    }

    public String[] getDataFilterVars() {
        if(dataFilterVars == null) {
            synchronized (this) {
                if(dataFilterVars == null) {
                        if(StringUtils.isNotBlank(getDataFilterExpression())) {
                            String[] vars = RegexUtils.find(getDataFilterExpression().replaceAll("'[^']*'", ""), "[a-zA-Z]+[a-zA-Z0-9._]*");
                            vars = (String[]) (new HashSet(Arrays.asList(vars))).toArray(new String[0]);
                            Arrays.sort(vars, new Comparator<String>() {
                                @Override
                                public int compare(String o1, String o2) {
                                    return o1.contains(o2) ? -1 : 1;
                                }
                            });
                            dataFilterVars = vars;
                        }
                }
            }
        }
        return dataFilterVars;
    }

}

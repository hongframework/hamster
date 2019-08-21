package com.hframework.datacenter.dynscript;

import java.util.Map;

/**
 * Created by zhangquanhong on 2017/3/14.
 */
public interface Script {

    public Double execute(Map<String, Object> objectProperties) throws Exception;
}

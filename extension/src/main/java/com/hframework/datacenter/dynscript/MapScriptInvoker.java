package com.hframework.datacenter.dynscript;

import java.util.Map;

/**
 * Created by zhangquanhong on 2017/3/14.
 */
public class MapScriptInvoker {

    public Double execute(Map<String, Object> objectProperties, Script script) throws Exception {
        return script.execute(objectProperties);
    }
}

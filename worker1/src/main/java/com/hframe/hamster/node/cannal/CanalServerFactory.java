package com.hframe.hamster.node.cannal;

import com.hframe.hamster.node.monitor.bean.FlowKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangquanhong on 2016/9/29.
 */
public class CanalServerFactory {

    private static Map<String, CanalServer>  factory = new HashMap<>();

    public static void put(FlowKey flowKey, PrototypeKey prototypeKey, CanalServer canalServer) {
        factory.put(flowKey.value() + "-" + prototypeKey.value(), canalServer);
    }
    public static CanalServer get(FlowKey flowKey, PrototypeKey prototypeKey) {
        return factory.get(flowKey.value() + "-" + prototypeKey.value());
    }
}

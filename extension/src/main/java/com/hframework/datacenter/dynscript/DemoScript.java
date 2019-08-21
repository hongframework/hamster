package com.hframework.datacenter.dynscript;

import java.util.Map;

/**
 * Created by zhangquanhong on 2017/3/14.
 */
public class DemoScript extends ScriptContext implements Script {
    @Override
    public Double execute(Map<String, Object> objectProperties) throws Exception {
        super.objectProperties = objectProperties;
        setIsTestModel(true);
        Double money = parseDouble("money");
        Double deal_id = parseDouble("deal_id");

        return money * dbQuery("firstb2b_test", "firstb2b_deal", "repay_time", "id=" + deal_id)/3600;

    }
}

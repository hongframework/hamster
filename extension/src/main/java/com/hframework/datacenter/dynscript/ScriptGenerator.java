package com.hframework.datacenter.dynscript;

import com.hframework.common.util.RegexUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhangquanhong on 2017/3/15.
 */
public class ScriptGenerator {

    private static final String SCRIPT_BODY_TEMPLATE =
            "package com.hframe.dynscript;\n" +
            "import java.util.Map;\n" +

            "public class ${className} extends ScriptContext implements Script {\n" +
            "    @Override\n" +
            "    public Double execute(Map<String, Object> objectProperties) throws Exception {\n" +
            "        super.objectProperties = objectProperties;\n" +
            "        ${prepare}\n" +
            "        return ${script};\n" +
            "\n" +
            "    }\n" +
            "}";

    public static String produceScriptBodyForTest(String className, String script) {
        String scriptContext = SCRIPT_BODY_TEMPLATE.replace("${className}", className);
        scriptContext = scriptContext.replace("${prepare}", "setIsTestModel(true);\n" + parsePrepareForTest(script));
        scriptContext = scriptContext.replace("${script}", script);
        return scriptContext;
    }

    public static String produceScriptBody(String className, String script) {
        String scriptContext = SCRIPT_BODY_TEMPLATE.replace("${className}", className);
        scriptContext = scriptContext.replace("${prepare}",  parsePrepare(script));
        scriptContext = scriptContext.replace("${script}", script);
        return scriptContext;
    }

    /**
     * money * dbQuery("firstb2b_test", "firstb2b_deal", "repay_time", "id=" + deal_id)/3600
     * @param scriptContent
     * @return
     */
    private static String parsePrepareForTest(String scriptContent) {
        String[] strings = RegexUtils.find(scriptContent.replaceAll("(\")[^\"]+(\")", ""), "[a-zA-Z_][a-zA-Z0-9_]+");
        Set<String> params = new HashSet<>();
        for (String string : strings) {
            if(!"dbQuery".equals(string)) {
                params.add(string);
            }
        }

        StringBuffer sb = new StringBuffer();
        for (String param : params) {
            sb.append("Double " + param + " = 1d;\n");
        }

        return sb.toString();
    }

    /**
     * money * dbQuery("firstb2b_test", "firstb2b_deal", "repay_time", "id=" + deal_id)/3600
     * @param scriptContent
     * @return
     */
    private static String parsePrepare(String scriptContent) {
        String[] strings = RegexUtils.find(scriptContent.replaceAll("(\")[^\"]+(\")", ""), "[a-zA-Z_][a-zA-Z0-9_]+");
        List<String> params = new ArrayList<>();
        for (String string : strings) {
            if(!"dbQuery".equals(string)) {
                params.add(string);
            }
        }

        StringBuffer sb = new StringBuffer();
        for (String param : params) {
            sb.append("Double " + param + " = parseDouble(\"" + param + "\");\n");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(parsePrepare("money * dbQuery(\"firstb2b_test\", \"firstb2b_deal\", \"repay_time\", \"id=\" + deal_id)/3600"));
    }
}

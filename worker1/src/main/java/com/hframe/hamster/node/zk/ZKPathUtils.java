package com.hframe.hamster.node.zk;

import org.apache.commons.lang.StringUtils;

import java.text.MessageFormat;

/**
 * Created by zhangquanhong on 2016/9/28.
 */
public class ZKPathUtils {

    private static final String ROOT                =   "/hamster";

    private static final String NODE_ROOT                  =     "/hamster/node";
    private static final String NODE                        =     "/hamster/node/{0}";

    private static final String STORY_ROOT                 =     "/hamster/story";
    private static final String STORY_INSTANCE_ROOT      =     "/hamster/story/{0}/instance/{1}";
    private static final String MAINSTEM                   =     "/hamster/story/{0}/instance/{1}/mainstem";
    private static final String PROCESS_ROOT              =     "/hamster/story/{0}/instance/{1}/process";
    private static final String PROCESS                    =     "/hamster/story/{0}/instance/{1}/process/{2}";
    private static final String TERMIN_ROOT              =     "/hamster/story/{0}/instance/{1}/termin";
    private static final String TERMIN                    =     "/hamster/story/{0}/instance/{1}/termin/{2}";
    private static final String PROCESS_STAGE             =     "/hamster/story/{0}/instance/{1}/process/{2}/{3}";
    private static final String REDIS_PROCESS_STAGE             =     "hamster_{0}_{1}_{2}_{3}";

    public static String getRootPath(){
        return ROOT;
    }

    public static String getNodeRootPath() {
        return NODE_ROOT;
    }

    public static String getStoryRootPath() {
        return STORY_ROOT;
    }

    public static String getNodePath(Long nodeId) {
        return MessageFormat.format(NODE, String.valueOf(nodeId));
    }

    public static String getNodePath(String nodeId) {
        return MessageFormat.format(NODE,nodeId);
    }


    public static String getStoryInstanceRootPath(String taskStory, String prototypeKey) {
        return MessageFormat.format(STORY_INSTANCE_ROOT, taskStory, prototypeKey);
    }

    public static String getMainStemPath(String taskStory, String prototypeKey) {
        return MessageFormat.format(MAINSTEM, taskStory, prototypeKey);
    }

    public static String getProcessRootPath(String taskStory, String prototypeKey) {
        return MessageFormat.format(PROCESS_ROOT, taskStory, prototypeKey);
    }

    public static String getProcessPath(String taskStory, String prototypeKey ,Long processId) {
        return MessageFormat.format(PROCESS, taskStory, prototypeKey, StringUtils.leftPad(String.valueOf(processId.intValue()), 10, '0'));
    }

    public static String getTerminRootPath(String taskStory, String prototypeKey) {
        return MessageFormat.format(TERMIN_ROOT, taskStory, prototypeKey);
    }

    public static String getTerminPath(String taskStory, String prototypeKey ,Long processId) {
        return MessageFormat.format(TERMIN, taskStory, prototypeKey, StringUtils.leftPad(String.valueOf(processId.intValue()), 10, '0'));
    }

    public static String getProcessStagePath(String taskStory, String prototypeKey ,Long processId, String stageName) {
        return MessageFormat.format(PROCESS_STAGE, taskStory, prototypeKey,
                StringUtils.leftPad(String.valueOf(processId.intValue()), 10, '0'), stageName);
    }

    public static String getRedisProcessStagePath(String taskStory, String prototypeKey ,Long processId, String stageName) {
        return MessageFormat.format(REDIS_PROCESS_STAGE, taskStory, prototypeKey,
                StringUtils.leftPad(String.valueOf(processId.intValue()), 10, '0'), stageName);
    }

}

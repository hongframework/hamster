package com.hframe.hamster.node.logHandlers;

import com.hframe.hamster.node.HamsterConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class SelectDumpHandler {
    private static final Logger logger = LoggerFactory.getLogger(SelectDumpHandler.class);
    public static void info(String selector, String content) {
        try{
            MDC.put(HamsterConst.splitSelectorLogFileKey, selector);
            logger.info(content);
        }finally {
            MDC.remove(HamsterConst.splitSelectorLogFileKey);
        }
    }
}

package com.hframe.hamster.node.logHandlers;

import com.hframe.hamster.node.HamsterConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class LoadDumpHandler {
    private static final Logger logger = LoggerFactory.getLogger(LoadDumpHandler.class);
    public static void info(String loader, String content) {
        try{
            MDC.put(HamsterConst.splitLoaderLogFileKey, loader);
            logger.info(content);
        }finally {
            MDC.remove(HamsterConst.splitLoaderLogFileKey);
        }
    }
}

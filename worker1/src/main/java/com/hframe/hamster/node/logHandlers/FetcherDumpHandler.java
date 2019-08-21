package com.hframe.hamster.node.logHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FetcherDumpHandler {
    private static final Logger logger = LoggerFactory.getLogger(FetcherDumpHandler.class);
    public static void info(String content) {
        logger.info(content);
    }
}

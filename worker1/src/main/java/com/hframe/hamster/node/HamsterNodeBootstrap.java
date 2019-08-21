package com.hframe.hamster.node;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 启动进程
 * Created by zhangquanhong on 2016/9/27.
 */
public class HamsterNodeBootstrap {
    private static final Logger logger = LoggerFactory.getLogger(HamsterNodeBootstrap.class);

    public static void main(String[] args) {
        try{
            logger.info("hamster startup ..");
            final HamsterController controller = HamsterContextInitializer.getBean(HamsterController.class);
            controller.start();
            logger.info("hamster startup ok ..");
            Runtime.getRuntime().addShutdownHook(new Thread(){
                @Override
                public void run() {
                    logger.info("hamster shutdown ..");
                    controller.stop();
                    logger.info("hamster shutdown ok ..");
                }
            });
        }catch (Exception e) {
            logger.error("hamster startup error, {}", ExceptionUtils.getFullStackTrace(e));
        }
    }
}

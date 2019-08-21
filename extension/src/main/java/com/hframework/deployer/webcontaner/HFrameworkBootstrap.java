
package com.hframework.deployer.webcontaner;

import com.hframework.deployer.webcontaner.JettyEmbedServer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Map.Entry;
import java.util.Properties;

public class HFrameworkBootstrap {

    private static final Logger logger               = LoggerFactory.getLogger(HFrameworkBootstrap.class);
    private static final String CLASSPATH_URL_PREFIX = "classpath:";

    public static void main(String[] args) throws Throwable {
        try {
            String conf = System.getProperty("hframe.conf", "classpath:hframe.properties");
            Properties properties = new Properties();
            if (conf.startsWith(CLASSPATH_URL_PREFIX)) {
                conf = StringUtils.substringAfter(conf, CLASSPATH_URL_PREFIX);
                properties.load(HFrameworkBootstrap.class.getClassLoader().getResourceAsStream(conf));
            } else {
                properties.load(new FileInputStream(conf));
            }

            // 合并配置到system参数中
            mergeProps(properties);

            logger.info("## start the manager server.");
            final JettyEmbedServer server = new JettyEmbedServer(properties.getProperty("hframe.jetty", "jetty.xml"));
            server.start();
            logger.info("## the manager server is running now ......");
            Runtime.getRuntime().addShutdownHook(new Thread() {

                public void run() {
                    try {
                        logger.info("## stop the manager server");
                        server.join();
                    } catch (Throwable e) {
                        logger.warn("##something goes wrong when stopping manager Server:\n{}",
                                    ExceptionUtils.getFullStackTrace(e));
                    } finally {
                        logger.info("## manager server is down.");
                    }
                }

            });
        } catch (Throwable e) {
            e.printStackTrace();
            logger.error("## Something goes wrong when starting up the manager Server:\n{}",
                         ExceptionUtils.getFullStackTrace(e));
            System.exit(0);
        }
    }

    private static void mergeProps(Properties props) {
        for (Entry<Object, Object> entry : props.entrySet()) {
            System.setProperty((String) entry.getKey(), (String) entry.getValue());
        }
    }
}

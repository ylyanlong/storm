package com.yl.storm.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2015/10/29.
 */
public class ThreadUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadUtil.class);

    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            LOG.info("waitForSeconds error");
        }
    }

    public static void waitForMillis(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            LOG.info("waitForMillis error");
        }
    }
}

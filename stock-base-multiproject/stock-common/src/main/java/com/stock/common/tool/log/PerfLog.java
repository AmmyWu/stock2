package com.stock.common.tool.log;


import org.apache.log4j.Logger;

/**
 * 性能日志
 */
public class PerfLog {
    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger("PERF_LOG");

    /**
     * 打印开始性能日志
     * 
     * @param invoker
     */
    public static void printStart(String invoker) {
        LogUtil.info(logger, invoker + " - S - " + System.currentTimeMillis());
    }

    /**
     * 打印结束性能日志
     * 
     * @param invoker
     */
    public static void printEnd(String invoker) {
        LogUtil.info(logger, invoker + " - E - " + System.currentTimeMillis());
    }
}

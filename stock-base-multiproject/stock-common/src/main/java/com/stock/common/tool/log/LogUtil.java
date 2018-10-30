package com.stock.common.tool.log;



import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.stock.common.tool.random.UniqueIdGenerator;
import com.stock.common.tool.string.StringUtil;

/**
 * 标准日志工具，要求在业务中，打日志，必须要用这个类中的方法。
 * 
 */
public class LogUtil {

    /**
     * 日志的唯一号，为了方便一个完整业务分散在多个日志文件中， 可以连起来看。
     */
    private static ThreadLocal<String> logUniqueId = new ThreadLocal<String>();

    public static String getUniqueId() {
        return logUniqueId.get();
    }

    /**
     * 设置日志的唯一号到线程变量。
     * 
     * @param previousLogId 表示父线程的logId
     */
    public static void myThreadStart(String previousLogId) {
        if (StringUtil.isBlank(previousLogId)) {
            logUniqueId.set(UniqueIdGenerator.generateRandomStr(10));
        } else {
            logUniqueId.set(previousLogId + "_" + UniqueIdGenerator.generateRandomStr(3));
        }
    }

    /**
     * 把日志的唯一号清除线程变量
     */
    public static void myThreadEnd() {
        logUniqueId.remove();
    }

    /**
     * 代替Logger.trace方法
     * 
     * @param logger
     * @param msg
     */
    public static void trace(Logger logger, String msg) {
        if (logger.isTraceEnabled()) {
            logger.trace(msg);
        }
    }

    /**
     * 代替Logger.trace方法
     * 
     * @param logger
     * @param msg
     */
    public static void trace(Logger logger, String msg, Object obj) {
        if (logger.isTraceEnabled()) {
            logger.trace(msg + " | " + obj.toString());
        }
    }

    /**
     * 代替Logger.trace方法
     * 
     * @param logger
     * @param msg
     * @param t 
     */
    public static void trace(Logger logger, String msg, Throwable t) {
        if (logger.isTraceEnabled()) {
            logger.trace(msg, t);
        }
    }

    /**
     * 代替Logger.debug方法
     * 
     * @param logger
     * @param msg
     */
    public static void debug(Logger logger, String msg) {
        if (logger.isDebugEnabled()) {
            logger.debug(msg);
        }
    }

    /**
     * 代替Logger.debug方法
     * 
     * @param logger
     * @param msg
     */
    public static void debug(Logger logger, String msg, Object obj) {
        if (logger.isDebugEnabled()) {
            logger.debug(msg + " | " + obj.toString());
        }
    }

    /**
     * 代替Logger.debug方法
     * 
     * @param logger
     * @param msg
     * @param t 
     */
    public static void debug(Logger logger, String msg, Throwable t) {
        if (logger.isDebugEnabled()) {
            logger.debug(msg, t);
        }
    }

    /**
     * 代替Logger.info方法
     * 
     * @param logger
     * @param msg
     */
    public static void info(Logger logger, String msg) {
        if (logger.isInfoEnabled()) {
            logger.info(msg);
        }
    }

    /**
     * 代替Logger.info方法
     * 
     * @param logger
     * @param msg
     */
    public static void info(Logger logger, String msg, Object obj) {
        if (logger.isInfoEnabled()) {
            logger.info(msg + " | " + obj.toString());
        }
    }

    /**
     * 代替Logger.info方法
     * 
     * @param logger
     * @param msg
     * @param t 
     */
    public static void info(Logger logger, String msg, Throwable t) {
        if (logger.isInfoEnabled()) {
            logger.info(msg, t);
        }
    }

    /**
     * 代替Logger.warn方法
     * 
     * @param logger
     * @param msg
     */
    public static void warn(Logger logger, String msg) {
        if (logger.isEnabledFor(Level.WARN)) {
            logger.warn(msg);
        }
    }

    /**
     * 代替Logger.warn方法
     * 
     * @param logger
     * @param msg
     */
    public static void warn(Logger logger, String msg, Object obj) {
        if (logger.isEnabledFor(Level.WARN)) {
            logger.warn(msg + " | " + obj.toString());
        }
    }

    /**
     * 代替Logger.warn方法
     * 
     * @param logger
     * @param msg
     * @param t 
     */
    public static void warn(Logger logger, String msg, Throwable t) {
        if (logger.isEnabledFor(Level.WARN)) {
            logger.warn(msg, t);
        }
    }

    /**
     * 代替Logger.error方法
     * 
     * @param logger
     * @param msg
     */
    public static void error(Logger logger, String msg) {
        if (logger.isEnabledFor(Level.ERROR)) {
            logger.error(msg);
        }
    }

    /**
     * 代替Logger.error方法
     * 
     * @param logger
     * @param msg
     */
    public static void error(Logger logger, String msg, Object obj) {
        if (logger.isEnabledFor(Level.ERROR)) {
            logger.error(msg + " | " + obj.toString());
        }
    }

    /**
     * 代替Logger.error方法
     * 
     * @param logger
     * @param msg
     * @param t 
     */
    public static void error(Logger logger, String msg, Throwable t) {
        if (logger.isEnabledFor(Level.ERROR)) {
            logger.error(msg, t);
        }
    }

    /**
     * 代替Logger.fatal方法
     * 
     * @param logger
     * @param msg
     */
    public static void fatal(Logger logger, String msg) {
        if (logger.isEnabledFor(Level.FATAL)) {
            logger.fatal(msg);
        }
    }

    /**
     * 代替Logger.fatal方法
     * 
     * @param logger
     * @param msg
     */
    public static void fatal(Logger logger, String msg, Object obj) {
        if (logger.isEnabledFor(Level.FATAL)) {
            logger.error(msg + " | " + obj.toString());
        }
    }

    /**
     * 代替Logger.error方法
     * 
     * @param logger
     * @param msg
     * @param t 
     */
    public static void fatal(Logger logger, String msg, Throwable t) {
        if (logger.isEnabledFor(Level.FATAL)) {
            logger.fatal(msg, t);
        }
    }

}

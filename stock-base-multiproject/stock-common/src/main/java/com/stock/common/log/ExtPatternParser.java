package com.stock.common.log;

import org.apache.log4j.helpers.FormattingInfo;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 
 */
public class ExtPatternParser extends PatternParser {

    /**
     * @param pattern
     */
    public ExtPatternParser(String pattern) {
        super(pattern);
    }

    /** 
    * 重写finalizeConverter，对特定的占位符进行处理，T表示线程ID占位符 
    */
    @Override
    protected void finalizeConverter(char c) {
        switch (c) {
            case 'T':
                this.addConverter(new ExPatternConverter(this.formattingInfo));
                break;
            case 'M':
                this.addConverter(new MethodPatternConverter(this.formattingInfo));
                break;
            default:
                super.finalizeConverter(c);
                break;
        }
    }

    private static class MethodPatternConverter extends PatternConverter {

        public MethodPatternConverter(FormattingInfo fi) {
            super(fi);
        }

        /** 
         * @see org.apache.log4j.helpers.PatternConverter#convert(org.apache.log4j.spi.LoggingEvent)
         */
        @Override
        protected String convert(LoggingEvent event) {
            Exception e = new Exception();
            StackTraceElement[] stacks = e.getStackTrace();
            for (StackTraceElement stack : stacks) {
                String cn = stack.getClassName();
                if (cn.startsWith("com.slianclass")) {
                    return stack.getMethodName();
                }
            }
            return "NULL";
        }
    }

    private static class ExPatternConverter extends PatternConverter {

        public ExPatternConverter(FormattingInfo fi) {
            super(fi);
        }

        /** 
          * 当需要显示唯一号时，显示唯一号
          */
        @Override
        protected String convert(LoggingEvent event) {
            return "[" + LogUtil.getUniqueId() + "]";
        }
    }
}

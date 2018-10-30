package com.stock.common.log;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;

/**
 * 扩展的PatternLayout
 */
public class ExtPatternLayout extends PatternLayout {

    public ExtPatternLayout(String pattern) {
        super(pattern);
    }

    public ExtPatternLayout() {
        super();
    }

    /** 
     * 重写createPatternParser方法，返回PatternParser的子类 
     */
    @Override
    protected PatternParser createPatternParser(String pattern) {
        return new ExtPatternParser(pattern);
    }
}

package com.stock.common.tool.config;



import java.util.Map;
import java.util.Properties;

import org.springframework.util.Log4jConfigurer;


/**
 */
public class SystemPropertiesConfig {
    /**
     */
    private Properties properties = null;
    boolean            iswindows  = true;

    /**
     */
    public void init() throws Exception {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = entry.getKey().toString().trim();
            String value = entry.getValue().toString().trim();
            if (value.indexOf("/home/") == 0) {
                if (iswindows) {
                    value = "D:" + value;
                }
            }
            System.setProperty(key, value);
        }
        Log4jConfigurer.initLogging("classpath:log4j.xml");
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

}

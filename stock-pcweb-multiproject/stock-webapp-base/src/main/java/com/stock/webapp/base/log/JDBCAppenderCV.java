package com.stock.webapp.base.log;

import org.apache.log4j.Priority;
import org.apache.log4j.jdbc.JDBCAppender;

public class JDBCAppenderCV extends JDBCAppender {
	@Override
	public boolean isAsSevereAsThreshold(Priority priority) {  
        // TODO Auto-generated method stub 
		OperateLevel ol=new OperateLevel();
		return ol.equals(priority);  
    } 
}

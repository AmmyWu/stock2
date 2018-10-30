package com.stock.webapp.base.log;

import org.apache.log4j.Level;
import org.apache.log4j.net.SyslogAppender;  

public class OperateLevel extends Level {
	public OperateLevel() {  
		super(45000, "OPERATE", SyslogAppender.LOG_LOCAL0);  
	}  
}

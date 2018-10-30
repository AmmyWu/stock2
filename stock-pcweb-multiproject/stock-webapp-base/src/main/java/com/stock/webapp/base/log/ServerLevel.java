package com.stock.webapp.base.log;

import org.apache.log4j.Level;
import org.apache.log4j.net.SyslogAppender;

public class ServerLevel extends Level {
	public ServerLevel() {  
		super(46000, "SERVER", SyslogAppender.LOG_LOCAL0);  
	} 
}

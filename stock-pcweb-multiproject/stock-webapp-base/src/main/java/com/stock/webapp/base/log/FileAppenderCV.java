package com.stock.webapp.base.log;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Priority;

public class FileAppenderCV extends FileAppender {
	@Override
	public boolean isAsSevereAsThreshold(Priority priority) {  
        // TODO Auto-generated method stub 
		ServerLevel sl=new ServerLevel();
		return sl.equals(priority); 
	}
}

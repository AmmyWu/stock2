
 /**************************************************************************
 * 项目名称：数联软件 web开发框架                          
 ***************************************************************************/
package com.stock.pojo.vo.sys;

import com.stock.dao.model.sys.SysMessage;


 /**
 * TODO 请在此处添加注释
 * @author <a href="mailto:chengxl@stocksoft.com">chengxl</a>
 * @version $Id$   
 * @since 2.0
 */

public class SysMessageVO extends SysMessage {
	
	
	private String producerName ;
	
	private String consumerName;
	
	private String strMessage;

	/**
	 */

	public SysMessageVO() {
		// TODO Auto-generated constructor stub
	}

	public String getStrMessage() {
		return strMessage;
	}

	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

}

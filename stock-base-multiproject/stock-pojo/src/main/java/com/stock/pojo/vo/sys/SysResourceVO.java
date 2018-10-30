/**************************************************************************
 * 项目名称：数联软件 web开发框架                          
 ***************************************************************************/
package com.stock.pojo.vo.sys;

/**
 * TODO 定义前端树形表格显示的数据格式 { "id": 1, "name": "系统", "type": "用户", "priority": null,
 * "url": "NULL", "pId": 0, "remark": "PUBLIC", "status": "1", "icon": "" }
 * 
 * 
 * @author <a href="mailto:chengxl@stocksoft.com">chengxl</a>
 * @version $Id$
 * @since 2.0
 */

public class SysResourceVO {

	private int id;

	private int pId;

	private String name;
	
	private String type;

	private Integer priority;

	private String url;

	private String remark;

	private String status;

	private String icon;

	/**  
	 *@return  the id
	 */
	
	public int getId() {
		return id;
	}

	/** 
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**  
	 *@return  the pId
	 */
	
	public int getpId() {
		return pId;
	}

	/** 
	 * @param pId the pId to set
	 */
	public void setpId(int pId) {
		this.pId = pId;
	}

	/**  
	 *@return  the name
	 */
	
	public String getName() {
		return name;
	}

	/** 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**  
	 *@return  the priority
	 */
	
	public Integer getPriority() {
		return priority;
	}

	/** 
	 * @param priority the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**  
	 *@return  the url
	 */
	
	public String getUrl() {
		return url;
	}

	/** 
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**  
	 *@return  the remark
	 */
	
	public String getRemark() {
		return remark;
	}

	/** 
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**  
	 *@return  the status
	 */
	
	public String getStatus() {
		return status;
	}

	/** 
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**  
	 *@return  the icon
	 */
	
	public String getIcon() {
		return icon;
	}

	/** 
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**  
	 *@return  the type
	 */
	
	public String getType() {
		return type;
	}

	/** 
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}

package com.stock.pojo.vo;

/**
 * @author chengxl
 * 
 */
public class BaseModelVO {
	
	
	private String creatorName;
	private String amenderName;
	private String organiztionName;
	


	public BaseModelVO() {
		// TODO Auto-generated constructor stub
	}



	/**
	 * @return the creatorName
	 */
	public String getCreatorName() {
		return creatorName;
	}



	/**
	 * @param creatorName the creatorName to set
	 */
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}



	/**
	 * @return the organiztionName
	 */
	public String getOrganiztionName() {
		return organiztionName;
	}



	/**
	 * @param organiztionName the organiztionName to set
	 */
	public void setOrganiztionName(String organiztionName) {
		this.organiztionName = organiztionName;
	}



	public String getAmenderName() {
		return amenderName;
	}



	public void setAmenderName(String amenderName) {
		this.amenderName = amenderName;
	}

}

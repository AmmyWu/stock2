package com.stock.pojo.vo.dto;

import java.io.Serializable;
import java.util.Date;

public class SolrWorkDTO implements Serializable{

	private int id;
	private String operate;
	private Date timeOut;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public Date getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(Date timeOut) {
		this.timeOut = timeOut;
	}
	
}

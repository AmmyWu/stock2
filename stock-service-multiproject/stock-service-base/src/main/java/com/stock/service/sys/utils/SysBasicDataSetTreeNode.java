package com.stock.service.sys.utils;


import java.util.ArrayList;
import java.util.List;

import com.stock.dao.model.sys.SysBasicDataSet;


public class SysBasicDataSetTreeNode extends SysBasicDataSet {
	
	private boolean checked;
	
	private String text;
	
	private Integer id;
	
	private String state;
	
	private List<SysBasicDataSetTreeNode> children;

	public List<SysBasicDataSetTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<SysBasicDataSetTreeNode> children) {
		this.children = children;
	}
	
	public void addChild(SysBasicDataSetTreeNode node){
		if(this.children == null)
			children = new ArrayList<SysBasicDataSetTreeNode>();
		
		children.add(node);
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}

/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.utils
 * 类/接口名	: SysOrganizationStructureTreeNode
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午6:08:06
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午6:08:06
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys.utils;

import java.util.ArrayList;
import java.util.List;

import com.stock.dao.model.sys.SysOrganizationStructure;

public class SysOrganizationStructureTreeNode extends SysOrganizationStructure {
	
	private Integer id;
	
	private boolean checked;
	
	private String text;
	
	private List<SysOrganizationStructureTreeNode> children;

	public List<SysOrganizationStructureTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<SysOrganizationStructureTreeNode> children) {
		this.children = children;
	}
	
	public void addChild(SysOrganizationStructureTreeNode node){
		if(this.children == null)
			children = new ArrayList<SysOrganizationStructureTreeNode>();
		
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
	
	/*public int getLevel(OrganizationStructureTreeNode node){
		
		return 0;
	}
	
	
	//计算节点的level
	private int levelSize(int level ,OrganizationStructureTreeNode node){
		if(node == null)
			return 0;

		if(node.getChildren() !=null & node.getChildren().size() >0)
			return level + levelSize(level,node);
		else
			return level;
		
	}
*/
}

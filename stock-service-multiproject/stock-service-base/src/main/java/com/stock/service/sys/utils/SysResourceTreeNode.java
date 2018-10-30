/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.utils
 * 类/接口名	: SysResourcesTreeNode
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午6:08:06
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午6:08:06
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys.utils;


import java.util.ArrayList;
import java.util.List;

import com.stock.dao.model.sys.SysResource;

public class SysResourceTreeNode extends SysResource {
	
	private int id;
	
	private boolean checked;
	private String name;
	private int level;
	
	private List<SysResourceTreeNode> children;

	public List<SysResourceTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<SysResourceTreeNode> children) {
		this.children = children;
	}
	
	public void addChild(SysResourceTreeNode node){
		if(this.children == null)
			children = new ArrayList<SysResourceTreeNode>();
		
		children.add(node);
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	/*public int getLevel(ResourcesTreeNode node){
		
		return 0;
	}
	
	
	//计算节点的level
	private int levelSize(int level ,ResourcesTreeNode node){
		if(node == null)
			return 0;

		if(node.getChildren() !=null & node.getChildren().size() >0)
			return level + levelSize(level,node);
		else
			return level;
		
	}
*/
}

/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.utils
 * 类/接口名	: SysOrganizationStructureTree
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午6:08:06
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午6:08:06
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys.utils;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.stock.dao.model.sys.SysOrganizationStructure;

/**
 * 资源树，将资源以树的方式存储
 * @author Apple
 *
 */
public class SysOrganizationStructureTree {

	private List<SysOrganizationStructure> listOrganizationStructure;

	private Map<Integer, SysOrganizationStructureTreeNode> tree = new HashMap<Integer, SysOrganizationStructureTreeNode>();


	
	public SysOrganizationStructureTree(List<SysOrganizationStructure> listOrganizationStructure) {
		this.listOrganizationStructure = listOrganizationStructure;
		buildOrganizationStructureTree();
	}
	
	
	public List<Integer> getOSIds(String name){
		
		List<Integer>  osIds = new ArrayList<Integer>();
		
		for(SysOrganizationStructure os:this.getListOrganizationStructure()){
			//System.out.println(os.getName() +"  aa:"+ os.getName().indexOf(name));
			if(os.getName().indexOf(name) < 0)
				;
			else
				osIds.add(os.getOrganizationStructureId());
		}
		return osIds;
	}

	// 构造树
	private void buildOrganizationStructureTree() {

		for (SysOrganizationStructure os : listOrganizationStructure) {

			SysOrganizationStructureTreeNode treeNode = new SysOrganizationStructureTreeNode();
			
//			ReflectionUtils.parentToChild(os, treeNode);
			
			BeanUtils.copyProperties(os,treeNode);
			
			if (tree.containsKey(os.getSuperiorId())) {
				SysOrganizationStructureTreeNode parentNode = tree.get(os.getSuperiorId());

				parentNode.addChild(treeNode);

			}

			tree.put(os.getOrganizationStructureId(), treeNode);

		}
	}

	// 根据key,获得指定的节点
	public SysOrganizationStructureTreeNode getNode(Integer key) {

		return tree.get(key);
	}

	// 获得节点的Ancestors node
	public List<SysOrganizationStructureTreeNode> getAncestorsNodes(SysOrganizationStructureTreeNode node) {
		List<SysOrganizationStructureTreeNode> anceNodes = new ArrayList<SysOrganizationStructureTreeNode>();

		this.setAnceNodes(node, anceNodes);
		return anceNodes;
	}


	//返回指定节点所有父节点的Name
	public String getParentsNodeName(SysOrganizationStructureTreeNode node){
		
		if(node.getSuperiorId() == null || "0".equals(node.getSuperiorId()) ||"".equals(node.getSuperiorId()))
			return "";
		
		List<SysOrganizationStructureTreeNode>  anceNodes = new ArrayList<SysOrganizationStructureTreeNode>();
		
		this.setAnceNodes(node, anceNodes);
		
		String parentsName = "";
		
		for(SysOrganizationStructureTreeNode ost:anceNodes){
			
			parentsName += ost.getName() + "##";
		}
		
		String [] parentsArrayName = parentsName.split("##");
		
		String newParentsName = "";

		
		for( int i = parentsArrayName.length ;i > 0; i--){
			
			newParentsName += parentsArrayName[i-1] + " || ";
		}
		
		
		return " || ".equals(newParentsName)?"":newParentsName;
	}
	// 递归依次获取所有祖先节点
	private void setAnceNodes(SysOrganizationStructureTreeNode node,
			List<SysOrganizationStructureTreeNode> anceNodes) {

		if (tree.containsKey(node.getSuperiorId())) {
			SysOrganizationStructureTreeNode parentNode = tree.get(node.getSuperiorId());
			anceNodes.add(parentNode);

			this.setAnceNodes(parentNode, anceNodes);

		}
	}

	// 获得节点的parent node
	public SysOrganizationStructureTreeNode getParentNode(SysOrganizationStructureTreeNode node) {
		
		return tree.get(node.getSuperiorId());
	}

	// 判断是否根节点
	public boolean isRootNode(SysOrganizationStructureTreeNode node) {

		return (node.getSuperiorId() == null || "0".equals(node.getSuperiorId())) ? true
				: false;

	}

	// 返回指定节点的所有叶子节点
	public List<SysOrganizationStructureTreeNode> getLeafNodes(SysOrganizationStructureTreeNode node) {

		List<SysOrganizationStructureTreeNode> leafNodes = new ArrayList<SysOrganizationStructureTreeNode>();
		this.setLeafNodes(node, leafNodes);

		return leafNodes;
	}

	// 递归依次获取所有叶子节点
	private void setLeafNodes(SysOrganizationStructureTreeNode node,
			List<SysOrganizationStructureTreeNode> leafNodes) {
		
		List<SysOrganizationStructureTreeNode> children = node.getChildren();

		if (children == null || children.size() < 1)
			leafNodes.add(node);

		else{
			for(SysOrganizationStructureTreeNode child:children)

				this.setLeafNodes(child, leafNodes);

		}
			
	}

	public List<SysOrganizationStructure> getListOrganizationStructure() {
		return listOrganizationStructure;
	}
	
	public Set<Integer> getAllChildren(Set<Integer> orgIdSet){
		if(orgIdSet==null)
			return null;
		if(orgIdSet.size()==0)
			return new HashSet<Integer>();
		else{
			HashSet<Integer> reSet=new HashSet<Integer>();
			for(Integer orgId:orgIdSet){
				if(orgId!=-1 && this.getNode(orgId)!=null){
					HashSet<Integer> sonSet=new HashSet<Integer>();
					
					List<SysOrganizationStructureTreeNode>OSTNL=this.getNode(orgId).getChildren();
					
					if(OSTNL!=null){
						for(SysOrganizationStructureTreeNode n:OSTNL){
							sonSet.add(n.getOrganizationStructureId());
							
						}
						reSet.addAll(this.getAllChildren(sonSet));
					}
				}
			}
			reSet.addAll(orgIdSet);
			return reSet;
		}
	}
	
	public List<SysOrganizationStructureTreeNode> getOSTreeNodes() {
		
		List<SysOrganizationStructureTreeNode> listOSTN = new ArrayList<SysOrganizationStructureTreeNode>();
		
		for (SysOrganizationStructure os : listOrganizationStructure) {

			SysOrganizationStructureTreeNode treeNode = new SysOrganizationStructureTreeNode();
			
//			ReflectionUtils.parentToChild(os, treeNode);
			
			BeanUtils.copyProperties(os, treeNode);
			
			treeNode.setId(os.getOrganizationStructureId());
			
			treeNode.setText(os.getName());
			
			listOSTN.add(treeNode);

		}
		
		return listOSTN;
	}

	public void setListOrganizationStructure(List<SysOrganizationStructure> listOrganizationStructure) {
		this.listOrganizationStructure = listOrganizationStructure;
	}

	public Map<Integer, SysOrganizationStructureTreeNode> getTree() {
		return tree;
	}
	
	public LinkedList<Integer> getDivision(Integer dId){
		LinkedList<Integer> ll=new LinkedList<Integer>();
		Integer cId=dId;
		do{
			ll.addLast(cId);
			cId=this.getNode(cId).getSuperiorId();	
		}while(!cId.equals(new Integer(0)));
		return ll;
	}

}

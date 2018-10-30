/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.utils
 * 类/接口名	: SysResourcesTree
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午6:08:06
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午6:08:06
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys.utils;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.stock.dao.model.sys.SysResource;



 /**
  * 资源数结构，定义资源的树形结构
  * 根据用户登陆的角色，构建index.html左面的菜单。
  * @author <a href="mailto:chengxl@stocksoft.com">chengxl</a>
  * @version $Id$   
  * @since 2.0
  */
  
public class SysResourceTree implements Serializable {

	/**
	 * serialVersionUID:TODO
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 5363421609532190022L;

	private List<SysResource> listResources;

	private Map<Integer, SysResourceTreeNode> tree = new HashMap<Integer, SysResourceTreeNode>();


	
	public  SysResourceTree(List<SysResource> listResources) {
		this.listResources = listResources;
		buildResourcesTree();
	}

	// 构造树
	private synchronized void buildResourcesTree() {

		for (SysResource res : listResources) {

			SysResourceTreeNode treeNode = new SysResourceTreeNode();
			
//			ReflectionUtils.parentToChild(res, treeNode);
			BeanUtils.copyProperties(res, treeNode);
			if (tree.containsKey(res.getParentId())) {
				SysResourceTreeNode parentNode = tree.get(res
						.getParentId());

				parentNode.addChild(treeNode);

			}

			tree.put(res.getResourceId(), treeNode);

		}
	}

	// 根据key,获得指定的节点
	public synchronized SysResourceTreeNode getNode(Integer key) {

		return tree.get(key);
	}

	// 获得节点的Ancestors node
	public synchronized List<SysResourceTreeNode> getAncestorsNodes(SysResourceTreeNode node) {
		List<SysResourceTreeNode> anceNodes = new ArrayList<SysResourceTreeNode>();

		this.setAnceNodes(node, anceNodes);
		return anceNodes;
	}

	// 递归依次获取该菜单的上级祖先菜单节点
	private synchronized void setAnceNodes(SysResourceTreeNode node,
			List<SysResourceTreeNode> menuNodes) {
	
		if (tree.containsKey(node.getParentId())) {
			SysResourceTreeNode parentNode = tree.get(node.getParentId());
			
			//是否启用的标识，并且 anceNodes不包含parentNode时添加到list中
			if("1".equals(parentNode.getStatus()) && !menuNodes.contains(parentNode)){
				menuNodes.add(parentNode); 
			}
			this.setAnceNodes(parentNode, menuNodes);
			
		}
	}

	// 获得节点的parent node
	public synchronized SysResourceTreeNode getParentNode(SysResourceTreeNode node) {

		return tree.get(node.getParentId());
	}
	
	// 获得节点的children node
	public synchronized List<SysResourceTreeNode> getChildrenNode(SysResourceTreeNode node) {

			return null;
	}

	/**
	 * 获取当前节点及其子节点的ID Set
	   * TODO 请在此处添加注释
	   * @param orgIdSet
	   * @return
	 */
	public Set<Integer> getAllChildrenResourceId(Set<Integer>  resourceIdSetPara){
		if(resourceIdSetPara ==null)
			return null;
	
		if(resourceIdSetPara.size()==0)
			return new HashSet<Integer>();
		else{
			HashSet<Integer> resourceIdSetReturn=new HashSet<Integer>();
			for(Integer resourceId:resourceIdSetPara){
				if(resourceId!=-1 && this.getNode(resourceId)!=null){
					
					HashSet<Integer> resourceIdSet=new HashSet<Integer>();
					
					List<SysResourceTreeNode> resourceNodes=this.getNode(resourceId).getChildren();
					
					if(resourceNodes != null){
						
						for(SysResourceTreeNode resourceNode:resourceNodes){
							resourceIdSet.add(resourceNode.getResourceId());
							
						}
						resourceIdSetReturn.addAll(this.getAllChildrenResourceId(resourceIdSet));
					}
				}
			}
			
			resourceIdSetReturn.addAll(resourceIdSetPara);
			
			return resourceIdSetReturn;
		}
	}
	
	// 判断是否根节点
	public synchronized boolean isRootNode(SysResourceTreeNode node) {

		return (node.getParentId() == null || node.getParentId() == 0) ? true
				: false;

	}

	// 返回指定节点的所有叶子节点
	public synchronized List<SysResourceTreeNode> getLeafNodes(SysResourceTreeNode node) {

		List<SysResourceTreeNode> leafNodes = new ArrayList<SysResourceTreeNode>();
		this.setLeafNodes(node, leafNodes);

		return leafNodes;
	}

	// 递归依次获取所有叶子节点
	private void setLeafNodes(SysResourceTreeNode node,
			List<SysResourceTreeNode> leafNodes) {
		
		List<SysResourceTreeNode> children = node.getChildren();

		if (children == null || children.size() < 1)
			leafNodes.add(node);

		else{
			for(SysResourceTreeNode child:children)

				this.setLeafNodes(child, leafNodes);

		}
			
	}

	public  List<SysResource> getListResources() {
		return listResources;
	}

	public synchronized void setListResources(List<SysResource> listResources) {
		this.listResources = listResources;
	}

	public  Map<Integer, SysResourceTreeNode> getTree() {
		return tree;
	}
	
	//根据访问的资源URL返回资源ID
	public  List<Integer> resourceMatching(String url){
		ArrayList<Integer> resourceIdList=new ArrayList<Integer>();
		for (SysResource resource :listResources ){
			if(!StringUtils.isEmpty(resource.getUrl() ) &&  url.contains(resource.getUrl())){  //  resource.getUrl().contains(url)
//			if(resource.getUrl() != null && resource.getUrl().contains(url)){
				resourceIdList.add(resource.getResourceId());
			}
		}
		return resourceIdList;
	}
	
	public  String typeOfResource(Integer resourceId){
		String re=null;
		for (SysResource res:this.listResources){
			if(res.getResourceId().equals(resourceId)){
				re=res.getType();
				break;
			}
		}
		return re;
	}

	public  Collection<Integer> getAncestors(Integer Id) {
		// TODO Auto-generated method stub
		HashSet <Integer> ancestors=new HashSet <Integer>();
		Integer currentId=Id;
		while(!this.getNode(currentId).getParentId().equals(new Integer(0))){
			currentId=this.getNode(currentId).getParentId();
			ancestors.add(currentId);
		}
		return ancestors;
	}
	
	/**
	 * 根据角色所赋予的资源，构造index.html 左边菜单的json 对象
	 * 
	 * 根据创建的menu菜单可将资源分为两类：
	 *     第一类是：menu资源节点，该节点的priority ！= null，
	 *     	其中 priority <100为一级menu节点    1000——10000之间为二级menu节点，100000——1000000之间为三级menu节点
	 *     第二类是：非menu资源节点，改节点的priority == null
	 * 菜单创建原则
	 * 1、菜单总共分3级，根节点系统，不创建菜单
	 * 2、根据角色所赋予的资源ID，依次找到需要创建的menu的父节点，并删除resourcesId=1的根节点
	 * 3、根据找到的所有menu依次创建菜单
	 * 第一步：创建一级菜单
	 * 第二步：根据一级菜单Node的childrenNodes创建2级菜单
	 * 第三步：根据二级菜单Node的childrenNodes创建3级菜单
	 * 
	 * 菜单样式
{
	  data: [{
            id: '1',
            text: '我的工作台 ',
            icon: 'fa fa-dashboard',
            url: '',
            menus: []
        },
            {
                id: '2',
                text: '运价管理',
                icon: 'fa fa-calendar',
                url: '',
                menus:[{
                    id: '2_1',
                    text: '海运管理',
                    icon: 'fa fa-circle-o',
                    url: '',
                    menus:[{
                        id: '2_1_1',
                        text: '等级管理',
                        icon: 'fa fa-circle-o',
                        url: 'freight/html/shipping_rank',
                        content:'shipping_rank'
                    },{
                        id: '2_1_2',
                        text: '基础运价管理(整箱)',
                        icon: 'fa fa-circle-o',
                        url: 'TariffManagement/html/BasicTariff',
                        content:'BasicTariff'
                    },{
                        id: '2_1_3',
                        text: '附加费管理(整箱)',
                        icon: 'fa fa-circle-o',
                        url: 'freight/html/shipping_surcharge',
                        content:'shipping_surcharge'
                    },{
                        id: '2_1_4',
                        text: '运价等级管理(整箱)',
                        icon: 'fa fa-circle-o',
                        url: 'TariffManagement/html/BasicTariff',
                        content:'BasicTariff'
                    },{
                        id: '2_1_5',
                        text: '客户特价(整箱)',
                        icon: 'fa fa-circle-o',
                        url: 'TariffManagement/html/BasicTariff',
                        content:'BasicTariff'
                    },{
                        id: '2_1_6',
                        text: '拼箱运价管理',
                        icon: 'fa fa-circle-o',
                        url: 'TariffManagement/html/BasicTariff',
                        content:'BasicTariff'
                    },{
                        id: '2_1_2',
                        text: '支线运价管理',
                        icon: 'fa fa-circle-o',
                        url: 'TariffManagement/html/BasicTariff',
                        content:'BasicTariff'
                    }]

                },{
                    id: '2_2',
                    text: '空运运价',
                    icon: 'fa fa-circle-o',
                    url: '',
                    menus:[{
                        id: '2_2_1',
                        text: '等级管理',
                        icon: 'fa fa-circle-o',
                        url: 'TariffManagement/html/BasicTariff',
                        content:'BasicTariff'
                    },{
                        id: '2_2_2',
                        text: '空运运价维护',
                        icon: 'fa fa-circle-o',
                        url: 'TariffManagement/html/BasicTariff',
                        content:'BasicTariff'
                    },{
                        id: '2_2_3',
                        text: '空运运价等级管理',
                        icon: 'fa fa-circle-o',
                        url: 'TariffManagement/html/BasicTariff',
                        content:'BasicTariff'
                    }]
                },{
                    id: '2_3',
                    text: '陆运运价',
                    icon: 'fa fa-circle-o',
                    url: '',
                    menus:[{
                        id: '2_3_1',
                        text: '内陆运输运价',
                        icon: 'fa fa-circle-o',
                        url: 'TariffManagement/html/BasicTariff',
                        content:'BasicTariff'
                    },{
                        id: '2_3_2',
                        text: '内陆运输等级价',
                        icon: 'fa fa-circle-o',
                        url: 'TariffManagement/html/BasicTariff',
                        content:'BasicTariff'
                    }]
                }]
            },
            
            ...
            
       }
	 */
	public synchronized JSONObject bulidMenuResourceTree(List<Integer> resourceIds){
		
		List<SysResourceTreeNode> menuNodes = new ArrayList<SysResourceTreeNode>();
		
		for(Integer rId: resourceIds){
			
			SysResourceTreeNode node = this.getNode(rId);
			System.out.println("node = "+node);
			//>0 表明该节点为menu节点
			if(node.getPriority() != null && node.getPriority() > 0)
				
				menuNodes.add(node);
			//menu节点的父节点，除根节点外，均为menu节点
			this.setAnceNodes(this.getNode(rId), menuNodes);
		}
		
		//删除 系统 根据节点
		menuNodes.remove(this.getNode(1));
		
		this.removeDuplicateWithOrder(menuNodes);
		
		JSONObject menuJSONObject = new JSONObject();
		JSONArray menus = new JSONArray();

		List<SysResourceTreeNode> childrenNodes = null;
		//构造menu json
		for(SysResourceTreeNode resourceNode:menuNodes){
//			if(resourceNode.getPriority() == null || resourceNode.getPriority() < 10)
//				continue;
			
			//priority 在（10-100),表示一级菜单
//			else
				if(resourceNode.getPriority() < 100){
//				System.out.println("parentID:" + resourceNode.getId());
//				for(SysResourcesTreeNode childNode :childrenNodes)
//					System.out.println("childRen:" + childNode.getId());
				
				menus.add(this.buildFirstLevelMenuNode(menuNodes,resourceNode));
//				menuNodes.remove(resourceNode);//移除当前的节点
			}
			
			
		}
		
//		menuJSONObject.put("menus", menus);
		
		menuJSONObject.put("data", menus);
		
		return menuJSONObject;
	}

	
	/**
	 * 从当前节点集合中获取指定节点的孩子节点
	 * @param anceNodes
	 * @param currentNode
	 * @return
	 */
	private List<SysResourceTreeNode> getChildren(List<SysResourceTreeNode> anceNodes,SysResourceTreeNode currentLevelNode){
		
		List<SysResourceTreeNode> childrenNodes = new ArrayList<SysResourceTreeNode>();
		
		for(SysResourceTreeNode resourceNode:anceNodes){
//			System.out.println("aaa:"+resourceNode.getId());
			if(currentLevelNode.getResourceId().equals(resourceNode.getParentId() ))
				
				childrenNodes.add(resourceNode);
			
		}
		return childrenNodes;
	}
	
	/**
	 * 移除相同的父节点
	 */
	private  void removeDuplicateWithOrder(List<SysResourceTreeNode> list) {
		   Set<SysResourceTreeNode> set = new HashSet<SysResourceTreeNode>();
		   List<SysResourceTreeNode> newList = new ArrayList<SysResourceTreeNode>();
		   for (Iterator iter = list.iterator(); iter.hasNext();) {
		          Object element = iter.next();
		          if (set.add((SysResourceTreeNode)element))
		             newList.add((SysResourceTreeNode)element);
		       }
		      list.clear();
		      list.addAll(newList);
		      
		      this.sortResourceNodeList(list);
//		     System.out.println( " remove duplicate " + list);
		}
	
	
	/**
	 * 
	   * 根据资源的priority 值进行排序
	   * @param list
	 */
	
	private  void sortResourceNodeList(List<SysResourceTreeNode> list) {
		
		Collections.sort(list, new Comparator<SysResourceTreeNode>() {
	    @Override
		public int compare(SysResourceTreeNode rtn1,
	    		SysResourceTreeNode rtn2) {
	        return rtn1.getPriority().compareTo(rtn2.getPriority());
	    }
	});
	}
	
	/**
	 * 构建一级菜单 priority值在 10——99之间
	 * 节点的样式
	 * 
	*/

	private JSONObject buildFirstLevelMenuNode(List<SysResourceTreeNode> menuNodes,SysResourceTreeNode firstLevelNode){
		
		JSONObject firstLevelmenuNode = new JSONObject();
		JSONArray secondLevelMenus = new JSONArray();
		
		//从当前父节点集合中，获取一级菜单的二级孩子节点
		List<SysResourceTreeNode> childrenNodes = this.getChildren(menuNodes,firstLevelNode); 
		
		for(SysResourceTreeNode secondLevelNode:childrenNodes){
			
//			if(secondLevelNode.getPriority() == null || secondLevelNode.getPriority() < 1000)
//				continue;
			//priority 在（1000-10000),表示二级菜单
//			else
				if(secondLevelNode.getParentId() <10000){
					secondLevelMenus.add(this.buildSecondLevelMenuNode(menuNodes,secondLevelNode));
//				menuNodes.remove(secondLevelNode);//移除以构建菜单的节点
				
			}
			
		}
		
//		firstLevelmenuNode.put("menuid", firstLevelNode.getResourceId());
//		firstLevelmenuNode.put("menuname", firstLevelNode.getText());
		
		String url = firstLevelNode.getUrl();
		firstLevelmenuNode.put("id", firstLevelNode.getResourceId());
		firstLevelmenuNode.put("text", firstLevelNode.getText());
		firstLevelmenuNode.put("url",url);
		firstLevelmenuNode.put("content", this.convertUrlToContent(url));

		firstLevelmenuNode.put("icon",firstLevelNode.getIcon());
		firstLevelmenuNode.put("menus", secondLevelMenus);
		
		return firstLevelmenuNode;
	}
	
	private String convertUrlToContent(String url){
		
		if(StringUtils.isEmpty(url))
			return "";
		
		String[] names = url.split("/");
		
		return names[names.length-1];
		
	}
	
	/**
	   * 二级菜单节点 priority  1000——9999
	   * @param secondLevelNode
	   * @param childrenNodes
	   * @return
	 */
	

	private JSONObject buildSecondLevelMenuNode(List<SysResourceTreeNode> menuNodes,SysResourceTreeNode secondLevelNode){
		
		JSONObject secondLevelmenuNode = new JSONObject();
		JSONArray thirdLevelMenus = new JSONArray();
		
		//从当前父节点集合中，获取一级菜单的二级孩子节点
		List<SysResourceTreeNode> childrenNodes = this.getChildren(menuNodes,secondLevelNode); 
		
		
		for(SysResourceTreeNode thirdLevelNode:childrenNodes){
			
//			if(thirdLevelNode.getPriority() == null || thirdLevelNode.getPriority() < 100000)
//				continue;
			//priority 在（100000-1000000),表示三级菜单
//			else 
			if(thirdLevelNode.getParentId() <1000000){
				
				thirdLevelMenus.add(this.buildThirdLevelMenuNode(thirdLevelNode));
//				menuNodes.remove(thirdLevelNode);//移除以构建菜单的节点
			}
			
		}
		
//		secondLevelmenuNode.put("menuid", secondLevelNode.getResourceId());
//		secondLevelmenuNode.put("menuname", secondLevelNode.getText());
		String url = secondLevelNode.getUrl();
		secondLevelmenuNode.put("id", secondLevelNode.getResourceId());
		secondLevelmenuNode.put("text", secondLevelNode.getText());
		secondLevelmenuNode.put("icon",secondLevelNode.getIcon());
		secondLevelmenuNode.put("url", url);
		secondLevelmenuNode.put("content", this.convertUrlToContent(url));
		secondLevelmenuNode.put("menus", thirdLevelMenus);
		
		return secondLevelmenuNode;
	}
	
	/**
	 * 三级节点，最后一级  priority   100000——999999
	 * 
	 * 节点的样式
	 {
	    "menuid": "11",
		"menuname": "海运报价管理",
		"icon": "icon-sum",
		"url": "demo/quotation.html"
	}
	*/

	private JSONObject buildThirdLevelMenuNode(SysResourceTreeNode resourcesTreeNode){
		
		JSONObject menuNode = new JSONObject();
		
		String url = resourcesTreeNode.getUrl();
		
		menuNode.put("id", resourcesTreeNode.getResourceId());
		menuNode.put("text", resourcesTreeNode.getText());
		menuNode.put("icon",resourcesTreeNode.getIcon());
		menuNode.put("url",url);
		
		menuNode.put("content",this.convertUrlToContent(url));
		return menuNode;
	}
	
}

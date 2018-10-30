package com.stock.webapp.base.controller.pcweb;

import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.sys.SysBasicDataSet;
import com.stock.service.sys.SysBasicDataSetService;
import com.stock.service.sys.utils.HttpJson;
import com.stock.service.sys.utils.SysBasicDataSetTreeNode;
import com.stock.service.sys.utils.SysConst;


@Controller
@RequestMapping(value="/basicdataset")
 
public class SysBasicDataSetController {

	@Autowired
	private SysBasicDataSetService basicDataSetService;
	
	@RequestMapping(value = "/getBasicDataSetbyParentId.do")
	public @ResponseBody List<SysBasicDataSetTreeNode>  getBasicDataSetbyParentId(String parentId){ 
		
		if(StringUtils.isEmpty(parentId))
			return null;
		

//		return basicDataSetService.getBasicDataSet(Integer.parseInt(parentId));
		return SysConst.BASICDATASET_TREE.getNode(Integer.parseInt(parentId)).getChildren();
	}
	
	@RequestMapping(value = "/listBDSTree.do")
	public @ResponseBody List<SysBasicDataSetTreeNode> listBDSTree(){ 

		return SysConst.BASICDATASET_TREE.getBDSTreeNodes();
	}
	
	@RequestMapping(value = "/listParentBDSTree.do")
	public @ResponseBody List<SysBasicDataSetTreeNode> listParentBDSTree(){ 
		
		return SysConst.BASICDATASET_TREE.getBDSParentTreeNodes();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert.do")
	public @ResponseBody HttpJson insert(
			SysBasicDataSet bds){ 

		
	//	JSONObject joBDS = JSONObject.fromObject(request.getParameter("bds"));

		HttpJson j = new HttpJson();
		if(basicDataSetService.insert(bds)){
			j.setSuccess(true);
			j.setMsg("基本信息保存成功!");
		}
		else{
			j.setSuccess(false);
			j.setMsg("基本信息保存成功!");
		}		
		return j;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/update.do")
	public @ResponseBody HttpJson update(
			SysBasicDataSet bds){ 
		
		//JSONObject joBDS = JSONObject.fromObject(request.getParameter("bds"));
			
		HttpJson j = new HttpJson();
		
		
		if(basicDataSetService.update(bds)){
			j.setSuccess(true);
			j.setMsg("基本信息修改成功!");
		}
		else{
			j.setSuccess(false);
			j.setMsg("基本信息修改失败!");
		}		
		return j;
	}
	

	@RequestMapping(value = "/delete.do")
	public @ResponseBody Object delete(String id){ 
		
		if(id == null || "".equals(id))
			return "no found";
		
		HttpJson j = new HttpJson();
		
		
		if(basicDataSetService.delete(Integer.parseInt(id))){
			j.setSuccess(true);
			j.setMsg("基本信息删除成功!");
		}
		else{
			j.setSuccess(false);
			j.setMsg("包含子节点的基本信息不能删除，删除失败！");
		}		
		return j;

	}
	
}

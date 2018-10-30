/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.controller
 * 类/接口名	: CommonController
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午9:20:17
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午9:20:17
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.webapp.base.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.service.common.util.CommonUtils;
import com.stock.service.common.util.DateJsonValueProcessor;
import com.stock.service.common.util.DateMorpherEx;
import com.stock.service.sys.CommonService;

/**
 * ClassName	: CommonController
 * Function		: 该类为公共控制器，定义通用的list,update,insert,delete，getById等方法。
 * Parameter	:T 代表model，M mapper，E 代表 exmaple
 * date 		: 2017年5月7日下午9:20:17
 * @author chengxl
 * @version		: 
 * @since   JDK 1.7
 */
public class CommonController<T,M,E> {
	
	private CommonService<T,M,E> commonService;
	
	
	/**
	 * setCommonService:注入具体的实现类. <br/> 
	 * 子类继承时，需注入一个CommonService的实现类.<br/> 
	 * 
	 * @author chengxl 
	 * @param commonService 
	 * @since JDK 1.7 
	 */
	public void setCommonService( CommonService<T,M,E> commonService) {
		this.commonService = commonService;
	}

	/*
	 * 根据documentId获取文件办理内容
	 */
	/**
	 * getById:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param req
	 * @return 
	 * @since JDK 1.7 
	 */
	public @ResponseBody Object getById(Integer id,M mapper){
		
//		int id = Integer.parseInt(req.getParameter("id"));
		
		Object ob = commonService.getById(id,mapper);
		
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));
		
	
		return JSONObject.fromObject(ob,config);
	}
	
	/**
	 * listByPage:按页获取数据. <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param req
	 * @return 
	 * @since JDK 1.7 
	 */
	public @ResponseBody
	Object listByPage(HttpServletRequest req, T model,M mapper,E example) {

		// 注册时间转换器，处理传入的JSON数据中的日期格式
//		JSONUtils.getMorpherRegistry().registerMorpher(
//				new DateMorpherEx(new String[] { "yyyy-MM-dd" }, (Date) null));

		String keys = req.getParameter("keys");

		Integer length = Integer.parseInt(req.getParameter("length"));
		Integer start = Integer.parseInt(req.getParameter("start"));

		Map<String, Object> map = 	commonService.getByPage(keys, length, start, model, mapper, example);
		
		
//		RequestResult rr
//				getByPage(keys, pageSize,
//				pageIndex,  model, mapper, example);
		

		return JSONObject.fromObject(map);
	}

	
	/**
	 * listByKeys:按页获取数据. <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param req
	 * @return 
	 * @since JDK 1.7 
	 */
	public @ResponseBody
	Object listByKeys(HttpServletRequest req, T model,M mapper,E example) {



		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpherEx(new String[] { "yyyy-MM-dd" }, (Date) null));

		String keys = req.getParameter("keys");

	
		List<T> modelList = 	commonService.getByKeys(keys, model, mapper, example);
		
		
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

		return JSONArray.fromObject(modelList,config);
	}

	/**
	 * insert:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param req
	 * @param e
	 * @return 
	 * @since JDK 1.7 
	 */
	public @ResponseBody
	Object insert(HttpServletRequest req,T model,M mapper) {

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		String info = req.getParameter("inserted");

		if (StringUtils.isEmpty(info))
			return "false";

		return commonService.insert(this.convert(info,model),mapper);

	}
	
	
	/**
	 * insert:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param req
	 * @param e
	 * @return 
	 * @since JDK 1.7 
	 */
	public @ResponseBody
	Object insert(T model,M mapper) {

	

		return commonService.insert(model,mapper);

	}
	
	
	/**
	 * insert:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param req
	 * @param e
	 * @return 
	 * @since JDK 1.7 
	 */
	public @ResponseBody
	Object update(T model,M mapper) {

	

		return commonService.update(model,mapper);

	}
	
	
	
	/**
	 * convert:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param info
	 * @param e
	 * @return 
	 * @since JDK 1.7 
	 */
	private  T convert(String info,T model ){
		
//		JSONObject info = JSONObject.fromObject(aflInfo);
		
		JSONArray jsonArray = JSONArray.fromObject(info);
		
		
		//移除vo中的  baseModel属性
		for(int i = 0; i < jsonArray.size();i ++){
			JSONObject jo = (JSONObject)jsonArray.get(i);
			
			if(jo.containsKey("baseModel") )
				jo.remove("baseModel");
			
		}
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) JSONArray.toCollection(
				jsonArray, model.getClass());
		
		for (T o : list) {
			
			return o;
		}
		
		return null;
	}

	
	/**
	 * update:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param req
	 * @param e
	 * @return 
	 * @since JDK 1.7 
	 */
	public @ResponseBody
	Object update(HttpServletRequest req,T model,M mapper) {

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		String info = req.getParameter("updated");

		if (StringUtils.isEmpty(info))
			return "false";

		return commonService.update(this.convert(info,model),mapper);
	}

	

//	/**
//	 * delete:(这里用一句话描述这个方法的作用). <br/> 
//	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
//	 * 
//	 * @author chengxl 
//	 * @param req
//	 * @return 
//	 * @since JDK 1.7 
//	 */
//	public @ResponseBody
//	Object delete(HttpServletRequest req,String idFieldName,M mapper,E example) {
//		
//		String ids = req.getParameter("ids");
//		
//		return commonService.deleteByIds(idFieldName,CommonUtils.idsArrayToList(ids),mapper,example);
//		
//	
//	}
	
	
	/**
	 * delete:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * 
	 * @author chengxl 
	 * @param req
	 * @return 
	 * @since JDK 1.7 
	 */
	public @ResponseBody
	Object delete(String ids,String idFieldName,M mapper,E example) {
		
//		String ids = req.getParameter("ids");
		
		return commonService.deleteByIds(idFieldName,CommonUtils.idsArrayToList(ids),mapper,example);
		
	
	}

	

	@InitBinder    
	public void initBinder(WebDataBinder binder) {    
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
	        dateFormat.setLenient(false);    
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
	        
	}  
	
	

}

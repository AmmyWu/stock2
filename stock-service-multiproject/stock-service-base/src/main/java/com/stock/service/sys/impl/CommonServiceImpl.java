package com.stock.service.sys.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stock.common.exceptions.BizException;
import com.stock.common.tool.log.ErrorLoggers;
import com.stock.common.tool.log.LogUtil;
import com.stock.common.tool.string.StringUtil;
import com.stock.common.util.HttpResponseConstants;
import com.stock.common.util.HttpResponseConstants.Public;
import com.stock.common.util.ReflectionUtil;
import com.stock.dao.page.Page;
import com.stock.pojo.vo.BaseModelVO;
import com.stock.pojo.vo.RequestResultVO;
import com.stock.service.common.DataAuthorizeService;
import com.stock.service.common.util.CriteriaUtils;
import com.stock.service.common.util.DateJsonValueProcessor;
import com.stock.service.sys.CommonService;
import com.stock.service.sys.utils.SysConst;
import com.stock.service.sys.utils.SysOrganizationStructureTreeNode;



/**
 * ClassName : BaseServiceImpl Function : TODO date : 2017年5月15日上午9:57:21
 * 
 * @author chengxl
 * @version :
 * @since JDK 1.7
 */
@Lazy
@Service("commonService")
public class CommonServiceImpl implements CommonService<Object, Object, Object> {

	/**
	 * Create a new instance of CommonServiceImpl
	 */
	
	@Autowired
	private DataAuthorizeService dataAuthorizeService;
	
/*	@Autowired
	private RedisServiceImpl redisService;*/
	
	public CommonServiceImpl() {
		// TODO Auto-generated constructor stub

	}
	
	@Transactional
	public RequestResultVO update(Object model, Object mapper) {

		Object result;
		RequestResultVO rr = new RequestResultVO();

		try {
			Object[] paras = new Object[] { model };
			
			// 修改时，根据前端传的amender、amendTime、accessGroup,设置creator、createTime 和 accessGroup
			dataAuthorizeService.addDataAuthorizeInfo(model,"update");

			result = ReflectionUtil.invokeMethod(mapper,
					"updateByPrimaryKeySelective", paras);

			Integer i = (Integer) result;

			if (i < 1){
				rr.setMessage("修改失败");
				throw new BizException(Public.ERROR_CODE,"修改失败");
			}else{
				rr.setCode(HttpResponseConstants.Public.SUCCESS_CODE);
				rr.setMessage("修改成功！");
			}
			

		} catch (Exception e) {

			rr.setMessage("访问异常，修改失败！");
			LogUtil.error(ErrorLoggers.ERROR_LOGGER, "访问异常，修改失败！", e);
			throw new BizException(Public.ERROR_CODE,"访问异常，修改失败！");
		}
		// if (equipmentMapper.updateByPrimaryKeySelective(equipment) < 1)
		// return "修改失败";
		return rr;
	}
	
	public RequestResultVO insert(Object model, Object mapper) {
		// TODO Auto-generated method stub

		Object result;
		RequestResultVO rr = new RequestResultVO();

		Object[] params = new Object[] { model };

		try {
			
			//设置creator、createTime 和 accessGroup
//			this.setValueForInsert(model);
			
			// 插入时，根据前端传的amender、amendTime、accessGroup,设置creator、createTime 和 accessGroup
			dataAuthorizeService.addDataAuthorizeInfo(model,"insert");
			
			result = ReflectionUtil.invokeMethod(mapper, "insertSelective",
					params);

			Integer i = (Integer) result;

			if (i == 0) {
				rr.setCode(1);
				rr.setMessage("数据访问失败，操作异常");
				throw new BizException("新增失败");
			}

			rr.setCode(0);
			rr.setMessage("新增成功！");

			// rr.setObj(model);

		} catch (Exception e) {

			rr.setCode(1);
			rr.setMessage("数据访问失败，操作异常");
			LogUtil.error(ErrorLoggers.ERROR_LOGGER, "访问异常，操作失败！", e);
			throw new BizException("数据访问失败，操作异常");
		}

		return rr;
	}
	
	@Override
	public boolean isExistModel(Object model, Object mapper,Object example,String[] fieldNames,String[] fieldValues,String keyFieldName,Integer keyId,String type) throws Exception{


		int i = 0;
		for(String fieldValue :fieldValues){
			
			if(!StringUtil.isBlank(fieldValue)){
				
				// 创建example的criteria
				Object criteria = ReflectionUtil.invokeMethod(example,
						"createCriteria");
				
				Object[] params = new Object[] { fieldValue };
				
				ReflectionUtil.invokeMethod(criteria, "and" + fieldNames[i]+ "EqualTo",
						params);
				
				
				params = new Object[] { criteria };
				
				ReflectionUtil.invokeMethod(example, "or",
						params);
				
			}
			i++;
		}
		
		Object[] params = new Object[] { example };
		
		List<Object> objects = (List<Object>) ReflectionUtil.invokeMethod(
				mapper, "selectByExample", params);
		
		if("insert".equals(type) )
			if(objects.size() > 0)
				return true;
			else
				return false;
		
		if("update".equals(type)){
			if(objects.size() >0){
				
				for(Object object : objects){
					
					
					Integer id = (Integer) ReflectionUtil.invokeMethod(
							object, "get"+keyFieldName);
					
					if(id.intValue() != keyId.intValue())
						return true;
						
				}
				
			}else
				return false;
			
		}
		
		return false;
	}

	
	@Override
	public RequestResultVO deleteById(Integer id, Object mapper) {

		Object result;
		RequestResultVO rr = new RequestResultVO();

		Object[] params = new Object[] { id };

		try {
			result = ReflectionUtil.invokeMethod(mapper, "deleteByPrimaryKey",
					params);

			Integer i = (Integer) result;

			if (i == 0)
				rr.setMessage("删除失败!");

			rr.setMessage("删除成功！");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtil.error(ErrorLoggers.ERROR_LOGGER, "删除失败！", e);

			rr.setCode(1);

		}

		return rr;

	}

	@Override
	public RequestResultVO deleteByIds(String idFieldName, List<Integer> ids,
			Object mapper, Object example) {

		Object result;
		RequestResultVO rr = new RequestResultVO();

		try {

			// Method createCriteria = example.getClass().getMethod(
			// "createCriteria");
			//
			// // Object[] arguments = new Object[]{employeeId};
			// // 执行方法
			// Object tCriteria = createCriteria.invoke(example);
			//
			// tCriteriaList.add((Object) tCriteria);

			// 创建example的criteria
			Object criteria = ReflectionUtil.invokeMethod(example,
					"createCriteria");

			Object[] params = new Object[] { ids };

			// employyeId 转为 EmployeeId
			int length = idFieldName.length();
			idFieldName = idFieldName.substring(0, 1).toUpperCase()
					+ idFieldName.substring(1, length);

			// example.createCriteria().andEquipmentIdIn(ids);

			ReflectionUtil.invokeMethod(criteria, "and" + idFieldName + "In",
					params);

			params = new Object[] { example };

			result = ReflectionUtil.invokeMethod(mapper, "deleteByExample",
					params);

			Integer i = (Integer) result;

			if (i == 0){
				rr.setCode(HttpResponseConstants.Public.ERROR_CODE);
				rr.setMessage("删除失败!");
			}
			else{
				rr.setCode(HttpResponseConstants.Public.SUCCESS_CODE);
				rr.setMessage("删除成功！");
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
		    LogUtil.error(ErrorLoggers.ERROR_LOGGER, "删除失败！", e);

			rr.setCode(1);

			rr.setMessage("数据库访问异常，删除失败！");

		}
		return rr;
	}

	@Override
	public Object getByKey(String key, Object mapper) {
		return null;
	}

	
	/**
	 * 
	 * 根据Id返回指定对象
	 * @see com.seawin.service.common.CommonService#getById(java.lang.Integer, java.lang.Object)
	 */
	@Override
	public Object getById(Integer id, Object mapper) {

		Object result = null;
//		RequestResultVO rr = new RequestResultVO();

//		if (id == null) {
//
//			rr.setCode(1);
//			rr.setMessage("id为空，获取数据失败！");
//
//			return rr;
//		}

		Object[] params = new Object[] { id };
		try {
			result = ReflectionUtil.invokeMethod(mapper, "selectByPrimaryKey",
					params);

//			rr.setCode(0);
//			rr.setData(result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtil.error(ErrorLoggers.ERROR_LOGGER, "数据库访问异常，操作失败！", e);

//			rr.setCode(1);
//			rr.setMessage("数据库访问异常，操作失败！");

		}

		return result;

	}

	@Override
	public Map<String, Object> getByPage(String keys, Integer length,
			Integer start, Object model, Object mapper, Object example) {

		return getByPageDateFormat(keys, length, start, model, mapper, example,"yyyy-MM-dd HH:mm:ss");
	}
	
	@Override
	public Map<String, Object> getByPageDateFormat(String keys, Integer length, Integer start, Object model,
			Object mapper, Object example,String dateFormate) {
		return this.getByPageDateFormat(keys, length, start, model, mapper, example, dateFormate, " amend_time desc");
	}
	@Override
	public Map<String, Object> getByPageDateFormat(String keys, Integer length, Integer start, Object model,
			Object mapper, Object example,String dateFormate, String orderBy) {
		this.setExample(keys,model, mapper, example);
		//添加数据权限
		dataAuthorizeService.setExampleByAuthorization(example);
		// // criteria.andEmployeeIdIsNotNull();
		// int totalrecords = equipmentMapper.countByExample(example);
		
		Object result;
		RequestResultVO rr = new RequestResultVO();
		
		Object[] params = new Object[] { example };
		
		Map<String, Object> map = null;
		
		try {
			result = ReflectionUtil.invokeMethod(mapper, "countByExample",
					params);
			
			int totalrecords = (Integer) result;
			
			// 设置分页信息
			Page page = new Page();
			
			page.setBegin(start);//
			page.setLength(length);
			
			params = new Object[] { orderBy };
			
			ReflectionUtil.invokeMethod(example, "setOrderByClause", params);
			
			params = new Object[] { page };
			
			// example.setPage(page);
			ReflectionUtil.invokeMethod(example, "setPage", params);
			
			params = new Object[] { example };
			
			List<Object> objects = (List<Object>) ReflectionUtil.invokeMethod(
					mapper, "selectByExample", params);
			
			map = new HashMap<String, Object>();
			// map.put("total", totalrecords);
			// if (totalrecords % pageSize == 0)
			// map.put("totalPages", totalrecords / pageSize);
			// else
			// map.put("totalPages", totalrecords / pageSize + 1);
			JsonConfig config = new JsonConfig();
			config.setIgnoreDefaultExcludes(false);
			config.registerJsonValueProcessor(Date.class,
					new DateJsonValueProcessor(dateFormate));
			
			// map.put("rows", JSONArray.fromObject(converToVO(objects,model),
			// config));
			map.put("aaData",
					JSONArray.fromObject(converToVO(objects, model), config));
			
			map.put("recordsTotal", totalrecords);// total
			map.put("recordsFiltered", totalrecords);// total
			// rr.setCode(0);
			// rr.setData(result);
			// rr.setObj(map);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			rr.setCode(1);
			LogUtil.error(ErrorLoggers.ERROR_LOGGER, "数据库访问异常，操作失败！", e);
			
		}
		return map;
	}
	
	
	@Override
	public  Map<String, Object>  getByPageForBusiness(String keys, Integer length,
			Integer start, Object model, Object mapper, Object example) {

		this.setExample(keys,model, mapper, example);

	
		Object result;
//		RequestResultVO rr = new RequestResultVO();

		Object[] params = new Object[] { example };

		Map<String, Object> map = null;

		try {
			result = ReflectionUtil.invokeMethod(mapper, "countByExample",
					params);

			int totalrecords = (Integer) result;

			// 设置分页信息
			Page page = new Page();

			page.setBegin(start);//
			page.setLength(length);

			params = new Object[] { " amend_time desc" };

			ReflectionUtil.invokeMethod(example, "setOrderByClause", params);

			params = new Object[] { page };

			// example.setPage(page);
			ReflectionUtil.invokeMethod(example, "setPage", params);

			params = new Object[] { example };

			List<Object> objects = (List<Object>) ReflectionUtil.invokeMethod(
					mapper, "selectByExample", params);

			map = new HashMap<String, Object>();
			
			map.put("aaData",objects);

			map.put("recordsTotal", totalrecords);// total
			map.put("recordsFiltered", totalrecords);// total



		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtil.error(ErrorLoggers.ERROR_LOGGER, "commonService查询异常", e);
//			rr.setCode(1);

		}
		return map;
	}

	
	private List<Object> converToVO(List<Object> objects, Object model) {

		List<Object> objectVOs = new ArrayList<Object>();

		Class VOClass;
		try {
			// 根据model对象名找到对应的modelVO类
			String modelName = model.getClass().getName();

			String modelVOName = modelName.replace(".dao.model", ".pojo.vo")
					+ "VO";

			VOClass = Class.forName(modelVOName);

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			LogUtil.error(ErrorLoggers.ERROR_LOGGER, "转换异常", e1);

			return objects;
		}


		try {

			for (Object businessModel : objects) {

				Object businessModelVO = VOClass.newInstance();

				BeanUtils.copyProperties(businessModel, businessModelVO);

				//通过baseModel将创建人，修改人，组织架构等Id转为Name
				this.addBaseModel(businessModel,businessModelVO);
				
				objectVOs.add(businessModelVO);

			}

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			LogUtil.error(ErrorLoggers.ERROR_LOGGER, "转换异常", e);
			throw new BizException(e,"转换异常");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			LogUtil.error(ErrorLoggers.ERROR_LOGGER, "转换异常", e);
			throw new BizException(e,"转换异常");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtil.error(ErrorLoggers.ERROR_LOGGER, "转换异常", e);
			throw new BizException(e,"转换异常");
		}

		return objectVOs;

	}
	
	/**
	 * 根据businessModelVO中的creator,amender,accessgroup等字段值，设置businessModelVO中的baseModel的creatorName，amenderName，organiztionName的值
	 * 
	 * @author chengxl
	 * @param businessModel
	 *        businessModelVO
	 * @throws Exception 
	 * @since JDK 1.7 
	 */
	@Override
	public void addBaseModel(Object businessModel,Object businessModelVO) throws Exception{
		
		BaseModelVO baseModel =  new BaseModelVO();
		Object[] params = null;
		
		//  get from redies
//		if(ReflectionUtil.getFieldValue(businessModel,"creator") == null)
//			params = new Object[]{"空"};
//		else {
//			params = new Object[] {
//					redisService.opsForHash().hget("seawin:HASH_IDNAME_employee",
//							(Integer)ReflectionUtil.getFieldValue(businessModel,"creator")) == null?"空":
//								redisService.opsForHash().hget("seawin:HASH_IDNAME_employee",
//										(Integer)ReflectionUtil.getFieldValue(businessModel,"creator")) 
//			};
//		}
		//ReflectionUtil.getFieldValue(businessModel,"creator")
		
		if( ReflectionUtil.invokeMethod(businessModel, "getCreator")== null)
			params = new Object[]{"空"};
		else
			params = new Object[] { SysConst.EMPLOYEE_MAP.get((Integer)ReflectionUtil.invokeMethod(businessModel, "getCreator")) == null?"空":
				SysConst.EMPLOYEE_MAP.get((Integer)ReflectionUtil.invokeMethod(businessModel, "getCreator"))};

		
		ReflectionUtil
				.invokeMethod(baseModel, "setCreatorName", params);

//		params = new Object[] { "test" };
		
		// get from redis
//		if(ReflectionUtil.getFieldValue(businessModel,"amender") == null)
//			params = new Object[]{"空"};
//		else {
//			params = new Object[] {
//					redisService.opsForHash().hget("seawin:HASH_IDNAME_employee",
//							(Integer)ReflectionUtil.getFieldValue(businessModel,"amender")) == null?"空":
//								redisService.opsForHash().hget("seawin:HASH_IDNAME_employee",
//										(Integer)ReflectionUtil.getFieldValue(businessModel,"amender")) 
//			};
//		}
		if(ReflectionUtil.invokeMethod(businessModel, "getAmender") == null)
			params = new Object[]{"空"};
		else
			params = new Object[] { SysConst.EMPLOYEE_MAP.get((Integer)ReflectionUtil.invokeMethod(businessModel, "getAmender")) == null?"空":
					SysConst.EMPLOYEE_MAP.get((Integer)ReflectionUtil.invokeMethod(businessModel, "getAmender")) };

		ReflectionUtil
				.invokeMethod(baseModel, "setAmenderName", params);
		
		if(ReflectionUtil.invokeMethod(businessModel, "getAccessGroup") == null)
			params = new Object[]{"空"};
		else{
			SysOrganizationStructureTreeNode osTree = SysConst.ORGANIZATIONSTRUCTURE_TREE.getNode(
				(Integer)ReflectionUtil.invokeMethod(businessModel, "getAccessGroup")) ;
			
			
			params = new Object[] {osTree == null?"空":osTree.getName()};
		}
		
		
		ReflectionUtil.invokeMethod(baseModel, "setOrganiztionName",
				params);
	
		params = new Object[] { baseModel };

		ReflectionUtil.invokeMethod(businessModelVO, "setBaseModel", params);
		
	}
	
	@Override
	public List<Object> getByKeys(String keys, Object model, Object mapper,
			Object example) {

		this.setExample(keys,model, mapper, example);

		// // criteria.andEmployeeIdIsNotNull();
		// int totalrecords = equipmentMapper.countByExample(example);

		Object result;

		Object[] params = new Object[] { example };

		Map<String, Object> map = null;

		try {


			params = new Object[] { example };

			List<Object> objects = (List<Object>) ReflectionUtil.invokeMethod(
					mapper, "selectByExample", params);

			map = new HashMap<String, Object>();
			

			return objects;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtil.error(ErrorLoggers.ERROR_LOGGER, "异常", e);
            throw new BizException(e,"异常");

		}

	}

	
	@Override
	public void setExample(String keys,Object model, Object mapper, Object example
			) {

		if (keys == null || "{}".equals(keys))
			return;

		JSONObject jKeys = JSONObject.fromObject(keys);

		// 创建example的criteria
		Object criteria = null;
		try {
//			criteria = ReflectionUtil.invokeMethod(example, "createCriteria");
			//判断是否有 criteria,并且获取
			criteria = CriteriaUtils.judgeAndGetCriteria(example);
			//设置isDelete ！= 1 ，表示未删除的记录
//			ReflectionUtil.setObjectValues(criteria,"andIsDeletedNotEqualTo", "Integer java.lang.Integer",1);

			Object keyModel = (Object) jKeys.toBean(jKeys, model.getClass());

			if (keyModel == null)
				return;

			// 获得news model非空（null）属性list
			List<Map<?, ?>> list = ReflectionUtil.getFiledValues(keyModel,
					false);

			// 根据输入条件，设置Criteria
			CriteriaUtils.setCriteria(criteria, list);

		} catch (Exception e) {
			LogUtil.error(ErrorLoggers.ERROR_LOGGER, "异常", e);
            throw new BizException(e,"异常");
		}

	}

	@Override
	public List<Object> getByModel(Object model, Object mapper, Object example) {
		try {
			this.setExampleModel(model, example);
			Object[] params =new Object[]{example};
			List<Object> list = (List<Object>)ReflectionUtil.invokeMethod(mapper, "selectByExample",params);
			return list;
		} catch (Exception e) {
			LogUtil.error(ErrorLoggers.ERROR_LOGGER, Public.ERROR_100, e);
			throw new BizException(Public.ERROR_100);
		}
	}
	
	@Override
	public int countByModel(Object model, Object mapper, Object example) {
		if(model==null){
			return 0;
		}
		try {
			this.setExampleModel(model, example);
			Object[] params =new Object[]{example};
			int i = (int) ReflectionUtil.invokeMethod(mapper, "countByExample",params);
			return i;
		} catch (Exception e) {
			LogUtil.error(ErrorLoggers.ERROR_LOGGER, "失败", e);
			throw new BizException("失败");
		}
	}

	@Override
	public void setExampleModel(Object model, Object example) throws Exception{
			if (model == null){
				return;
			}
			//判断是否有 criteria,并且获取
			Object criteria = CriteriaUtils.judgeAndGetCriteria(example);
			// 获得 model非空（null）属性list
			List<Map<?, ?>> filedValues = ReflectionUtil.getFiledValues(model, false);
			// 根据输入条件，设置Criteria
			CriteriaUtils.setCriteria(criteria, filedValues);
	}
	
	

	/** 
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.seawin.service.common.CommonService#saveList(java.lang.Object, java.lang.Object, int)
	 */
	@Override
	public int saveByExchangeOperate(Object modelVO, Object mapper, Integer id) throws Exception {
		int a;
		Object[] o =new Object[]{modelVO};
		Object[] i =new Object[]{id};
		String operate = (String) ReflectionUtil.getFieldValue(modelVO, "exchange_operate");
		if("insert".equals(operate)){
			dataAuthorizeService.addDataAuthorizeInfo(modelVO, "insert");
			a=(int) ReflectionUtil.invokeMethod(mapper, "insertSelective", o);
		}else if("delete".equals(operate)){
			a=(int) ReflectionUtil.invokeMethod(mapper, "deleteByPrimaryKey", i);
		}else{
			dataAuthorizeService.addDataAuthorizeInfo(modelVO, "update");
			a=(int) ReflectionUtil.invokeMethod(mapper, "updateByPrimaryKeySelective", o);
		}
		return a;
	}
	@Override
    public int updateByModel(Object param,Object model,Object mapper,Object example){
    	int a=0;
    	try {
    		if (param != null){
    			this.setExampleModel(param, example);
    		}
			Object[] paras=new Object[]{model,example};
			// 修改时，根据前端传的amender、amendTime、accessGroup,设置creator、createTime 和 accessGroup
			dataAuthorizeService.addDataAuthorizeInfo(model,"update");
			a = (int) ReflectionUtil.invokeMethod(mapper,"updateByExampleSelective", paras);
		} catch (Exception e) {
			LogUtil.error(ErrorLoggers.ERROR_LOGGER, "未知异常", e);;
			throw new BizException(Public.ERROR_CODE,"未知异常");
		}
    	return a;
    }

	/** 
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.seawin.service.common.CommonService#insertAndGetId(java.lang.Object, java.lang.Object)
	 */
	@Override
	public RequestResultVO insertAndGetId(Object model, Object mapper) {
		Object result;
		RequestResultVO rr = new RequestResultVO();

		Object[] params = new Object[] { model };

		try {
			
			//设置creator、createTime 和 accessGroup
//			this.setValueForInsert(model);
			
			// 插入时，根据前端传的amender、amendTime、accessGroup,设置creator、createTime 和 accessGroup
			dataAuthorizeService.addDataAuthorizeInfo(model,"insert");
			
			result = ReflectionUtil.invokeMethod(mapper, "insertAndGetId",
					params);

			Integer i = (Integer) result;

			if (i == 0) {
				rr.setCode(1);
				rr.setMessage("数据访问失败，插入异常");

			}

			rr.setCode(0);
			rr.setMessage("插入成功！");

			// rr.setObj(model);

		} catch (Exception e) {
			// TODO Auto-generated catch block
		    LogUtil.error(ErrorLoggers.ERROR_LOGGER, "数据访问失败，插入异常", e);

			rr.setCode(1);
			rr.setMessage("数据访问失败，插入异常");
		}

		return rr;
	}
	
	/*@Override
	public  Map<String, Object>  getByPageForModel(Object model, Integer length,
			Integer start, Object mapper, Object example,String orderBy) {

		try {
			this.setExampleModel(model, example);
		} catch (Exception e1) {
			LogUtil.error(ErrorLoggers.ERROR_LOGGER, "Public.ERROR_100", e1);
			throw new BizException(Public.ERROR_100);
		}
	
		Object result;

		Object[] params = new Object[] { example };

		Map<String, Object> map = null;

		try {
			result = ReflectionUtil.invokeMethod(mapper, "countByExample",
					params);

			int totalrecords = (Integer) result;
			if (totalrecords == 0) {
				return ResultBuilder.builFailResultForPage();
			}
			// 设置分页信息
			Page page = new Page();

			page.setBegin(start);//
			page.setLength(length);

			params = new Object[] { orderBy };

			ReflectionUtil.invokeMethod(example, "setOrderByClause", params);

			params = new Object[] { page };

			// example.setPage(page);
			ReflectionUtil.invokeMethod(example, "setPage", params);

			params = new Object[] { example };

			List<Object> objects = (List<Object>) ReflectionUtil.invokeMethod(
					mapper, "selectByExample", params);

			map = new HashMap<String, Object>();
			
			map.put("aaData",objects);

			map.put("recordsTotal", totalrecords);// total
			map.put("recordsFiltered", totalrecords);// total


		} catch (Exception e) {
			LogUtil.error(ErrorLoggers.ERROR_LOGGER, "Public.ERROR_100", e);
			throw new BizException(Public.ERROR_100);
		}
		return map;
	}*/
/*	@Override
	public  Map<String, Object>  getByPageForModel(Object model, Integer length,
			Integer start, Object mapper, Object example) {
		return this.getByPageForModel(model, length, start, mapper, example,PageConstants.CREATE_TIME_DESC);
	}*/
	
	@Override
	public int deleteByModel(Object param,Object mapper,Object example){
		int a=0;
    	try {
    		if (param != null){
    			this.setExampleModel(param, example);
    		}
			Object[] paras=new Object[]{example};
			a = (int) ReflectionUtil.invokeMethod(mapper,"deleteByExample", paras);
		} catch (Exception e) {
			LogUtil.error(ErrorLoggers.ERROR_LOGGER, "未知异常", e);
			throw new BizException(Public.ERROR_CODE,"未知异常");
		}
    	return a;
	}

}

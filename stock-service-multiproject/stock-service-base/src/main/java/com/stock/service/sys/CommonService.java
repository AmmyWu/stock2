package com.stock.service.sys;

import java.util.List;
import java.util.Map;

import com.stock.pojo.vo.RequestResultVO;



/**
 * ClassName	: BaseService
 * T			: 业务model
 * E			: 业务Mapper
 * Function		: TODO
 * date 		: 2017年5月15日上午9:54:24
 * @author chengxl
 * @version		: 
 * @since   JDK 1.7
 */

public interface CommonService<T,M,E> {
	
	
	void setExample(String keys,T model,M mapper,E example);
	
	 /**
	 * @param model
	 * @return
	 */
	RequestResultVO insert(T model,M mapper);

	 /**
	 * @param id
	 * @return
	 */
	RequestResultVO deleteById(Integer id,M mapper);
	 
	 
	 /**
	 * @param Ids
	 * @return
	 */
	RequestResultVO deleteByIds(String idFieldName,List<Integer> Ids,M mapper,E example);
	 

	 /**
	 * @param data
	 * @return
	 */
	RequestResultVO update(T model,M mapper);
	
	 /**
	 * @param key
	 * @return
	 */
	T getByKey(String key,M mapper);
	 
	 /**
	 * @param id
	 * @return
	 */
	T getById(Integer id,M mapper);

	 /**
	 * @param keys
	 * @param pageSize
	 * @param pageNow
	 * @return
	 */
	Map<String, Object> getByPage(String keys,Integer length,
			Integer start,T model,M mapper,E example);
	 
	 /**
	 * 按条件获取所有的数据
	 * @param keys
	 * @return
	 */
	List<T> getByKeys(String keys,T model,M mapper,E example);
	
	
	 /**
	 * @param keys
	 * @param pageSize
	 * @param pageNow
	 * @return
	 */
	Map<String, Object>  getByPageForBusiness(String keys,Integer length,
			Integer start ,T model,M mapper,E example);

	/**
	 * 根据businessModelVO中的creator,amender,accessgroup等字段值，设置businessModelVO中的baseModel的creatorName，amenderName，organiztionName的值
	 * 
	 * @author chengxl
	 * @param businessModel
	 *        businessModelVO
	 * @throws Exception 
	 * @since JDK 1.7 
	 */
	void addBaseModel(Object businessModel,Object businessModelVO) throws Exception;	
	/**
	 * 根据 model中的值，查询
	 * @author hkd
	 * @param model
	 * @param mapper
	 * @param example
	 * @return 
	 * @since JDK 1.7
	 */
	List<T> getByModel(T model,M mapper,E example);
	/**
	 * 根据model中的值设置查询条件
	 * @author hkd
	 * @param model
	 * @param example 
	 * @since JDK 1.7
	 */
	void setExampleModel(T model,E example)  throws Exception;
	/**
	 * 根据 exchange_operate 保存
	 * @author hkd
	 * @param modelVO
	 * @param mapper
	 * @param id
	 * @return
	 * @throws Exception 
	 * @since JDK 1.7
	 */
	public int saveByExchangeOperate(Object modelVO,M mapper,Integer id) throws Exception;

	/**
	 * updateByModel:(这里用一句话描述这个方法的作用). 
	 * TODO(这里描述这个方法适用条件 – 可选).
	 * 
	 * @author hkd
	 * @param param
	 * @param model
	 * @param mapper
	 * @param example
	 * @return 
	 * @since JDK 1.7 
	 */
	int updateByModel(T param, T model, M mapper, E example);
	/**
	 * 
	 * insertAndGetId:(新增并返回id). 
	 * TODO(这里描述这个方法适用条件 – 可选).
	 * 
	 * @author taofeng
	 * @param model
	 * @param mapper
	 * @return 
	 * @since JDK 1.7
	 */
	RequestResultVO insertAndGetId(T model,M mapper);

	/**
	 * isExistModel:新增，修改记录时，根据指定的多个字段名判断是否已存在相应记录 
	 * TODO(这里描述这个方法适用条件 – 可选).
	 * 
	 * @author chengxl
	 * @param model
	 * @param mapper
	 * @param example
	 * @param fieldNames，判断的字段名
	 * @param keyFiledName，主键字段，修改时使用
	 * @param keyId，主键id，修改时使用
	 * @param type，insert or update
	 * @return
	 * @throws Exception 
	 * @since JDK 1.7 
	 */
	boolean isExistModel(Object model, Object mapper, Object example,
			String[] fieldNames,String[] filedValues, String keyFiledName, Integer keyId, String type)
			throws Exception;

	/**
	 * @author hkd
	 * @param keys
	 * @param length
	 * @param start
	 * @param model
	 * @param mapper
	 * @param example
	 * @param dateFormate 日期类型
	 * @return 
	 * @since JDK 1.7 
	 */
	Map<String, Object> getByPageDateFormat(String keys, Integer length, Integer start, T model, M mapper,
			E example, String dateFormate);

	/**
	 * 
	 * 
	 * @author hkd
	 * @param model
	 * @param length
	 * @param start
	 * @param mapper
	 * @param example
	 * @param orderBy 排序
	 * @return 
	 * @since JDK 1.7 
	 */
//	Map<String, Object> getByPageForModel(T model, Integer length, Integer start, M mapper, E example,String orderBy);

	/**
	 * @author hkd
	 * @param model
	 * @param length
	 * @param start
	 * @param mapper
	 * @param example
	 * @return 
	 * @since JDK 1.7 
	 */
	//Map<String, Object> getByPageForModel(T model, Integer length, Integer start, M mapper, E example);

	/**
	 * @author hkd
	 * @param model
	 * @param mapper
	 * @param example
	 * @return 
	 * @since JDK 1.7 
	 */
	int countByModel(T model, M mapper, E example);

	/**
	 * @author hkd
	 * @param param
	 * @param mapper
	 * @param example
	 * @return 
	 * @since JDK 1.7 
	 */
	int deleteByModel(Object param, Object mapper, Object example);

	/**
	 * getByPageDateFormat:(这里用一句话描述这个方法的作用). 
	 * @author hkd
	 * @param keys
	 * @param length
	 * @param start
	 * @param model
	 * @param mapper
	 * @param example
	 * @param dateFormate
	 * @param orderBy
	 * @return 
	 * @since JDK 1.7 
	 */
	Map<String, Object> getByPageDateFormat(String keys, Integer length, Integer start, Object model, Object mapper,
			Object example, String dateFormate, String orderBy);
}

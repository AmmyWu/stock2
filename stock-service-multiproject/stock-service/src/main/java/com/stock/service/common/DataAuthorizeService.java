/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.common
 * 类/接口名: DataAuthorizeService
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月20日上午6:40:50
 * 作者		: chengxl
 * 修改历史	: 2017年5月20日上午6:40:50
 */
package com.stock.service.common;


/**
 * ClassName	: DataAuthorizeService
 * Function		: TODO
 * date 		: 2017年5月20日上午6:40:50
 * @author 		: chengxl
 * @version		: 1.0
 * @since   JDK 1.7
 */
public interface DataAuthorizeService {
	
	
	 int setMappExParaByAtuhorization(Object paraOjbect,String dataBelongFieldSetMethod);
	
	  /**
	 * setExampleByAuthorization:(这里用一句话描述这个方法的作用). 
	 * TODO(这里描述这个方法适用条件 – 可选).
	 * 
	 * @author chengxl
	 * @param example
	 * @param session 
	 * @since JDK 1.7 
	 */
	void setExampleByAuthorization(Object example) ;
	
	/**
	 * 插入修改是添加记录创建人，创建时间，访问组等信息
	 * 
	 * @author taofeng
	 * @param model
	 * @param type 
	 * @since JDK 1.7
	 */
	void addDataAuthorizeInfo(Object model,String type);



}

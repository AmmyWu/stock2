package com.stock.common.util;

import java.util.List;
import java.util.Map;

import com.stock.common.exceptions.BizException;



 
/**
 * 
 * ClassName	: CriteriaUtils
 * Function		: TODO
 * date 		: 2017年5月7日下午6:09:45
 * @author dean
 * @version		: 
 * @since   JDK 1.7
 */

//set mybaits criteria
public class CriteriaUtils {

	public static void setCriteria(Object o, List<Map<?,?>> list) {

		String criteriaMethodName ="";
		
		for (Map map : list) {
			
			
			
			//parameters type,ex: class java.lang.Integer
			String type = (String)map.get("type");
			Object value = map.get("value");
			
		
			
			if("class java.lang.String".equals(type)){// String 类型采用模糊匹配
				criteriaMethodName =  getCriteriaMethodName((String)map.get("name"),"LIKE");
				value = "%"+value.toString() +"%";
			}
			else
				criteriaMethodName =  getCriteriaMethodName((String)map.get("name"),"EQUAL");

			
			ReflectionUtil.setObjectValues(o, criteriaMethodName, type,value);
		}

	}
	
	public static void setCriteriaByOperator(Object o, List<Map<?,?>> list) {

		String criteriaMethodName ="";
		
		for (Map map : list) {
			
			
			
			//parameters type,ex: class java.lang.Integer
			String type = (String)map.get("type");//数据类型
			Object value = map.get("value");//具体指
			
			String operator = (String)map.get("operator");//大于，等于
			
		
			
			if("class java.lang.String".equals(type)){// String 类型采用模糊匹配
				criteriaMethodName =  getCriteriaMethodName((String)map.get("name"),operator);//"LIKE"
				if("LIKE".equals(operator) || "NOTLIKE".equals(operator)){
					value = "%"+value.toString() +"%";
				}else{
					value=value.toString();
				}
			}
			else
				criteriaMethodName =  getCriteriaMethodName((String)map.get("name"),"EQUAL");

			
			ReflectionUtil.setObjectValues(o, criteriaMethodName, type,value);
		}

	}

	private static String getCriteriaMethodName(String modelFiedName, String operator) {

		if("EQUAL".equals(operator))
			return "and"+CommonUtils.upperHeadChar(modelFiedName)+"EqualTo";
		
		if("LIKE".equals(operator))
			return "and"+CommonUtils.upperHeadChar(modelFiedName)+"Like";
		
		if("NOTEQUAL".equals(operator))
			return "and"+CommonUtils.upperHeadChar(modelFiedName)+"NotEqualTo";
		
		if("NOTLIKE".equals(operator))
			return "and"+CommonUtils.upperHeadChar(modelFiedName)+"NotLike";
		

		return "";
	}
	
	
	/**
	 * //判断是否有 criteria 并且获取
	 * @author hkd
	 * @param example
	 * @return
	 * @throws Exception 
	 * @since JDK 1.7
	 */
	public static Object judgeAndGetCriteria(Object example) throws Exception {
		//判断是否有 criteria
		Object oredCriteria= ReflectionUtil.invokeMethod(example, "getOredCriteria");
		Object createCriteria = null;
		if (oredCriteria instanceof List) {
			List<Object> oredCriteriaList = (List<Object>) oredCriteria;
			int size = oredCriteriaList.size();
			if (size == 0) {
				createCriteria = ReflectionUtil.invokeMethod(example, "createCriteria");
			} else if (size == 1){
				createCriteria = oredCriteriaList.get(0);
			} else {
				throw new BizException("createCriteria 异常");
			}
		} else {
			createCriteria = ReflectionUtil.invokeMethod(example, "createCriteria");
		}
		return createCriteria;
	}
}

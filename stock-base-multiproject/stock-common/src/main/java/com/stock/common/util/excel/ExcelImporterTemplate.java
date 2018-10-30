/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.seawin.common.util.excel
 * 类/接口名: ExcelImporterTemplate
 * 版本信息	: 1.00
 * 新建日期	: 2017年9月28日下午1:45:37
 * 作者		: taofeng
 * 修改历史	: 2017年9月28日下午1:45:37
 */
package com.stock.common.util.excel;

import java.util.HashMap;
import java.util.Map;


/**
 * ClassName	: ExcelImporterTemplate
 * Function		: excel导入模板
 * date 		: 2017年10月23日下午1:45:37
 * @author 		: dean
 * @version		: 1.0
 * @since   JDK 1.7
 */
public class ExcelImporterTemplate {

	/**地区excel模板**/
	public static Map<String, String[]> administrationMethodMap = new HashMap<String,String[]>();
	
	static {
		/**地区excel模板**/
		administrationMethodMap.put("节点id", new String[]{"setGocodeString","class java.lang.String"});
		administrationMethodMap.put("父节点id", new String[]{"setIdSuperString","class java.lang.String"});
		administrationMethodMap.put("名字", new String[]{"setName","class java.lang.String"});
	
	}
	
	/**地区excel模板**/
	public static Map<String, String[]> employeeMethodMap = new HashMap<String,String[]>();
	
	static {
		employeeMethodMap.put("组织结构id", new String[]{"setOrganizationStructureIdString","class java.lang.String"});
		employeeMethodMap.put("员工代码", new String[]{"setEmployeeCode","class java.lang.String"});
		employeeMethodMap.put("姓名", new String[]{"setEmployeeName","class java.lang.String"});
		employeeMethodMap.put("手机号码", new String[]{"setEmployeeCellPhone","class java.lang.String"});
	}
	
}

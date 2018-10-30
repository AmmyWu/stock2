/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.utils
 * 类/接口名	: SysConst
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午6:08:06
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午6:08:06
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.sys.utils;

import java.math.BigDecimal;
import java.util.Map;



//servcie层常量类,被web继承，在web层启动时做初始化
public abstract class SysConst {
	

	public static String SMS_SENDURL = "http://www.tosms.cn/Api/SendSms.ashx" ;

	public static String SMS_USERNAME = "zy87302728";

	public static String SMS_PASSWORD = "9389796395346BE90FD6D4E57DC4D479";
	
	public static String ADMIN_URL = "cnsce-index.html";
	
	public static String PORTALSYSTEM_URL = "";
	
	/**
	 * 系统员工id，name map
	 */
	public static Map<Integer,String> EMPLOYEE_MAP = null;
	

	/**
	 * 系统员工id，name map
	 */
	public static Map<Integer,String> USER_MAP = null;
	
	/**
	 * 系统参数设置
	 */
	public static Map<Integer,String> SYSTEMSETTING = null;
	
	/**
	 * 系统资源tree,不包含业务级资源
	 */
	public static SysResourceTree RESOURCESTREE_UNINCLUDE_BUSINESS = null;
	/**
	 * 系统资源tree
	 */
	public static SysResourceTree RESOURCESTREE = null;
	
	/**
	 * 系统组织架构 tree
	 */
	public static SysOrganizationStructureTree ORGANIZATIONSTRUCTURE_TREE = null;
	
	/**
	 * 基础数据集tree
	 */
//	public static BaseDataCommonSetTree BASICDATASET_TREE = null;
	
	/**
	 * 基础数据集tree
	 */
	public static SysBasicDataSetTree BASICDATASET_TREE = null;
	
	/**
	 * 角色、资源、组织架构映射常量
	 */
	public static RoleResourceOrganization ROLE_RESOURCE = null;

	public static Map<Integer, String> PARAMETER_MAP = null;
	
	public static Map<Integer, String> PRODUCER_MAP = null;

	public static Map<Integer, String> EQUIPMENT_MAP = null;
	
	public static Map<Integer, String> MEETING_MAP = null;
	
	public static Map<Integer, String> MEETING_EMPLOYEE_MAP = null;

	public static Map<BigDecimal, String> INSPECTIONRECORD_MAP = null;

	public static Map<Integer, String> CUSTOMER_MAP = null;
	
	
	public static class  ExcelConst{
		 
		 
		
		public static final   String[] HEAD_SYSCUSTOMER = {"姓名","性别","身份证","职务","所在单位","感兴趣产品","参观目的",
			"感兴趣产品","参观目的","手机","电话","QQ","EMail","传真","类型"};
			
		public static final   String[] FIELDNAMES_SYSCUSTOMER = {"customerName","sex","identifier","duties","companyName","interestedProducts","target",
			"interestedProducts","target","cellPhone","officePhone","qq","email","fax","type"};
			
			
		public static final   String[] HEAD_SYSCOMPANY = {"单位名称","简称","单位性质","所属行业","地址","联系人","职务",
				"电话","手机","传真","邮箱"};
			
		public static final   String[] FIELDNAMES_SYSCOMPANY = {"companyName","abbreviation","companyType","industry","address","contact","position",
				"phone","cellPhone","fax","email"};
			
			
		public static final   String[] HEAD_SYSEMPLOYEE = {"姓名",
			"手机","电话","邮件","传真","备注"};
			
		public static final   String[] FIELDNAMES_SYSEMPLOYEE = {"employeeName",
			"employeeCellPhone","employeeOfficePhone","email","fax","comment"};
		
		public static final String[] HEAD_INSPECTIONRECORD={"记录编号","设备名称","巡检员","记录时间","状态","经度"
				,"纬度","进水温度","出水温度","压力","记录状态"};
		
		public static final String[] FILEDNAMES_INSPECTIONRECORD={"recordId","equipmentName","inspectorName","recordTime","status"
				,"recordLongtitude","recordLatitude","temperatureIn","temperatureOut","pressure","recordStatus"};
				
		public static final   String[] HEAD_MEETING = {"活动ID","活动名称","活动开始时间",
				"活动结束时间","主讲人","活动地点","二维码","活动内容"};
		
		public static final   String[] FIELDNAMES_MEETING = {"meetingId","meetingName",
				"meetingStarttime","meetingEndtime","presentationName","meetingLocation","qrCode","remark"};
		
		public static final   String[] HEAD_MEETING_EMPLOYEE = {"活动Id","活动名称","参与者",
				"签到标志","签退标志","签到时间","签退时间"};
		
		public static final   String[] FIELDNAMES_MEETING_EMPLOYEE = {"meetingEid","meetingName","employeeName",
				"attendstartMark","attendendMark","attendstartTime","attendendTime"};
  
		public static final String[] HEAD_MAINTENANCERECORD={"记录编号","设备名称","设备型号","设备序列号","巡检员","经度"
			,"纬度","清洗状态","消毒状态","维护状态","备注","记录时间"};
		
		public static final String[] FILEDNAMES_MAINTENANCERECORD={"emrId","equipmentName","model","seriesNumber","employeeName",
			"recordLongtitude","recordLatitude","clearStatus","disinfectStatus","maintenanceStatus","remark","addTime"};
		
	
	}


	

}

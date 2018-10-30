package com.stock.common.constants;

import com.stock.common.util.PropertyReader;


 /**
 * 返回系统常量(本系统不使用此类)
 * @author <a href="mailto:zhangsy@zjport.gov.cn">zhangsy</a>
 * @version $Id$   
 * @since 2.0
 */

public class Sysconstants {


	//微信企业号corp_id
	public static String getCORP_ID() {
		return PropertyReader.read("com.slianclass.resources.sysconstants", "CORP_ID");
	}
	//sliansoft-wechat地址
	public static String getURL_SLIANSOFT_WECHAT() {
		
		return PropertyReader.read("com.slianclass.resources.sysconstants", "URL_SLIANSOFT_WECHAT");
	}
	public static String getURL_PRE() {
		return PropertyReader.read("com.slianclass.resources.sysconstants", "URL_PRE");
	}
	/**企业号应用客户管理的agentid**/
	public static String getCUSTOMER_AGENTID() {
		return PropertyReader.read("com.slianclass.resources.sysconstants", "CUSTOMER_AGENTID");
	}
	/**企业号应用拜访记录的agentid**/
	public static String getACTIVITY_AGENTID() {
		return PropertyReader.read("com.slianclass.resources.sysconstants", "ACTIVITY_AGENTID");
	}
	/**邦达接口地址**/
	public static String getBANGDA_URL() {
		return PropertyReader.read("com.slianclass.resources.sysconstants", "BANGDA_URL");
	}
	
	public static String getSALESYS_MOBILE_WEB() {
		return PropertyReader.read("com.slianclass.resources.sysconstants", "SALESYS_MOBILE_WEB");
	}
}

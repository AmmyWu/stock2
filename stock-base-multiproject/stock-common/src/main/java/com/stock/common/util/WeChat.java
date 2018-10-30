/**
 * 通过该类调用sliansoft-wechat 实现与微信企业号的通信
 * 
 */

package com.stock.common.util;

import org.apache.commons.lang.StringUtils;

import com.stock.common.constants.Sysconstants;

import net.sf.json.JSONObject;


public class WeChat {
	static String CORP_ID = Sysconstants.getCORP_ID();// 企业号corpid  "wx2c6c0614f9474f25"
	static String URL_PRE =  Sysconstants.getURL_SLIANSOFT_WECHAT();//"http://localhost:8080/sliansoft-wechat";
	
	static String URL_SENT_TEXT_MSG = URL_PRE + "//message//sendTextMsg.do";
	static String URL_GET_USER = URL_PRE +"/core/getUserByuserId.do";	
	
	
 
	public static void main(String[] args) {
		// TODO Auto-generated method stub lwy1581410450|
		sendTextMsg2("0006262","宁波顺圆物流有限公司", "1000003", "aa文本消息推送测试.<a href='aa.html?id=2%26name=3'>aabb</a>");
	}

	/**
	 * 发送文本消息接口
	 * corpId=wxa75fcc28f7f6401a  敏智微服
	 * corpId=wx5cd0347fc9d6f2e4  英联集团
	 * @param touser  微信企业号里面的账号 ，可以是多个  多个用 “|”分割
	 * @param toparty 部门
	 * @param agentid  应用id
	 * @param content
	 */

	public static JSONObject sendTextMsg(String touser,String toparty, String agentid, String content) {

		

//		content = "corpId=wx5cd0347fc9d6f2e4&touser=chengxl&toparty=开发部宁波数联&agentid=3&content=文本消息推送测试";

		
		if(StringUtils.isEmpty(touser) ||
				StringUtils.isEmpty(agentid) || StringUtils.isEmpty(content))
			return null;
		
		content = "corpId="+CORP_ID+"&touser="+touser+"&toparty="+toparty + "&agentid=" +agentid +"&content=" +content;

		return JSONObject.fromObject(HttpConnectUtil.sendPost( URL_SENT_TEXT_MSG  , content));


	}
public static Object sendTextMsg2(String touser,String toparty, String agentid, String content) {

		

//		content = "corpId=wx5cd0347fc9d6f2e4&touser=chengxl&toparty=开发部宁波数联&agentid=3&content=文本消息推送测试";

		
		if(StringUtils.isEmpty(touser) ||
				StringUtils.isEmpty(agentid) || StringUtils.isEmpty(content))
			return null;
		
		content = "corpId="+CORP_ID+"&touser="+touser+"&toparty="+toparty + "&agentid=" +agentid +"&content=" +content;

		return HttpRequest.sendPost( URL_SENT_TEXT_MSG  , content);


	}
	
	/**
	 * 根据微信企业号中的账号获取员工数据
	 * @param userid
	 * @return
	 */
	public static JSONObject getUser(String userid){
		
		if(StringUtils.isEmpty(userid))
			return null;
		
		String content = "corpId="+CORP_ID +"&userId="+userid;
		
		return JSONObject.fromObject(HttpConnectUtil.sendPost(URL_GET_USER  , content));
		
	}

}

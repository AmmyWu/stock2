/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.base.impl
 * 类/接口名	: SmsServiceImpl
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:32:17
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:32:17
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.common.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.stock.common.context.Constants;
import com.stock.service.common.SmsService;
import com.stock.service.common.util.HttpRequest;
/**
 * 
 * ClassName	: SmsServiceImpl
 * Function		: TODO
 * date 		: 2017年5月7日下午5:38:17
 * @author chengxl
 * @version		: 
 * @since   JDK 1.7
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService {

	
	@Override
	public JSONObject sendSMS(String mobiles,String content){
		
		JSONObject jo = new JSONObject();	
		
		
		try {
			content = transferChinese(content+"【北仑区域卫生信息分析决策平台】");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			jo.put("result","error");
			jo.put("msg", "编码转换异常");
			return jo;
		}
		
		
		
		String[] mobileArray = mobiles.split(",");
		
		String returnvalue = "";
		for(int i = 0 ;i< mobileArray.length; i++){
			
			returnvalue = HttpRequest.sendGet(Constants.SMS_SENDURL, this.createSMS(mobileArray[i], content));
			jo.put("result", this.setSendStatus(returnvalue));
		}
		
		return jo;
	}
	
	private String createSMS(String phone,String content){
		
		String sms = "username="+Constants.SMS_USERNAME+"&"+"Password="+Constants.SMS_PASSWORD+"&"+
				"Phones="+phone+"&"+"Content="+content;
		sms=sms.replaceAll("%0D%0A", "");//去掉回车
		sms=sms.replaceAll("%09", "");//去掉tab-%9
		return sms;
	}

	
	private String setSendStatus(String returnvalue){
		switch(Integer.parseInt(returnvalue)){
        case   0: return "其他系统错误";
		case  -1: return "短信余额不足";
		case  -2: return "资金账户被锁定";
		case  -3: return "用户被锁定";
		case  -4: return "号码在黑名单内";
		case  -5: return "用户名或者密码不正确，请联系管理员";
		case  -6: return "号码不正确";
		case  -7: return "接口连接失败";
		case  -8: return "电话号码格式不正确";
		case  -9: return "通道编号错误";
		case  -12: return "短信内容超出长度限制";
		case  -18: return "短信内容没有签名，请联系管理员设置签名";
		case  -15: return "内容含非法关键字";
		case  -30: return "账户未认证，请联系管理员";
		case   1 : return "短信发送成功";
		default : return "短信发送失败";
		}	
	}
	
	public String transferChinese(String strText) throws UnsupportedEncodingException {
//		 new String(strText.getBytes("ISO-8859-1"), "UTF-8")
		
		return  URLEncoder.encode(strText, "utf8").toString();
	}



	
}

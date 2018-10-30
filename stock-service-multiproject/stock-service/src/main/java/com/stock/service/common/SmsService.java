/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.base
 * 类/接口名	: SmsService
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:39:29
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:39:29
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.common;

import net.sf.json.JSONObject;

public interface SmsService {
	
	public JSONObject sendSMS(String mobiles,String content);
	
//	public JSONObject sendMessage(MessageSendBean message);
//	public JSONObject sendMessageCommon(MessageSendBean message);

}

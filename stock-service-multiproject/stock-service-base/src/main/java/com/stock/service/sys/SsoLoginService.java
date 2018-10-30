package com.stock.service.sys;

public interface SsoLoginService {
	/**
	 * 统一身份登录，返回账号
	 * @param code
	 * @param userType
	 * @return
	Object zjuLogin(String code, String userType);*/
	/**
	 * 设置session
	 * @param account
	 * @return
	 */
	Object setLoginSession(String account);
	/**
	 * 获取登录信息
	 * @return
	 */
	Object getLoginMessage();
}

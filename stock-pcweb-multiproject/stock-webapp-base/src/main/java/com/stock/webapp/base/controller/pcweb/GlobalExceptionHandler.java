package com.stock.webapp.base.controller.pcweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {
	public static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Map<String, Object> defaultErrorHandler(Exception e, HttpServletRequest request) {
		Map<String, Object> map= new HashMap<>();
		e.printStackTrace();
		map.put("success", Boolean.FALSE);
		map.put("message", "服务器报错");
		this.writeLog(request, e);
		
		return map;
	}

	private void writeLog(HttpServletRequest request, Throwable e) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> enumeration = request.getHeaderNames();
		while (enumeration.hasMoreElements()) {
			String str = enumeration.nextElement().toString();
			map.put(str, request.getHeader(str));
		}
		map.put("getRequestURI", request.getRequestURI());
		logger.error("请求异常,请求头：{};", map.toString(), e);

	}
}
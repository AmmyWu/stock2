package com.stock.webapp.base.log;

import java.util.Arrays;
import java.util.Date;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.stock.service.sys.SysEmployeeService;

@Aspect
public class AspectForLog {

	private static Logger logger = Logger.getLogger(AspectForLog.class);
	OperateLevel OPERATE_LEVEL = new OperateLevel();
	ServerLevel SERVER_LEVEL = new ServerLevel();
	
	@Autowired
	private SysEmployeeService employeeService;
	

	private void log_O(String message) {
		logger.log(OPERATE_LEVEL, message);
	}

	private void log_S(String message) {
		logger.log(SERVER_LEVEL, message);
	}

	@Around("execution(* com.stock.service.*.impl.*ServiceImpl.delet*(..))"
			+"||execution(* com.stock.service.*.impl.*ServiceImpl.insert*(..))"
			+"||execution(* com.stock.service.*.impl.*ServiceImpl.save*(..))"
			+"||execution(* com.stock.service.*.impl.*ServiceImpl.update*(..))"
			+"||execution(* com.stock.service.*.impl.*ServiceImpl.refresh*(..))")
	public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
		String op = joinPoint.getTarget() + "."
				+ joinPoint.getSignature().getName()
				+ Arrays.toString(joinPoint.getArgs());
		String targetFunction = joinPoint.getSignature().getName();
		String targetClass = joinPoint.getTarget().getClass().getName();

		String type = "stock_SYS";

		Object[] args = joinPoint.getArgs();
		
		

		if (MDC.get("userId") == null)
			MDC.put("userId", -1);
		
		System.out.println(MDC.get("UserId"));
		if (MDC.get("IP") == null)
			MDC.put("IP", "-.-.-.-");

		MDC.put("function", targetFunction.replace("'", "$S$"));
		MDC.put("class", targetClass.replace("'", "$S$"));
		//parameters.replace("'", "$S$")
		
		String parameters = "";
		String message = "";
		
		
		//依次获取参考类型，及参数的值
		for(int i = 0; i < args.length ; i ++){
			
			if( args[i] == null)
				continue;
			
			parameters += args[i].getClass().toString()+"; ";
			
			String primitiveValue = this.getPrimitiveValue(args[i]);
			
			//非基本数据类型
			if("UNPrimitive".equals(primitiveValue))
				message +=   	JSONArray.fromObject(args[i]).toString();// JSONObject.fromObject(args[i]).toString()+"; ";
			else
				message += primitiveValue;
			
		}
		MDC.put("parameters",parameters);//JSONArray.fromObject(args).toString()
		MDC.put("message", message);
		//
		// System.out.println(this.convertToTable(targetClass));
		MDC.put("type", type);
		try {

			Object result = joinPoint.proceed(args);

			String resultS = "r";
			if (result != null) {
				resultS = result.toString();
				if (resultS.length() > 100) {
					resultS = resultS.substring(0, 100);
				}
			}
			MDC.put("result", resultS);
			log_O(message);//"message_tt"
			
			return result;
		} catch (IllegalArgumentException e) {
			log_S(op + "error:" + e);
			throw e;
		}
	}
	/**
	 * 返回基本数据类型的值
	 * @param param
	 * @return
	 */
	private String getPrimitiveValue(Object param) {

		if (param instanceof String)
			return (String) param;

		else if (param instanceof Integer) {
			return ((Integer) param).toString();

		} else if (param instanceof Double) {
			return Double.toString(((Double) param).doubleValue());
		} else if (param instanceof Float) {
			return ((Float) param).toString();
		} else if (param instanceof Long) {
			((Long) param).toString();

		} else if (param instanceof Boolean) {
			return ((Boolean) param).toString();

		} else if (param instanceof Date) {
			return ((Date) param).toString();

		}
		return "UNPrimitive";

	}
}

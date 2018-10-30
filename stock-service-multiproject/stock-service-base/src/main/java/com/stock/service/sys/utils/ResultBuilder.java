package com.stock.service.sys.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.stock.common.exceptions.BizException;
import com.stock.pojo.vo.RequestResultVO;
import com.stock.service.sys.utils.HttpResponseConstants.Public;



/**
 * 标准的结果生成器
 * 
 */
public class ResultBuilder {

    /**
     * 生成成功的结果
     * 
     * @param obj 数据体
     * @return
     */
    public static RequestResultVO buildSuccessResult(String message, Object obj) {
        RequestResultVO response = new RequestResultVO();
        response.setCode(HttpResponseConstants.Public.SUCCESS_CODE);
        response.setMessage(message);
        response.setData(obj);
        return response;

    }

    /**
     * 生成成功的结果
     * 
     * @param obj 数据体
     * @return
     */
    public static RequestResultVO buildSuccessResult(Object obj) {
        RequestResultVO response = new RequestResultVO();
        response.setCode(HttpResponseConstants.Public.SUCCESS_CODE);
        response.setData(obj);
        response.setMessage(Public.SUCCESS);
        return response;
    }
    
    public static RequestResultVO buildSuccessResult() {
    	return buildSuccessResult(null);
    }

    /**
     * 生成失败的结果
     * 
     * @param errCode
     * @param errMsg
     * @return
     */
    public static RequestResultVO buildFailResult(String errMsg) {
        RequestResultVO response = new RequestResultVO();
        response.setCode(HttpResponseConstants.Public.ERROR_CODE);
        response.setMessage(errMsg);
        return response;
    }
    /**
     * 生成失败的结果
     * @param errorCode
     * @param errMsg
     * @return
     */
    public static RequestResultVO buildFailResult(Integer errorCode,String errMsg) {
        RequestResultVO response = new RequestResultVO();
        response.setCode(errorCode);
        response.setMessage(errMsg);
        return response;
    }
    /**
     * 生成失败的结果
     * 
     */
    public static RequestResultVO buildFailResult(BizException be) {
        return buildFailResult(be.getMsg());
    }
    
    public static RequestResultVO buildFailResultMsg(BizException biz) {
        RequestResultVO response = new RequestResultVO();
        response.setCode(HttpResponseConstants.Public.ERROR_CODE);
        response.setMessage(biz.getMsg());
        return response;
    }
    
    /**
     * 未知异常
     * @return
     */
    public static RequestResultVO buildFailResultUnknown() {
    	RequestResultVO response = new RequestResultVO();
    	response.setCode(HttpResponseConstants.Public.ERROR_CODE);
    	response.setMessage("未知异常");
    	return response;
    }
    
    /**
     * 返回分页查询失败结果
     * @return
     */
    public static Map<String,Object> builFailResultForPage(){
    	Map<String, Object> map = new HashMap<>();
		map.put("recordsTotal", 0);// total
		map.put("recordsFiltered", 0);// total
		map.put("aaData",new ArrayList<Object>());
		return map;
    }
}

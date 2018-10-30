package com.stock.common.tool.http;

import com.stock.common.tool.exception.BizException;

/**
 * 标准的结果生成器
 * 
 */
public class ResultBuilder {

    /**
     * 生成成功的结果
     * 
     * @param obj 数据体
     * @param paginator 分页信息
     * @return
     */
    public static RequestResultVO buildSuccessResult(String message, Object obj) {
        RequestResultVO response = new RequestResultVO();
        response.setCode(0);
        response.setMessage(message);
        response.setData(obj);
        return response;

    }

    /**
     * 生成失败的结果
     * 
     * @param errCode
     * @param errMsg
     * @return
     */
    public static RequestResultVO buildFailResult(int errCode, String errMsg) {
        RequestResultVO response = new RequestResultVO();
        response.setCode(errCode);
        response.setMessage(errMsg);
        return response;
    }

    /**
     * 生成失败的结果
     * 
     * @param errCode
     * @param errMsg
     * @return
     */
    public static RequestResultVO buildFailResult(BizException be) {
        return buildFailResult(be.getCode(), be.getMessage());
    }
}

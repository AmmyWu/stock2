package com.stock.webapp.base.controller.stock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.stock.BuyerHistoryEntrustRecord;
import com.stock.service.stock.BuyerHistoryEntrustRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.stock.common.exceptions.BizException;
import com.stock.common.util.CommonUtils;
import com.stock.common.util.JsonFastUtil;
import com.stock.pojo.vo.RequestResultVO;
import com.stock.service.sys.utils.HttpResponseConstants.Public;

@Controller
@RequestMapping(value="/buyerHistoryEntrustRecord")
public class BuyerHistoryEntrustRecordController {

	@Autowired
	private BuyerHistoryEntrustRecordService buyerHistoryEntrustRecordService;
	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/insert.do")
	public @ResponseBody RequestResultVO insert(HttpServletRequest request){
		String buyerHistoryEntrustRecordString = request.getParameter("buyerHistoryEntrustRecord");
BuyerHistoryEntrustRecord buyerHistoryEntrustRecord = null;
		try{
buyerHistoryEntrustRecord = JsonFastUtil.parseObject(buyerHistoryEntrustRecordString, BuyerHistoryEntrustRecord.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return buyerHistoryEntrustRecordService.insert(buyerHistoryEntrustRecord);
	}
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update.do")
	public @ResponseBody RequestResultVO update(HttpServletRequest request){
		String buyerHistoryEntrustRecordString = request.getParameter("buyerHistoryEntrustRecord");
BuyerHistoryEntrustRecord buyerHistoryEntrustRecord = null;
		try{
buyerHistoryEntrustRecord = JsonFastUtil.parseObject(buyerHistoryEntrustRecordString, BuyerHistoryEntrustRecord.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return buyerHistoryEntrustRecordService.update(buyerHistoryEntrustRecord);
	}
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete.do")
	public @ResponseBody RequestResultVO delete(HttpServletRequest request){
		String buyerHistoryEntrustRecordIdsString = request.getParameter("buyerHistoryEntrustRecordIds");
		List<Integer> buyerHistoryEntrustRecordIds;
    try{
buyerHistoryEntrustRecordIds = CommonUtils.idsArrayToList(buyerHistoryEntrustRecordIdsString);
    }catch(Exception e){
    throw new BizException(Public.ERROR_700);
    }
    return buyerHistoryEntrustRecordService.delete(buyerHistoryEntrustRecordIds);
    }
    /**
    * 分页查询
    * @param request
    * @return
    */
    @RequestMapping(value="/getByPage.do")
    public @ResponseBody Object getByPage(HttpServletRequest request){
    String keys = request.getParameter("keys");
    Integer length = Integer.parseInt(request.getParameter("length"));
    Integer start = Integer.parseInt(request.getParameter("start"));
    return buyerHistoryEntrustRecordService.getByPage(keys, length, start);
    }
    }

package com.stock.webapp.base.controller.stock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.stock.BuyerSellerHistoryEntrustRecord;
import com.stock.service.stock.BuyerSellerHistoryEntrustRecordService;
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
@RequestMapping(value="/buyerSellerHistoryEntrustRecord")
public class BuyerSellerHistoryEntrustRecordController {

	@Autowired
	private BuyerSellerHistoryEntrustRecordService buyerSellerHistoryEntrustRecordService;
	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/insert.do")
	public @ResponseBody RequestResultVO insert(HttpServletRequest request){
		String buyerSellerHistoryEntrustRecordString = request.getParameter("buyerSellerHistoryEntrustRecord");
BuyerSellerHistoryEntrustRecord buyerSellerHistoryEntrustRecord = null;
		try{
buyerSellerHistoryEntrustRecord = JsonFastUtil.parseObject(buyerSellerHistoryEntrustRecordString, BuyerSellerHistoryEntrustRecord.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return buyerSellerHistoryEntrustRecordService.insert(buyerSellerHistoryEntrustRecord);
	}
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update.do")
	public @ResponseBody RequestResultVO update(HttpServletRequest request){
		String buyerSellerHistoryEntrustRecordString = request.getParameter("buyerSellerHistoryEntrustRecord");
BuyerSellerHistoryEntrustRecord buyerSellerHistoryEntrustRecord = null;
		try{
buyerSellerHistoryEntrustRecord = JsonFastUtil.parseObject(buyerSellerHistoryEntrustRecordString, BuyerSellerHistoryEntrustRecord.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return buyerSellerHistoryEntrustRecordService.update(buyerSellerHistoryEntrustRecord);
	}
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete.do")
	public @ResponseBody RequestResultVO delete(HttpServletRequest request){
		String buyerSellerHistoryEntrustRecordIdsString = request.getParameter("buyerSellerHistoryEntrustRecordIds");
		List<Integer> buyerSellerHistoryEntrustRecordIds;
    try{
buyerSellerHistoryEntrustRecordIds = CommonUtils.idsArrayToList(buyerSellerHistoryEntrustRecordIdsString);
    }catch(Exception e){
    throw new BizException(Public.ERROR_700);
    }
    return buyerSellerHistoryEntrustRecordService.delete(buyerSellerHistoryEntrustRecordIds);
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
    return buyerSellerHistoryEntrustRecordService.getByPage(keys, length, start);
    }
    }

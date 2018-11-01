package com.stock.webapp.base.controller.stock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.stock.BuyerEntrustPriceQueue;
import com.stock.service.stock.BuyerEntrustPriceQueueService;
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
@RequestMapping(value="/buyerEntrustPriceQueue")
public class BuyerEntrustPriceQueueController {

	@Autowired
	private BuyerEntrustPriceQueueService buyerEntrustPriceQueueService;
	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/insert.do")
	public @ResponseBody RequestResultVO insert(HttpServletRequest request){
		String buyerEntrustPriceQueueString = request.getParameter("buyerEntrustPriceQueue");
BuyerEntrustPriceQueue buyerEntrustPriceQueue = null;
		try{
buyerEntrustPriceQueue = JsonFastUtil.parseObject(buyerEntrustPriceQueueString, BuyerEntrustPriceQueue.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return buyerEntrustPriceQueueService.insert(buyerEntrustPriceQueue);
	}
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update.do")
	public @ResponseBody RequestResultVO update(HttpServletRequest request){
		String buyerEntrustPriceQueueString = request.getParameter("buyerEntrustPriceQueue");
BuyerEntrustPriceQueue buyerEntrustPriceQueue = null;
		try{
buyerEntrustPriceQueue = JsonFastUtil.parseObject(buyerEntrustPriceQueueString, BuyerEntrustPriceQueue.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return buyerEntrustPriceQueueService.update(buyerEntrustPriceQueue);
	}
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete.do")
	public @ResponseBody RequestResultVO delete(HttpServletRequest request){
		String buyerEntrustPriceQueueIdsString = request.getParameter("buyerEntrustPriceQueueIds");
		List<Integer> buyerEntrustPriceQueueIds;
    try{
buyerEntrustPriceQueueIds = CommonUtils.idsArrayToList(buyerEntrustPriceQueueIdsString);
    }catch(Exception e){
    throw new BizException(Public.ERROR_700);
    }
    return buyerEntrustPriceQueueService.delete(buyerEntrustPriceQueueIds);
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
    return buyerEntrustPriceQueueService.getByPage(keys, length, start);
    }
    }

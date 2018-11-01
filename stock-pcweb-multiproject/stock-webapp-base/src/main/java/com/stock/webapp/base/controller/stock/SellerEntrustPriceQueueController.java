package com.stock.webapp.base.controller.stock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.stock.SellerEntrustPriceQueue;
import com.stock.service.stock.SellerEntrustPriceQueueService;
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
@RequestMapping(value="/sellerEntrustPriceQueue")
public class SellerEntrustPriceQueueController {

	@Autowired
	private SellerEntrustPriceQueueService sellerEntrustPriceQueueService;
	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/insert.do")
	public @ResponseBody RequestResultVO insert(HttpServletRequest request){
		String sellerEntrustPriceQueueString = request.getParameter("sellerEntrustPriceQueue");
SellerEntrustPriceQueue sellerEntrustPriceQueue = null;
		try{
sellerEntrustPriceQueue = JsonFastUtil.parseObject(sellerEntrustPriceQueueString, SellerEntrustPriceQueue.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return sellerEntrustPriceQueueService.insert(sellerEntrustPriceQueue);
	}
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update.do")
	public @ResponseBody RequestResultVO update(HttpServletRequest request){
		String sellerEntrustPriceQueueString = request.getParameter("sellerEntrustPriceQueue");
SellerEntrustPriceQueue sellerEntrustPriceQueue = null;
		try{
sellerEntrustPriceQueue = JsonFastUtil.parseObject(sellerEntrustPriceQueueString, SellerEntrustPriceQueue.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return sellerEntrustPriceQueueService.update(sellerEntrustPriceQueue);
	}
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete.do")
	public @ResponseBody RequestResultVO delete(HttpServletRequest request){
		String sellerEntrustPriceQueueIdsString = request.getParameter("sellerEntrustPriceQueueIds");
		List<Integer> sellerEntrustPriceQueueIds;
    try{
sellerEntrustPriceQueueIds = CommonUtils.idsArrayToList(sellerEntrustPriceQueueIdsString);
    }catch(Exception e){
    throw new BizException(Public.ERROR_700);
    }
    return sellerEntrustPriceQueueService.delete(sellerEntrustPriceQueueIds);
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
    return sellerEntrustPriceQueueService.getByPage(keys, length, start);
    }
    }

package com.stock.webapp.base.controller.stock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.stock.StockTradePrice;
import com.stock.service.stock.StockTradePriceService;
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
@RequestMapping(value="/stockTradePrice")
public class StockTradePriceController {

	@Autowired
	private StockTradePriceService stockTradePriceService;
	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/insert.do")
	public @ResponseBody RequestResultVO insert(HttpServletRequest request){
		String stockTradePriceString = request.getParameter("stockTradePrice");
StockTradePrice stockTradePrice = null;
		try{
stockTradePrice = JsonFastUtil.parseObject(stockTradePriceString, StockTradePrice.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return stockTradePriceService.insert(stockTradePrice);
	}
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update.do")
	public @ResponseBody RequestResultVO update(HttpServletRequest request){
		String stockTradePriceString = request.getParameter("stockTradePrice");
StockTradePrice stockTradePrice = null;
		try{
stockTradePrice = JsonFastUtil.parseObject(stockTradePriceString, StockTradePrice.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return stockTradePriceService.update(stockTradePrice);
	}
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete.do")
	public @ResponseBody RequestResultVO delete(HttpServletRequest request){
		String stockTradePriceIdsString = request.getParameter("stockTradePriceIds");
		List<Integer> stockTradePriceIds;
    try{
stockTradePriceIds = CommonUtils.idsArrayToList(stockTradePriceIdsString);
    }catch(Exception e){
    throw new BizException(Public.ERROR_700);
    }
    return stockTradePriceService.delete(stockTradePriceIds);
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
    return stockTradePriceService.getByPage(keys, length, start);
    }
    }

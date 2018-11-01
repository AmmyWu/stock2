package com.stock.webapp.base.controller.stock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.stock.StockOpenClosePrice;
import com.stock.service.stock.StockOpenClosePriceService;
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
@RequestMapping(value="/stockOpenClosePrice")
public class StockOpenClosePriceController {

	@Autowired
	private StockOpenClosePriceService stockOpenClosePriceService;
	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/insert.do")
	public @ResponseBody RequestResultVO insert(HttpServletRequest request){
		String stockOpenClosePriceString = request.getParameter("stockOpenClosePrice");
StockOpenClosePrice stockOpenClosePrice = null;
		try{
stockOpenClosePrice = JsonFastUtil.parseObject(stockOpenClosePriceString, StockOpenClosePrice.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return stockOpenClosePriceService.insert(stockOpenClosePrice);
	}
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update.do")
	public @ResponseBody RequestResultVO update(HttpServletRequest request){
		String stockOpenClosePriceString = request.getParameter("stockOpenClosePrice");
StockOpenClosePrice stockOpenClosePrice = null;
		try{
stockOpenClosePrice = JsonFastUtil.parseObject(stockOpenClosePriceString, StockOpenClosePrice.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return stockOpenClosePriceService.update(stockOpenClosePrice);
	}
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete.do")
	public @ResponseBody RequestResultVO delete(HttpServletRequest request){
		String stockOpenClosePriceIdsString = request.getParameter("stockOpenClosePriceIds");
		List<Integer> stockOpenClosePriceIds;
    try{
stockOpenClosePriceIds = CommonUtils.idsArrayToList(stockOpenClosePriceIdsString);
    }catch(Exception e){
    throw new BizException(Public.ERROR_700);
    }
    return stockOpenClosePriceService.delete(stockOpenClosePriceIds);
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
    return stockOpenClosePriceService.getByPage(keys, length, start);
    }
    }

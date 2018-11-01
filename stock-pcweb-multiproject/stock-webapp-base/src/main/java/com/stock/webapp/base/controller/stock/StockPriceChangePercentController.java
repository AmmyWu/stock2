package com.stock.webapp.base.controller.stock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.stock.StockPriceChangePercent;
import com.stock.service.stock.StockPriceChangePercentService;
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
@RequestMapping(value="/stockPriceChangePercent")
public class StockPriceChangePercentController {

	@Autowired
	private StockPriceChangePercentService stockPriceChangePercentService;
	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/insert.do")
	public @ResponseBody RequestResultVO insert(HttpServletRequest request){
		String stockPriceChangePercentString = request.getParameter("stockPriceChangePercent");
StockPriceChangePercent stockPriceChangePercent = null;
		try{
stockPriceChangePercent = JsonFastUtil.parseObject(stockPriceChangePercentString, StockPriceChangePercent.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return stockPriceChangePercentService.insert(stockPriceChangePercent);
	}
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update.do")
	public @ResponseBody RequestResultVO update(HttpServletRequest request){
		String stockPriceChangePercentString = request.getParameter("stockPriceChangePercent");
StockPriceChangePercent stockPriceChangePercent = null;
		try{
stockPriceChangePercent = JsonFastUtil.parseObject(stockPriceChangePercentString, StockPriceChangePercent.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return stockPriceChangePercentService.update(stockPriceChangePercent);
	}
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete.do")
	public @ResponseBody RequestResultVO delete(HttpServletRequest request){
		String stockPriceChangePercentIdsString = request.getParameter("stockPriceChangePercentIds");
		List<Integer> stockPriceChangePercentIds;
    try{
stockPriceChangePercentIds = CommonUtils.idsArrayToList(stockPriceChangePercentIdsString);
    }catch(Exception e){
    throw new BizException(Public.ERROR_700);
    }
    return stockPriceChangePercentService.delete(stockPriceChangePercentIds);
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
    return stockPriceChangePercentService.getByPage(keys, length, start);
    }
    }

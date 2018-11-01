package com.stock.webapp.base.controller.stock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.stock.StockTurnoverRate;
import com.stock.service.stock.StockTurnoverRateService;
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
@RequestMapping(value="/stockTurnoverRate")
public class StockTurnoverRateController {

	@Autowired
	private StockTurnoverRateService stockTurnoverRateService;
	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/insert.do")
	public @ResponseBody RequestResultVO insert(HttpServletRequest request){
		String stockTurnoverRateString = request.getParameter("stockTurnoverRate");
StockTurnoverRate stockTurnoverRate = null;
		try{
stockTurnoverRate = JsonFastUtil.parseObject(stockTurnoverRateString, StockTurnoverRate.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return stockTurnoverRateService.insert(stockTurnoverRate);
	}
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update.do")
	public @ResponseBody RequestResultVO update(HttpServletRequest request){
		String stockTurnoverRateString = request.getParameter("stockTurnoverRate");
StockTurnoverRate stockTurnoverRate = null;
		try{
stockTurnoverRate = JsonFastUtil.parseObject(stockTurnoverRateString, StockTurnoverRate.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return stockTurnoverRateService.update(stockTurnoverRate);
	}
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete.do")
	public @ResponseBody RequestResultVO delete(HttpServletRequest request){
		String stockTernoverRateIdsString = request.getParameter("stockTernoverRateIds");
		List<Integer> stockTernoverRateIds;
    try{
stockTernoverRateIds = CommonUtils.idsArrayToList(stockTernoverRateIdsString);
    }catch(Exception e){
    throw new BizException(Public.ERROR_700);
    }
    return stockTurnoverRateService.delete(stockTernoverRateIds);
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
    return stockTurnoverRateService.getByPage(keys, length, start);
    }
    }

package com.stock.webapp.base.controller.stock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.stock.StockTradeNum;
import com.stock.service.stock.StockTradeNumService;
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
@RequestMapping(value="/stockTradeNum")
public class StockTradeNumController {

	@Autowired
	private StockTradeNumService stockTradeNumService;
	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/insert.do")
	public @ResponseBody RequestResultVO insert(HttpServletRequest request){
		String stockTradeNumString = request.getParameter("stockTradeNum");
StockTradeNum stockTradeNum = null;
		try{
stockTradeNum = JsonFastUtil.parseObject(stockTradeNumString, StockTradeNum.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return stockTradeNumService.insert(stockTradeNum);
	}
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update.do")
	public @ResponseBody RequestResultVO update(HttpServletRequest request){
		String stockTradeNumString = request.getParameter("stockTradeNum");
StockTradeNum stockTradeNum = null;
		try{
stockTradeNum = JsonFastUtil.parseObject(stockTradeNumString, StockTradeNum.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return stockTradeNumService.update(stockTradeNum);
	}
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete.do")
	public @ResponseBody RequestResultVO delete(HttpServletRequest request){
		String stockTradeNumIdsString = request.getParameter("stockTradeNumIds");
		List<Integer> stockTradeNumIds;
    try{
stockTradeNumIds = CommonUtils.idsArrayToList(stockTradeNumIdsString);
    }catch(Exception e){
    throw new BizException(Public.ERROR_700);
    }
    return stockTradeNumService.delete(stockTradeNumIds);
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
    return stockTradeNumService.getByPage(keys, length, start);
    }
    }

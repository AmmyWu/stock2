package com.stock.webapp.base.controller.stock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.stock.StockExisting;
import com.stock.service.stock.StockExistingService;
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
@RequestMapping(value="/stockExisting")
public class StockExistingController {

	@Autowired
	private StockExistingService stockExistingService;
	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/insert.do")
	public @ResponseBody RequestResultVO insert(HttpServletRequest request){
		String stockExistingString = request.getParameter("stockExisting");
StockExisting stockExisting = null;
		try{
stockExisting = JsonFastUtil.parseObject(stockExistingString, StockExisting.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return stockExistingService.insert(stockExisting);
	}
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update.do")
	public @ResponseBody RequestResultVO update(HttpServletRequest request){
		String stockExistingString = request.getParameter("stockExisting");
StockExisting stockExisting = null;
		try{
stockExisting = JsonFastUtil.parseObject(stockExistingString, StockExisting.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return stockExistingService.update(stockExisting);
	}
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete.do")
	public @ResponseBody RequestResultVO delete(HttpServletRequest request){
		String existingIdsString = request.getParameter("existingIds");
		List<Integer> existingIds;
    try{
existingIds = CommonUtils.idsArrayToList(existingIdsString);
    }catch(Exception e){
    throw new BizException(Public.ERROR_700);
    }
    return stockExistingService.delete(existingIds);
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
    return stockExistingService.getByPage(keys, length, start);
    }
    }

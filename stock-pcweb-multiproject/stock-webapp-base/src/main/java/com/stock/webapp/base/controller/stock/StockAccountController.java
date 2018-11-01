package com.stock.webapp.base.controller.stock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.stock.StockAccount;
import com.stock.service.stock.StockAccountService;
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
@RequestMapping(value="/stockAccount")
public class StockAccountController {

	@Autowired
	private StockAccountService stockAccountService;
	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/insert.do")
	public @ResponseBody RequestResultVO insert(HttpServletRequest request){
		String stockAccountString = request.getParameter("stockAccount");
StockAccount stockAccount = null;
		try{
stockAccount = JsonFastUtil.parseObject(stockAccountString, StockAccount.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return stockAccountService.insert(stockAccount);
	}
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update.do")
	public @ResponseBody RequestResultVO update(HttpServletRequest request){
		String stockAccountString = request.getParameter("stockAccount");
StockAccount stockAccount = null;
		try{
stockAccount = JsonFastUtil.parseObject(stockAccountString, StockAccount.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return stockAccountService.update(stockAccount);
	}
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete.do")
	public @ResponseBody RequestResultVO delete(HttpServletRequest request){
		String stockAccountIdsString = request.getParameter("stockAccountIds");
		List<Integer> stockAccountIds;
    try{
stockAccountIds = CommonUtils.idsArrayToList(stockAccountIdsString);
    }catch(Exception e){
    throw new BizException(Public.ERROR_700);
    }
    return stockAccountService.delete(stockAccountIds);
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
    return stockAccountService.getByPage(keys, length, start);
    }
    }

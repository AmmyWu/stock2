package com.stock.webapp.base.controller.stock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.stock.SellerEntrustPrice;
import com.stock.service.stock.SellerEntrustPriceService;
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
@RequestMapping(value="/sellerEntrustPrice")
public class SellerEntrustPriceController {

	@Autowired
	private SellerEntrustPriceService sellerEntrustPriceService;
	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/insert.do")
	public @ResponseBody RequestResultVO insert(HttpServletRequest request){
		String sellerEntrustPriceString = request.getParameter("sellerEntrustPrice");
SellerEntrustPrice sellerEntrustPrice = null;
		try{
sellerEntrustPrice = JsonFastUtil.parseObject(sellerEntrustPriceString, SellerEntrustPrice.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return sellerEntrustPriceService.insert(sellerEntrustPrice);
	}
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update.do")
	public @ResponseBody RequestResultVO update(HttpServletRequest request){
		String sellerEntrustPriceString = request.getParameter("sellerEntrustPrice");
SellerEntrustPrice sellerEntrustPrice = null;
		try{
sellerEntrustPrice = JsonFastUtil.parseObject(sellerEntrustPriceString, SellerEntrustPrice.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return sellerEntrustPriceService.update(sellerEntrustPrice);
	}
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete.do")
	public @ResponseBody RequestResultVO delete(HttpServletRequest request){
		String sellerEntrustPriceIdsString = request.getParameter("sellerEntrustPriceIds");
		List<Integer> sellerEntrustPriceIds;
    try{
sellerEntrustPriceIds = CommonUtils.idsArrayToList(sellerEntrustPriceIdsString);
    }catch(Exception e){
    throw new BizException(Public.ERROR_700);
    }
    return sellerEntrustPriceService.delete(sellerEntrustPriceIds);
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
    return sellerEntrustPriceService.getByPage(keys, length, start);
    }
    }

package com.stock.webapp.base.controller.pcweb;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.common.exceptions.BizException;
import com.stock.dao.model.report.Templet;
import com.stock.pojo.vo.RequestResultVO;
import com.stock.service.common.util.CommonUtils;
import com.stock.service.sys.utils.HttpResponseConstants.Public;
import com.stock.service.sys.utils.JsonFastUtil;

@Controller
@RequestMapping(value="/templet")
public class TempletController {

	@Autowired
	private TempletService templetService;
	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/insert.do")
	public @ResponseBody RequestResultVO insert(HttpServletRequest request){
		String templetString = request.getParameter("templet");
		Templet templet = null;
		try{
			templet = JsonFastUtil.parseObject(templetString, Templet.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return templetService.insert(templet);
	}
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update.do")
	public @ResponseBody RequestResultVO update(HttpServletRequest request){
		String templetString = request.getParameter("templet");
		Templet templet = null;
		try{
			templet = JsonFastUtil.parseObject(templetString, Templet.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return templetService.update(templet);
	}
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete.do")
	public @ResponseBody RequestResultVO delete(HttpServletRequest request){
		String templetIdsString = request.getParameter("templetIds");
		List<Integer> templetIds;
		try{
			templetIds = CommonUtils.idsArrayToList(templetIdsString);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return templetService.delete(templetIds);
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
		return templetService.getByPage(keys, length, start);
	}
}

/**
 * 数联云通讯
 */
package com.stock.webapp.base.controller.pcweb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.sys.SysCustomer;
import com.stock.pojo.vo.RequestResultVO;
import com.stock.pojo.vo.basedata.SysCustomerVO;
import com.stock.service.sys.SysCustomerService;
import com.stock.service.sys.utils.SysConst;

@Controller
@RequestMapping(value = "/customer")
public class SysCustomerController {

	@Autowired
	private SysCustomerService customerService;

	@Autowired
	private HttpSession session;

	/**
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getCustomers.do")
	public @ResponseBody
	Object getCustomers(HttpServletRequest req) {

		String keys = req.getParameter("keys");

//		user.setType(userType);

		Integer pageSize = Integer.parseInt(req.getParameter("length"));
		Integer pageIndex = Integer.parseInt(req.getParameter("start"));

		return customerService.getCustomerVO(keys,pageSize, pageIndex);
	}
	
	
	/**
	 * 
	 * getUsers:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param req
	 * @param user
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/getUserIdName.do")
	public @ResponseBody
	Object getUsers(String userId) {
		
		return SysConst.USER_MAP;
	}



	/**
	 * 
	 * del:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param request
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/delete.do")
	public @ResponseBody
	RequestResultVO del(HttpServletRequest request) {

		String customerIds = request.getParameter("customerIds");

		RequestResultVO j = new RequestResultVO();

		if (customerIds == null || "".equals(customerIds)) {
			j.setCode(1000);
			j.setMessage("请选择删除的用户！");

		}

		if (customerService.delete(customerIds)) {
			j.setCode(0);
			j.setMessage("用户删除成功!");
		} else {

			j.setCode(1000);
			j.setMessage("数据访问失败，用户删除失败！");
		}

		return j;
	}

	/**
	 * 
	 * save:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param request
	 * @return
	 * @since JDK 1.7
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/save.do")
	public @ResponseBody
	RequestResultVO save(HttpServletRequest request) {

		List<SysCustomer> userList = (List<SysCustomer>) JSONArray.toCollection(
				JSONArray.fromObject(request.getParameter("data")),
				SysCustomer.class);

		RequestResultVO j = new RequestResultVO();

		SysCustomer customer = new SysCustomer();

		BeanUtils.copyProperties(userList.get(0), customer);

		if (StringUtils.isEmpty(customer.getCustomerName())) {

			j.setCode(1000);
			j.setMessage("用户名不能为空，保存失败!");
			return j;
		}

		// do insert
		if (customer.getCustomerId() == null) {
			if (customerService.insert(customer)) {
				j.setCode(0);
				j.setMessage("新增用户保存成功!");
			} else {
				j.setCode(1000);
				j.setMessage("新增用户保存失败!");
			}

		} else { // do update
			if (customerService.update(customer)) {
				j.setCode(0);
				j.setMessage("修改用户保存成功!");
			} else {
				j.setCode(1000);
				j.setMessage("修改用户保存失败!");
			}
		}

		return j;
	}

	/**
	 * 
	 * save:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param request
	 * @return
	 * @since JDK 1.7
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert.do")
	public @ResponseBody
	RequestResultVO insert(SysCustomer user) {

		// SysCustomer user = new SysCustomer();
		//
		// BeanUtils.copyProperties(userVO, user);

		customerService.insert(user);

		RequestResultVO rrVO = new RequestResultVO();

		rrVO.setCode(0);
		rrVO.setMessage("保存成功!");
		return rrVO;

	}

	/**
	 * 
	 * save:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param request
	 * @return
	 * @since JDK 1.7
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/update.do")
	public @ResponseBody
	RequestResultVO update(SysCustomer customer) {

		// SysCustomer user = new SysCustomer();
		//
		// BeanUtils.copyProperties(userVO, user);

		customerService.update(customer);

		RequestResultVO rrVO = new RequestResultVO();

		rrVO.setCode(0);
		rrVO.setMessage("保存成功!");
		return rrVO;

	}
	/**
	 * 
	 * save:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param request
	 * @return
	 * @since JDK 1.7
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insertNewCustomer.do")
	public @ResponseBody
	RequestResultVO insertNewCustomer(SysCustomerVO user) {

		// SysCustomer user = new SysCustomer();
		//
		// BeanUtils.copyProperties(userVO, user);

		customerService.insert(user);

		RequestResultVO rrVO = new RequestResultVO();

		rrVO.setCode(0);
		rrVO.setMessage("保存成功!");
		return rrVO;

	}

	
	

	@InitBinder    
	public void initBinder(WebDataBinder binder) {    
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
	        dateFormat.setLenient(false);    
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
	}  
	
	

}

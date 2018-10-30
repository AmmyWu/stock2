/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.controller.pcweb
 * 类/接口名	: SysEmployeeController
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午2:44:34
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午2:44:34
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.webapp.base.controller.pcweb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.stock.common.util.convert.DateConvert;
import com.stock.dao.model.sys.SysEmployee;
import com.stock.pojo.vo.RequestResultVO;
import com.stock.pojo.vo.sys.SysEmployeeVO;
import com.stock.service.common.SmsService;
import com.stock.service.common.util.DateJsonValueProcessor;
import com.stock.service.common.util.DateMorpherEx;
import com.stock.service.common.util.ValidateCodeUtil;
import com.stock.service.sys.SysEmployeeService;
import com.stock.service.sys.utils.HttpResponseConstants;

/**
 * 
 * ClassName : SysEmployeeController Function : TODO date : 2017年5月7日下午3:38:37
 * 
 * @author chengxl
 * @version :
 * @since JDK 1.7
 */
@Controller
@RequestMapping(value = "/employee")
public class SysEmployeeController {

	@Autowired
	private SysEmployeeService employeeService;
	@Autowired
	private SmsService smsService;

	/**
	 * 
	 * 查询员工
	 * @author chengxl
	 * @param req
	 * @param employee
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/listAll.do")
	public @ResponseBody
	Object listAll(HttpServletRequest req, SysEmployee employee) {

		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));

		return JSONArray.fromObject(employeeService.getEmployees(), config);
	}
	

	/**
	 * 返回未分配用户的员工数据
	 * 
	 * @author chengxl
	 * @param req
	 * @param employee
	 * @return
	 * @since JDK 1.7
	 */ 
	@RequestMapping(value = "/listEmployeesOfUnallotUsers.do")
	public @ResponseBody
	Object listEmployeesOfUnallotUsers(HttpServletRequest req) {

	

		return JSONArray.fromObject(employeeService.getEmployeesOfUnallotUsers());
	}
	

	/**
	 * 
	 * list:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param req
	 * @param employee
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/listByPage.do")
	public @ResponseBody
	Object listByPage(HttpServletRequest req) {
		
		String keys = req.getParameter("keys");
		
		Integer pageSize = Integer.parseInt(req.getParameter("length"));
		Integer pageNow = Integer.parseInt(req.getParameter("start"));
		Map<String, Object> map = employeeService.getEmployeeByPage(keys,
				pageSize, pageNow);
		return JSONObject.fromObject(map);
	}

	/**
	 * 
	 * list:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param req
	 * @param employee
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/list.do")
	public @ResponseBody
	Object list(HttpServletRequest req) {
		

		String keys = req.getParameter("keys");
		
		Integer pageSize = Integer.parseInt(req.getParameter("rows"));
		Integer pageNow = Integer.parseInt(req.getParameter("page"));
		Map<String, Object> map = employeeService.getEmployeeByPage(keys,
				pageSize, pageNow);
		return JSONObject.fromObject(map);
	}



	/**
	 * 新增员工
	 * 
	 * @author chengxl
	 * @param req
	 * @return
	 * @since JDK 1.7
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert.do")
	public @ResponseBody
	Object insert(SysEmployee employee) {
		
//		employee.setCreateTime(employee.getAmendTime());
//		employee.setCreator(employee.getAmender());


		return employeeService.insert(employee);

	}

	/**
	 * 
	 * 修改员工
	 * 
	 * @author chengxl
	 * @param req
	 * @return
	 * @since JDK 1.7
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/update.do")
	public @ResponseBody
	Object update(SysEmployee employee) {

		return employeeService.update(employee);

	}

	/**
	 * 
	 * 删除员工
	 * 
	 * @author chengxl
	 * @param req
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/delete.do")
	public @ResponseBody
	String delete(HttpServletRequest request) {
		String ids = request.getParameter("employeeIds");
		String[] cIds = ids.split(",");

		return employeeService.deleteEmpById(cIds);

	}

	/**
	 * 
	 * exportExcel:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param request
	 * @param response
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/exportExcel.do")
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response) {

		// File file = new
		// File(getServletContext().getRealPath("WEB-INF/book.jpg"));

		String excelFileName;
		try {
			excelFileName = new String(
					("å‘˜å·¥ä¿¡æ¯"
							+ DateConvert.dateToStr(new Date(),
									"yyyyMMddhhmmss") + ".xls")
							.getBytes("gb2312"),
					"ISO8859-1");

			response.setContentType("octets/stream");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ excelFileName);

		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String queryPara = request.getParameter("queryPara");

		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpherEx(new String[] { "yyyy-MM-dd HH:mm:ss" },
						(Date) null));

		SysEmployeeVO sysEmployeeEx = (SysEmployeeVO) JSONObject.toBean(
				JSONObject.fromObject(queryPara), SysEmployeeVO.class);

		try {

			OutputStream out = response.getOutputStream();
			employeeService.exportExcel(sysEmployeeEx, out);
			out.close();
			System.out.println("excelï¿½ï¿½ï¿½ï¿½ï¿½É¹ï¿½ï¿½ï¿½");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * getByCustomerName:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param employeeName
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/getByEmployeeName.do")
	public @ResponseBody
	Object getByCustomerName(String employeeName) {

		// String customerName = req.getParameter("customerName");

		if (StringUtils.isEmpty(employeeName))
			return null;

		return JSONObject.fromObject(employeeService
				.getByCustomerName(employeeName));

	}

	/**
	 * 
	 * getByCellPhone:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * 
	 * @author chengxl
	 * @param cellPhone
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/getByCellPhone.do")
	public @ResponseBody
	Object getByCellPhone(String cellPhone) {

		// String customerName = req.getParameter("customerName");

		if (StringUtils.isEmpty(cellPhone))
			return null;

		return JSONObject.fromObject(employeeService.getByCellPhone(cellPhone));

	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	//生成验证码
	@RequestMapping(value = "/sendValidateCode.do")
	public  @ResponseBody Object sendValidateCode(HttpServletRequest request, HttpServletResponse response) {
        
		String cellPhone = request.getParameter("cellPhone");
		
		if(StringUtils.isEmpty(cellPhone))
			return "请输入正确的手机号码。";
		
		 ValidateCodeUtil randomValidateCode = new ValidateCodeUtil();
		 
		 String validateCode = randomValidateCode.getValidateCode(4);
		 JSONObject jo = smsService.sendSMS(cellPhone,validateCode + "为您本次注册的验证码，验证码10分钟后失效，请尽快注册。");
		
		if(!"短信发送成功".equals(jo.getString("result")))
			return "短信验证码发送失败，失败原因："+jo.getString("result")+"！请与管理员联系。";

		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(10*60);//session 10分钟有效期
		session.setAttribute(ValidateCodeUtil.RANDOMCODEKEY,validateCode);

		return validateCode;
    }
	/**
	 * 导入通讯录
	 * @param excel
	 * @param request
	 * @param response
	 */
	 @RequestMapping(value = "/importExcel.do", method = RequestMethod.POST)
		public @ResponseBody  void importExcel(@RequestParam("excel") MultipartFile excel,HttpServletRequest request,HttpServletResponse response){ 
			response.setContentType("text/html;charset=utf-8");
			RequestResultVO requestResultVO = new RequestResultVO();
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
				requestResultVO.setContent(HttpResponseConstants.Public.ERROR_CODE, HttpResponseConstants.Public.ERROR_500, null);
				out.println(JSONObject.fromObject(requestResultVO));
				out.flush();
				out.close();
				return;
			}		
				out.println(JSONObject.fromObject(employeeService.importExcel(excel)));
				out.flush();
				out.close();
	       // return freightShippingQuotationService.importExcel(excel);
		}
}
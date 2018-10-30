
 /**************************************************************************
 * 项目名称：数联软件 web开发框架                          
 ***************************************************************************/
package com.stock.webapp.base.controller.pcweb;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.mapper.sys.SysAttachmentMapper;
import com.stock.dao.model.sys.SysAttachment;
import com.stock.dao.model.sys.SysAttachmentExample;
import com.stock.pojo.vo.RequestResultVO;
import com.stock.service.sys.CommonService;
import com.stock.webapp.base.controller.CommonController;



 /**
 * TODO 请在此处添加注释
 * @author <a href="mailto:chengxl@stocksoft.com">chengxl</a>
 * @version $Id$   
 * @since 2.0
 */


@Controller
@RequestMapping(value = "/sysAttachment")
public class SysAttachmentController extends
		CommonController<SysAttachment, SysAttachmentMapper, SysAttachmentExample> {

	
	@Autowired
	private SysAttachmentMapper sysAttachmentMapper;
	
	
	@Resource(name = "commonService")
	@Override
	public void setCommonService(CommonService<SysAttachment, SysAttachmentMapper, SysAttachmentExample> baseService) {
		super.setCommonService(baseService);
	}

	@RequestMapping(value = "/listByPage.do")
	public @ResponseBody
	Object listByPage(HttpServletRequest req) {
		
		SysAttachment attachment = new SysAttachment();
		
		SysAttachmentExample exmple = new SysAttachmentExample();
		
		
		
		
		return super.listByPage(req,attachment ,sysAttachmentMapper,exmple);
	}
	

	
	@RequestMapping(value = "/listByKeys.do")
	public @ResponseBody
	Object listByKeys(HttpServletRequest req) {
		

		SysAttachment attachment = new SysAttachment();
		
		SysAttachmentExample exmple = new SysAttachmentExample();
		

		return super.listByKeys(req,attachment, sysAttachmentMapper,exmple);
	}
	
	
	@RequestMapping(value = "/getById.do")
	public @ResponseBody
	Object getById(Integer id) {
		
		return super.getById(id, sysAttachmentMapper);
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert.do")
	public @ResponseBody
	Object insert( SysAttachment attachment) {

		return super.insert( attachment,sysAttachmentMapper);

	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/update.do")
	public @ResponseBody
	Object update(	SysAttachment attachment) {
		
		RequestResultVO result = (RequestResultVO)super.update( attachment,sysAttachmentMapper);
		return result;
	}
	
	
	@RequestMapping(value = "/delete.do")
	public @ResponseBody
	Object delete(String attachmentIds) {

		
		SysAttachmentExample exmple = new SysAttachmentExample();
		
		return super.delete(attachmentIds,"attachmentId",sysAttachmentMapper,exmple);

	}
	
	
	

}

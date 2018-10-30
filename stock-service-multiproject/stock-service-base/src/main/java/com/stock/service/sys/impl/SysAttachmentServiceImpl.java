package com.stock.service.sys.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.common.util.ReflectionUtil;
import com.stock.dao.mapper.sys.SysAttachmentMapper;
import com.stock.dao.mapper.sys.SysAttachmentMapperEx;
import com.stock.dao.model.sys.SysAttachment;
import com.stock.dao.model.sys.SysAttachmentExample;
import com.stock.dao.model.sys.SysAttachmentExample.Criteria;
import com.stock.service.common.util.CriteriaUtils;
import com.stock.service.sys.SysAttachmentService;

@Service
public class SysAttachmentServiceImpl implements SysAttachmentService {
	
	@Autowired
	private SysAttachmentMapperEx sysAttachmentMapperEx;
	@Autowired
	private SysAttachmentMapper sysAttachmentMapper;
	
		
	@Override
	public SysAttachment insertReturnId(SysAttachment attachment) {
		sysAttachmentMapperEx.insertReturnId(attachment);
		
		return attachment;
	}


	@Override
	public SysAttachment getById(int id) {
		return sysAttachmentMapper.selectByPrimaryKey(id);
	}


	@Override
	public int update(SysAttachment sysAttachment) {
		return sysAttachmentMapper.updateByPrimaryKeySelective(sysAttachment);
	}


	@Override
	public String getAttachmentHTML(SysAttachment sysAttachment, String type) {
	SysAttachmentExample example = new SysAttachmentExample();
		
		Criteria criteria = example.createCriteria();
		
		// 获得news model非空（null）属性list
		List<Map<?, ?>> list = ReflectionUtil.getFiledValues(sysAttachment, false);
		
		CriteriaUtils.setCriteria(criteria, list);
	
		List<SysAttachment> sysAList = sysAttachmentMapper.selectByExample(example); 
		
		String html = "";
		
		if("UPDATE".equals(type)){
			/*for(SysAttachment sysA:sysAList){

				html += "<a class='"+ sysA.getTitle() +"' target='_blank' href='"+sysA.getAttachmentPath()+"'>"+sysA.getAttachmentName()+"</a>"
				
				+ "<label class=\""+sysA.getTitle() +"\"onclick=\"deleteUplsysdFile('"+sysA.getBusinessTable()+"','"+sysA.getBusinessId()+"','"+sysA.getTitle()+"')\">[删除]</label></br>"; 
				
			}*/
			for(SysAttachment sysA:sysAList){

				html += "<a class='"+ sysA.getTitle().replace(".", "") +"' target='_blank' href='"+sysA.getAttachmentPath()+"'>"+sysA.getAttachmentName()+"</a>"
				
				+ "<a class=\""+sysA.getTitle().replace(".", "") +"\"onclick=\"deleteUploadFile('"+sysA.getAttachmentId()+"','"+sysA.getTitle().replace(".", "")+"')\">[删除]</a></br>"; 
				
			}
		}
		
		if("VIEW".equals(type)){
			
			for(SysAttachment sysA:sysAList){

				html += "<a target='_blank' href='"+sysA.getAttachmentPath()+"'>"+sysA.getAttachmentName()+"&nbsp;&nbsp;&nbsp;</a></br>";
			}
		}
	
		return html;
		
	}

}

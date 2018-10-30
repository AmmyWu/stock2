
 /**************************************************************************
 * 项目名称：数联软件 web开发框架                          
 ***************************************************************************/
package com.stock.service.sys.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.common.util.ReflectionUtil;
import com.stock.dao.mapper.sys.SysMessageMapper;
import com.stock.dao.model.sys.SysMessage;
import com.stock.dao.model.sys.SysMessageExample;
import com.stock.dao.model.sys.SysMessageExample.Criteria;
import com.stock.dao.page.Page;
import com.stock.pojo.vo.sys.SysMessageVO;
import com.stock.service.common.DataAuthorizeService;
import com.stock.service.common.util.CriteriaUtils;
import com.stock.service.common.util.DateJsonValueProcessor;
import com.stock.service.sys.SysMessageService;
import com.stock.service.sys.utils.SysConst;


 /**
 * TODO 请在此处添加注释
 * @author <a href="mailto:chengxl@stocksoft.com">chengxl</a>
 * @version $Id$   
 * @since 2.0
 */

@Service("sysMessageService")
public class SysMessageServiceImpl implements SysMessageService {
	
	@Autowired
	private SysMessageMapper messageMapper;
	
	@Autowired
	private DataAuthorizeService dataAuthorizeService;
	
	


	public SysMessageServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param text
	 * @return
	 * @see com.stock.service.sys.SysMessageService#getMessages(java.lang.String)
	 */
	@Override
	public List<SysMessage> getMessages(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 * @see com.stock.service.sys.SysMessage#getMessages()
	 */
	@Override
	public List<SysMessage> getMessages() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param keys
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 * @see com.stock.service.sys.SysMessageService#getMessages(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Map<String, Object> getMessages(String keys, Integer pageSize,
			Integer pageIndex) {
		
		SysMessageExample example = new SysMessageExample();
		
		this.setExample(example, keys);

		int total = messageMapper.countByExample(example);
		
		Page page = new Page();

		// page.setBegin((pageIndex-1)*pageSize);

		page.setBegin(pageIndex);

		page.setLength(pageSize);

		example.setPage(page);

		example.setOrderByClause(" message_id desc");

		List<SysMessage> messages = messageMapper.selectByExampleWithBLOBs(example);

		Map<String, Object> map = new HashMap<String, Object>();

		// map.put("total", total);
		// map.put("rows", messages);

		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

		
		map.put("recordsTotal", total);// total
		map.put("recordsFiltered", total);// total

		map.put("aaData", JSONArray.fromObject(this.convert(messages),config));

		return map;
		
	}
	
	
	private List<SysMessageVO> convert(List<SysMessage> messageList) {

		if (messageList == null)
			return null;

		List<SysMessageVO> messageVOList = new ArrayList<SysMessageVO>();

		for (SysMessage message : messageList) {
			SysMessageVO messageVO = new SysMessageVO();
			// ReflectionUtils.parentToChild(e, he);

			BeanUtils.copyProperties(message, messageVO);
			
			if(message.getReadNum() == null)
				message.setReadNum(0);

			//读取blob数据
			messageVO.setStrMessage(new String(message.getMessage()));
			messageVO.setMessage(null);
			
			if(message.getProducer() != null)
				messageVO.setProducerName(SysConst.USER_MAP.get(Integer.parseInt(message.getProducer())));
			else
				messageVO.setProducerName("系统公告");

			messageVOList.add(messageVO);

		}

		return messageVOList;

	}


	// 根据 keys的值，设置Criteria
	private void setExample(SysMessageExample example, String keys) {

		JSONObject jKeys = JSONObject.fromObject(keys);

		Criteria criteria = example.createCriteria();

		if (keys == null || "{}".equals(keys))
			return;

		SysMessage message = (SysMessage) JSONObject.toBean(jKeys, SysMessage.class);

		if (message == null)
			return;

		// 获得news model非空（null）属性list
		List<Map<?, ?>> list = ReflectionUtil.getFiledValues(message, false);

		if (list == null || list.size() == 0)
			return;

		CriteriaUtils.setCriteria(criteria, list);

	}


	/**
	 * 插入消息
	 * @param ，message对象的consumer属性为接收人，包括多个
	 * @return
	 * @see com.stock.service.sys.SysMessageService#insert(com.stock.service.sys.SysMessage)
	 */
	@Override
	public boolean insert(SysMessage message) {
		// TODO Auto-generated method stub
		
		message.setTime(new Date());
		message.setReadNum(0);
		
		dataAuthorizeService.addDataAuthorizeInfo(message, "insert");
		
		return messageMapper.insert(message) ==1?true:false;
	}

	/**
	 * @param rid
	 * @return
	 * @see com.stock.service.sys.SysMessageService#delete(java.lang.Integer)
	 */
	@Override
	public boolean delete(Integer rid) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @param messagesIds
	 * @return
	 * @see com.stock.service.sys.SysMessageService#deleteMessages(java.lang.String)
	 */
	@Override
	public boolean deleteMessages(String messagesIds) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 修改阅读的次数
	 * @param messages
	 * @return
	 * @see com.stock.service.sys.SysMessageService#update(com.stock.service.sys.SysMessage)
	 */
	@Override
	public boolean updateReadNum(Integer messageId,Integer readNum) {
		// TODO Auto-generated method stub
		
		SysMessage message = new SysMessage();
		
		message.setMessageId(messageId);
		
		message.setReadNum(readNum == null?1:(readNum+1));
		
		message.setLastReadTime(new Date());
		
		
		dataAuthorizeService.addDataAuthorizeInfo(message, "update");
		
		messageMapper.updateByPrimaryKeySelective(message);
		
		return false;
	}

}

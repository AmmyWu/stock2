package com.stock.dao.mapper.sys;

import com.stock.dao.model.sys.SysAttachment;

/**
 * 附件扩展类
  * @author <a href="mailto:taofeng@zjport.gov.cn">taofeng</a>
  * @version $Id$   
  * @since 2.0
 */
public interface SysAttachmentMapperEx extends SysAttachmentMapper{

	/**
	 * 增加一条记录返回主键
	   * @param record
	   * @return
	 */
    int insertReturnId (SysAttachment attachment);

}
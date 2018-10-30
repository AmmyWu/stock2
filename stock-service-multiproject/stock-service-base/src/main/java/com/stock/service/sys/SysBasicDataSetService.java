/**
 * 
 */
package com.stock.service.sys;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.sys.SysBasicDataSet;


/**
 * Copyright@宁波高新区敏智科技有限公司 www.mz3co.com
 * Copyright@浙江大学软件学院S309实验室
 *
 * @author chengxl
 * Cteated time：2015年1月8日 上午9:06:27   
 *
 */
public interface SysBasicDataSetService {

	public void init_BASICDATASET_TREE();
	
	public boolean delete(Integer id);
	
	public boolean delete(SysBasicDataSet basicDataSet);
	
	public boolean update(SysBasicDataSet basicDataSet);
	
	public boolean insert(SysBasicDataSet basicDataSet);


	
	/**
	 * 获得所有分组的基础数据
	 * @return
	 */
	public Map<String,List<?>>  getAllBasicDataSetGroupbyParentId(String []parentIds);
	
	/**
	 * 获取所有基础资料信息
	 */
	public Map<Integer,SysBasicDataSet> getBasicDataSetByParentId(Integer parentId);

		
	public String idToName(int id, String nameType);
}

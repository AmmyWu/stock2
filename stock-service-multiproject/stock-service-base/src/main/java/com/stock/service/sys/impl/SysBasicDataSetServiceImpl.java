/**
 * 
 */
package com.stock.service.sys.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.dao.mapper.sys.SysBasicDataSetMapper;
import com.stock.dao.model.sys.SysBasicDataSet;
import com.stock.dao.model.sys.SysBasicDataSetExample;
import com.stock.service.sys.SysBasicDataSetService;
import com.stock.service.sys.utils.SysBasicDataSetTree;
import com.stock.service.sys.utils.SysBasicDataSetTreeNode;
import com.stock.service.sys.utils.SysConst;


/**
 * Copyright@宁波高新区敏智科技有限公司 www.mz3co.com Copyright@浙江大学软件学院S309实验室
 * 
 * @author chengxl Cteated time：2015年1月8日 上午9:13:48
 * @param <JSONArray>
 * 
 */
@Service("basicDataSetService")
public class SysBasicDataSetServiceImpl<JSONArray> implements SysBasicDataSetService {

	@Autowired
	private SysBasicDataSetMapper basicDataSetMappser;

	

	
	private List<SysBasicDataSet> getBasicDataSets() {

		SysBasicDataSetExample example = new SysBasicDataSetExample();

		example.setOrderByClause(" basic_data_set_Id asc");

		return basicDataSetMappser.selectByExample(example);
	}

	// 对基础数据按照其父节点进行分类

	public Map<String, List<?>> getAllBasicDataSetGroupbyParentId(
			String[] parentIds) {

		if (parentIds == null || parentIds.length == 0)
			return null;

		if (SysConst.BASICDATASET_TREE == null)
			this.init_BASICDATASET_TREE();

		Map<String, List<?>> mapBasicData = new HashMap<String, List<?>>();

		List<SysBasicDataSet> listBasicData = null;

		for (String parentId : parentIds) {

			listBasicData = new ArrayList<SysBasicDataSet>();

			for (SysBasicDataSet bds : SysConst.BASICDATASET_TREE
					.getListBasicDataSet()) {

				if (bds.getSuperiorId() != null
						&& bds.getSuperiorId() == Integer.parseInt(parentId))
					listBasicData.add(bds);

			}


			this.sort(listBasicData);

			mapBasicData.put(parentId.toString(), listBasicData);
		}

		return mapBasicData;

	}

	

	// 对list进行排序
	private void sort(List<SysBasicDataSet> listBasicDataSet) {

		Collections.sort(listBasicDataSet, new Comparator<SysBasicDataSet>() {
			public int compare(SysBasicDataSet arg0, SysBasicDataSet arg1) {
				return arg0.getEnName().compareTo(arg1.getEnName());
			}
		});
	}



	/**
	 * 
	 * @param id
	 * @param id
	 *            CHINESE,ENGLISH,C_E
	 * @return
	 */

	public String idToName(int id, String nameType) {

		if (SysConst.BASICDATASET_TREE == null)
			this.init_BASICDATASET_TREE();

		SysBasicDataSet bds = SysConst.BASICDATASET_TREE.getNode(id); // Const.MAP_BASICDATASET.get(id);

		if (bds == null)
			return "";

		if ("C_E".equals(nameType))
			return bds.getEnName() + "||" + bds.getCnName();

		if ("CHINESE".equals(nameType))
			return bds.getCnName();

		if ("ENGLISH".equals(nameType))
			return bds.getEnName();

		return "";
	}

	
	/*
	 * parentId,parentId = null时,获取所有的父节点 (non-Javadoc)
	 * 
	 * @see
	 * com.mz3co.pcbas.service.BasicDataSetService#getBasicDataSetByParentId()
	 */
	public Map<Integer, SysBasicDataSet> getBasicDataSetByParentId(Integer parentId) {
		// TODO Auto-generated method stub

		Map<Integer, SysBasicDataSet> mapBasicDataParent = new HashMap<Integer, SysBasicDataSet>();
		// TODO Auto-generated method stub
		SysBasicDataSetExample example = new SysBasicDataSetExample();

		if (parentId == null || "".equals(parentId))
			example.or().andSuperiorIdIsNotNull();
		else
			example.or().andSuperiorIdEqualTo(parentId);

		example.setOrderByClause("English_name asc");

		List<SysBasicDataSet> listBDS = basicDataSetMappser
				.selectByExample(example);

		for (SysBasicDataSet bds : listBDS) {

			mapBasicDataParent.put(bds.getBasicDataSetId(), bds);
		}

		return mapBasicDataParent;
	}



	@Override
	public void init_BASICDATASET_TREE() {
		// TODO Auto-generated method stub

		SysConst.BASICDATASET_TREE = new SysBasicDataSetTree(this.getBasicDataSets());
	}

	@Override
	public boolean insert(SysBasicDataSet basicDataSet) {
		if (basicDataSetMappser.insertSelective(basicDataSet) != 1)
			return false;

		this.init_BASICDATASET_TREE();

		return true;
	}

	@Override
	public boolean update(SysBasicDataSet basicDataSet) {
		// TODO Auto-generated method stub

		if (basicDataSetMappser.updateByPrimaryKey(basicDataSet) != 1)
			return false;

		this.init_BASICDATASET_TREE();

		return true;

	}

	@Override
	public boolean delete(SysBasicDataSet basicDataSet) {
		// TODO Auto-generated method stub

		if (basicDataSetMappser.deleteByPrimaryKey(basicDataSet
				.getBasicDataSetId()) != 1)
			return false;

		this.init_BASICDATASET_TREE();

		return true;

	}

	@Override
	public boolean delete(Integer id) {

		SysBasicDataSetTreeNode bdtn = SysConst.BASICDATASET_TREE.getNode(id);

		// 如果存在子节点不允许删除
		if (bdtn.getChildren() != null && bdtn.getChildren().size() > 0)
			return false;

		if (basicDataSetMappser.deleteByPrimaryKey(id) != 1)
			return false;

		this.init_BASICDATASET_TREE();

		return true;
	}



}
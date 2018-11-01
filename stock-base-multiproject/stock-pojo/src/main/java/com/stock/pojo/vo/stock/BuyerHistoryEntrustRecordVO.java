package com.stock.pojo.vo.stock;

import com.stock.dao.model.stock.BuyerHistoryEntrustRecord;
import com.stock.pojo.vo.BaseModelVO;

public class BuyerHistoryEntrustRecordVO extends BuyerHistoryEntrustRecord {

	private BaseModelVO baseModel;

	public BaseModelVO getBaseModel() {
		return baseModel;
	}
	public void setBaseModel(BaseModelVO baseModel) {
		this.baseModel = baseModel;
	}
}
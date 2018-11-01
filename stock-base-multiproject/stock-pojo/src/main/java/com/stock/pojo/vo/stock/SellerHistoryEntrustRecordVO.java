package com.stock.pojo.vo.stock;

import com.stock.dao.model.stock.SellerHistoryEntrustRecord;
import com.stock.pojo.vo.BaseModelVO;

public class SellerHistoryEntrustRecordVO extends SellerHistoryEntrustRecord {

	private BaseModelVO baseModel;

	public BaseModelVO getBaseModel() {
		return baseModel;
	}
	public void setBaseModel(BaseModelVO baseModel) {
		this.baseModel = baseModel;
	}
}
package com.stock.pojo.vo.stock;

import com.stock.dao.model.stock.BuyerSellerHistoryEntrustRecord;
import com.stock.pojo.vo.BaseModelVO;

public class BuyerSellerHistoryEntrustRecordVO extends BuyerSellerHistoryEntrustRecord {

	private BaseModelVO baseModel;

	public BaseModelVO getBaseModel() {
		return baseModel;
	}
	public void setBaseModel(BaseModelVO baseModel) {
		this.baseModel = baseModel;
	}
}
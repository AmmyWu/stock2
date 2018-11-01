package com.stock.pojo.vo.stock;

import com.stock.dao.model.stock.SellerEntrustPriceQueue;
import com.stock.pojo.vo.BaseModelVO;

public class SellerEntrustPriceQueueVO extends SellerEntrustPriceQueue {

	private BaseModelVO baseModel;

	public BaseModelVO getBaseModel() {
		return baseModel;
	}
	public void setBaseModel(BaseModelVO baseModel) {
		this.baseModel = baseModel;
	}
}
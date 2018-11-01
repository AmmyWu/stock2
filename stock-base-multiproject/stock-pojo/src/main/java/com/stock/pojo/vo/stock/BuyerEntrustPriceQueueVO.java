package com.stock.pojo.vo.stock;

import com.stock.dao.model.stock.BuyerEntrustPriceQueue;
import com.stock.pojo.vo.BaseModelVO;

public class BuyerEntrustPriceQueueVO extends BuyerEntrustPriceQueue {

	private BaseModelVO baseModel;

	public BaseModelVO getBaseModel() {
		return baseModel;
	}
	public void setBaseModel(BaseModelVO baseModel) {
		this.baseModel = baseModel;
	}
}
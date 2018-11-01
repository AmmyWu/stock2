package com.stock.pojo.vo.stock;

import com.stock.dao.model.stock.BuyerEntrustPrice;
import com.stock.pojo.vo.BaseModelVO;

public class BuyerEntrustPriceVO extends BuyerEntrustPrice {

	private BaseModelVO baseModel;

	public BaseModelVO getBaseModel() {
		return baseModel;
	}
	public void setBaseModel(BaseModelVO baseModel) {
		this.baseModel = baseModel;
	}
}
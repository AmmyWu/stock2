package com.stock.pojo.vo.stock;

import com.stock.dao.model.stock.SellerEntrustPrice;
import com.stock.pojo.vo.BaseModelVO;

public class SellerEntrustPriceVO extends SellerEntrustPrice {

	private BaseModelVO baseModel;

	public BaseModelVO getBaseModel() {
		return baseModel;
	}
	public void setBaseModel(BaseModelVO baseModel) {
		this.baseModel = baseModel;
	}
}
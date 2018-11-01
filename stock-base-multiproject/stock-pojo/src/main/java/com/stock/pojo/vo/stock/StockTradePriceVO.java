package com.stock.pojo.vo.stock;

import com.stock.dao.model.stock.StockTradePrice;
import com.stock.pojo.vo.BaseModelVO;

public class StockTradePriceVO extends StockTradePrice {

	private BaseModelVO baseModel;

	public BaseModelVO getBaseModel() {
		return baseModel;
	}
	public void setBaseModel(BaseModelVO baseModel) {
		this.baseModel = baseModel;
	}
}
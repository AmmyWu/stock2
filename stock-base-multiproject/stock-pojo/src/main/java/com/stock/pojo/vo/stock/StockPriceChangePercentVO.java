package com.stock.pojo.vo.stock;

import com.stock.dao.model.stock.StockPriceChangePercent;
import com.stock.pojo.vo.BaseModelVO;

public class StockPriceChangePercentVO extends StockPriceChangePercent {

	private BaseModelVO baseModel;

	public BaseModelVO getBaseModel() {
		return baseModel;
	}
	public void setBaseModel(BaseModelVO baseModel) {
		this.baseModel = baseModel;
	}
}
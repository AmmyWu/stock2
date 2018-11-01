package com.stock.pojo.vo.stock;

import com.stock.dao.model.stock.StockOpenClosePrice;
import com.stock.pojo.vo.BaseModelVO;

public class StockOpenClosePriceVO extends StockOpenClosePrice {

	private BaseModelVO baseModel;

	public BaseModelVO getBaseModel() {
		return baseModel;
	}
	public void setBaseModel(BaseModelVO baseModel) {
		this.baseModel = baseModel;
	}
}
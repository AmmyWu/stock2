package com.stock.pojo.vo.stock;

import com.stock.dao.model.stock.Stock;
import com.stock.pojo.vo.BaseModelVO;

public class StockVO extends Stock {

	private BaseModelVO baseModel;

	public BaseModelVO getBaseModel() {
		return baseModel;
	}
	public void setBaseModel(BaseModelVO baseModel) {
		this.baseModel = baseModel;
	}
}
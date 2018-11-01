package com.stock.pojo.vo.stock;

import com.stock.dao.model.stock.StockTurnoverRate;
import com.stock.pojo.vo.BaseModelVO;

public class StockTurnoverRateVO extends StockTurnoverRate {

	private BaseModelVO baseModel;

	public BaseModelVO getBaseModel() {
		return baseModel;
	}
	public void setBaseModel(BaseModelVO baseModel) {
		this.baseModel = baseModel;
	}
}
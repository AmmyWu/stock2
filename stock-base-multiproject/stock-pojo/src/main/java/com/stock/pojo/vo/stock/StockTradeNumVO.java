package com.stock.pojo.vo.stock;

import com.stock.dao.model.stock.StockTradeNum;
import com.stock.pojo.vo.BaseModelVO;

public class StockTradeNumVO extends StockTradeNum {

	private BaseModelVO baseModel;

	public BaseModelVO getBaseModel() {
		return baseModel;
	}
	public void setBaseModel(BaseModelVO baseModel) {
		this.baseModel = baseModel;
	}
}
package com.stock.pojo.vo.stock;

import com.stock.dao.model.stock.StockAccount;
import com.stock.pojo.vo.BaseModelVO;

public class StockAccountVO extends StockAccount {

	private BaseModelVO baseModel;

	public BaseModelVO getBaseModel() {
		return baseModel;
	}
	public void setBaseModel(BaseModelVO baseModel) {
		this.baseModel = baseModel;
	}
}
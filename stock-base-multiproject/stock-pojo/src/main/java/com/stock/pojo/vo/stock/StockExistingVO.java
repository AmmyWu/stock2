package com.stock.pojo.vo.stock;

import com.stock.dao.model.stock.StockExisting;
import com.stock.pojo.vo.BaseModelVO;

public class StockExistingVO extends StockExisting {

	private BaseModelVO baseModel;

	public BaseModelVO getBaseModel() {
		return baseModel;
	}
	public void setBaseModel(BaseModelVO baseModel) {
		this.baseModel = baseModel;
	}
}
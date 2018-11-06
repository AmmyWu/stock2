package com.stock.pojo.vo.stock;

import com.stock.dao.model.stock.StockExisting;
import com.stock.pojo.vo.BaseModelVO;

public class StockExistingVO extends StockExisting {

	private String stockCode;

	private String stockName;

	private BaseModelVO baseModel;

	public BaseModelVO getBaseModel() {
		return baseModel;
	}
	public void setBaseModel(BaseModelVO baseModel) {
		this.baseModel = baseModel;
	}


	public String getStockCode() {
		return stockCode;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
}
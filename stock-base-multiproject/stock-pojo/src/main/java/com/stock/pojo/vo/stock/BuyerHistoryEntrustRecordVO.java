package com.stock.pojo.vo.stock;

import com.stock.dao.model.stock.BuyerHistoryEntrustRecord;
import com.stock.pojo.vo.BaseModelVO;

public class BuyerHistoryEntrustRecordVO extends BuyerHistoryEntrustRecord {

	private BaseModelVO baseModel;

	private String stockCode;
	private String stockName;

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public BaseModelVO getBaseModel() {
		return baseModel;
	}
	public void setBaseModel(BaseModelVO baseModel) {
		this.baseModel = baseModel;
	}
}
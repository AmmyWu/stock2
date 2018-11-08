package com.stock.pojo.vo.stock;

import com.stock.dao.model.stock.SellerHistoryEntrustRecord;
import com.stock.pojo.vo.BaseModelVO;

public class SellerHistoryEntrustRecordVO extends SellerHistoryEntrustRecord {

	private BaseModelVO baseModel;

	private String stockCode;
	private String stockName;

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
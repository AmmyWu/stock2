package com.stock.service.stock;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.stock.StockTradePrice;
import com.stock.pojo.vo.RequestResultVO;

public interface StockTradePriceService {

	public RequestResultVO insert(StockTradePrice  stockTradePrice);

	public RequestResultVO update(StockTradePrice  stockTradePrice);

	public RequestResultVO delete(List<Integer> stockTradePriceIds);

    public Map<String,Object> getByPage(String keys, Integer pageSize,Integer pageNow);

    }



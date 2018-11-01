package com.stock.service.stock;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.stock.StockPriceChangePercent;
import com.stock.pojo.vo.RequestResultVO;

public interface StockPriceChangePercentService {

	public RequestResultVO insert(StockPriceChangePercent  stockPriceChangePercent);

	public RequestResultVO update(StockPriceChangePercent  stockPriceChangePercent);

	public RequestResultVO delete(List<Integer> stockPriceChangePercentIds);

    public Map<String,Object> getByPage(String keys, Integer pageSize,Integer pageNow);

    }



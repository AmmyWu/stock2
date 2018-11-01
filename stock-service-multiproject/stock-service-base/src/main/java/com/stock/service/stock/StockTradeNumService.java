package com.stock.service.stock;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.stock.StockTradeNum;
import com.stock.pojo.vo.RequestResultVO;

public interface StockTradeNumService {

	public RequestResultVO insert(StockTradeNum  stockTradeNum);

	public RequestResultVO update(StockTradeNum  stockTradeNum);

	public RequestResultVO delete(List<Integer> stockTradeNumIds);

    public Map<String,Object> getByPage(String keys, Integer pageSize,Integer pageNow);

    }



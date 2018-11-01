package com.stock.service.stock;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.stock.StockTurnoverRate;
import com.stock.pojo.vo.RequestResultVO;

public interface StockTurnoverRateService {

	public RequestResultVO insert(StockTurnoverRate  stockTurnoverRate);

	public RequestResultVO update(StockTurnoverRate  stockTurnoverRate);

	public RequestResultVO delete(List<Integer> stockTernoverRateIds);

    public Map<String,Object> getByPage(String keys, Integer pageSize,Integer pageNow);

    }



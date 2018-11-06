package com.stock.service.stock;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.stock.StockExisting;
import com.stock.pojo.vo.RequestResultVO;

public interface StockExistingService {

	public RequestResultVO insert(StockExisting  stockExisting);

	public RequestResultVO update(StockExisting  stockExisting);

	public RequestResultVO delete(List<Integer> existingIds);

    public Map<String,Object> getByPage(String keys, Integer pageSize,Integer pageNow);

    public StockExisting findStockExistingByAccountAndStock(int accountId,int stockId);
    }



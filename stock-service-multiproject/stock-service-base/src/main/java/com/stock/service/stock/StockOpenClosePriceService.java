package com.stock.service.stock;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.stock.StockOpenClosePrice;
import com.stock.pojo.vo.RequestResultVO;

public interface StockOpenClosePriceService {

	public RequestResultVO insert(StockOpenClosePrice  stockOpenClosePrice);

	public RequestResultVO update(StockOpenClosePrice  stockOpenClosePrice);

	public RequestResultVO delete(List<Integer> stockOpenClosePriceIds);

    public Map<String,Object> getByPage(String keys, Integer pageSize,Integer pageNow);

    }



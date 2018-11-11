package com.stock.service.stock;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.stock.Stock;
import com.stock.dao.model.stock.StockAccount;
import com.stock.pojo.vo.RequestResultVO;

public interface StockService {

	public RequestResultVO insert(Stock  stock);

	public RequestResultVO update(Stock  stock);

	public RequestResultVO delete(List<Integer> stockIds);

    public Map<String,Object> getByPage(String keys, Integer pageSize,Integer pageNow);

    public Integer check(String id,String name);

    public Integer findKeyByName(String name);

    public Integer findKeyByCode(String code);

    public Stock findByCode(String code);
}



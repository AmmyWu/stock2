package com.stock.service.stock;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.stock.StockAccount;
import com.stock.pojo.vo.RequestResultVO;

public interface StockAccountService {

	public RequestResultVO insert(StockAccount  stockAccount);

	public RequestResultVO update(StockAccount  stockAccount);

	public RequestResultVO delete(List<Integer> stockAccountIds);

    public Map<String,Object> getByPage(String keys, Integer pageSize,Integer pageNow);

	public StockAccount findStockAccountByUser();

	public StockAccount findStockAccountByUser(String userId);
    }



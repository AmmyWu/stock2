package com.stock.service.stock;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.stock.BuyerSellerHistoryEntrustRecord;
import com.stock.dao.model.stock.SellerEntrustPriceQueue;
import com.stock.pojo.vo.RequestResultVO;

public interface BuyerSellerHistoryEntrustRecordService {

	public RequestResultVO insert(BuyerSellerHistoryEntrustRecord  buyerSellerHistoryEntrustRecord);

	public RequestResultVO update(BuyerSellerHistoryEntrustRecord  buyerSellerHistoryEntrustRecord);

	public RequestResultVO delete(List<Integer> buyerSellerHistoryEntrustRecordIds);

    public Map<String,Object> getByPage(String keys, Integer pageSize,Integer pageNow);



    }



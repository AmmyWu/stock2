package com.stock.service.stock;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.stock.BuyerHistoryEntrustRecord;
import com.stock.pojo.vo.RequestResultVO;

public interface BuyerHistoryEntrustRecordService {

	public RequestResultVO insert(BuyerHistoryEntrustRecord  buyerHistoryEntrustRecord);

	public RequestResultVO update(BuyerHistoryEntrustRecord  buyerHistoryEntrustRecord);

	public RequestResultVO delete(List<Integer> buyerHistoryEntrustRecordIds);

    public Map<String,Object> getByPage(String keys, Integer pageSize,Integer pageNow);

    }



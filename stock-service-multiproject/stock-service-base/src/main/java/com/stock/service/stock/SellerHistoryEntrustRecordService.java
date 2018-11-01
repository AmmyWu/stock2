package com.stock.service.stock;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.stock.SellerHistoryEntrustRecord;
import com.stock.pojo.vo.RequestResultVO;

public interface SellerHistoryEntrustRecordService {

	public RequestResultVO insert(SellerHistoryEntrustRecord  sellerHistoryEntrustRecord);

	public RequestResultVO update(SellerHistoryEntrustRecord  sellerHistoryEntrustRecord);

	public RequestResultVO delete(List<Integer> sellerHistoryEntrustRecordIds);

    public Map<String,Object> getByPage(String keys, Integer pageSize,Integer pageNow);

    }



package com.stock.service.stock;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.stock.BuyerEntrustPriceQueue;
import com.stock.pojo.vo.RequestResultVO;

public interface BuyerEntrustPriceQueueService {

	public RequestResultVO insert(BuyerEntrustPriceQueue  buyerEntrustPriceQueue);

	public RequestResultVO update(BuyerEntrustPriceQueue  buyerEntrustPriceQueue);

	public RequestResultVO delete(List<Integer> buyerEntrustPriceQueueIds);

    public Map<String,Object> getByPage(String keys, Integer pageSize,Integer pageNow);

    public BuyerEntrustPriceQueue findByBuyerEntrustPrice(int buyerEntrustPriceId);

    }



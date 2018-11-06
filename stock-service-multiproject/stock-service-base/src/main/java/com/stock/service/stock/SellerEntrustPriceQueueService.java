package com.stock.service.stock;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.stock.BuyerEntrustPriceQueue;
import com.stock.dao.model.stock.SellerEntrustPriceQueue;
import com.stock.pojo.vo.RequestResultVO;

public interface SellerEntrustPriceQueueService {

	public RequestResultVO insert(SellerEntrustPriceQueue  sellerEntrustPriceQueue);

	public RequestResultVO update(SellerEntrustPriceQueue  sellerEntrustPriceQueue);

	public RequestResultVO delete(List<Integer> sellerEntrustPriceQueueIds);

    public Map<String,Object> getByPage(String keys, Integer pageSize,Integer pageNow);

	public SellerEntrustPriceQueue findBySllerEntrustPrice(int sellerEntrustPriceId);

    }



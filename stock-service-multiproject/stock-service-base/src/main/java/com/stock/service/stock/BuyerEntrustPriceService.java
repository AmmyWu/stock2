package com.stock.service.stock;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.stock.BuyerEntrustPrice;
import com.stock.pojo.vo.RequestResultVO;

public interface BuyerEntrustPriceService {

	public RequestResultVO insert(BuyerEntrustPrice  buyerEntrustPrice);

	public RequestResultVO update(BuyerEntrustPrice  buyerEntrustPrice);

	public RequestResultVO delete(List<Integer> buyerEntrustPriceIds);

    public Map<String,Object> getByPage(String keys, Integer pageSize,Integer pageNow);

    public BuyerEntrustPrice findBuyerEntrustPriceByPriceAndStock(String priceJson);

    public BuyerEntrustPrice findBuyerEntrustPriceByPriceAndStock(Integer stock_id,Double stock_price);

    public Integer myinsert(Integer stock_id,Integer stock_count,Double stock_price);
}



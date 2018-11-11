package com.stock.service.stock;

import java.util.List;
import java.util.Map;

import com.stock.dao.model.stock.BuyerEntrustPrice;
import com.stock.dao.model.stock.SellerEntrustPrice;
import com.stock.pojo.vo.RequestResultVO;

public interface SellerEntrustPriceService {

	public RequestResultVO insert(SellerEntrustPrice  sellerEntrustPrice);

	public RequestResultVO update(SellerEntrustPrice  sellerEntrustPrice);

	public RequestResultVO delete(List<Integer> sellerEntrustPriceIds);

    public Map<String,Object> getByPage(String keys, Integer pageSize,Integer pageNow);

    public RequestResultVO sell(String price,String priceQueue);

    public SellerEntrustPrice findSellerEntrustPriceByPriceAndStock(String priceJson);

//    public SellerEntrustPrice findSellerEntrustPriceByPriceAndStock(String priceJson);

    public Integer findPriceVaild(Integer stock_id, Double stock_price);

    public void mydelete(int sell_id);
}



package com.stock.webapp.base.controller.stock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.stock.dao.model.stock.*;
import com.stock.service.stock.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.stock.common.exceptions.BizException;
import com.stock.common.util.CommonUtils;
import com.stock.common.util.JsonFastUtil;
import com.stock.pojo.vo.RequestResultVO;
import com.stock.service.sys.utils.HttpResponseConstants.Public;

@Controller
@RequestMapping(value = "/buyerEntrustPrice")
public class BuyerEntrustPriceController {

    @Autowired
    private BuyerEntrustPriceService buyerEntrustPriceService;
    @Autowired
    private SellerEntrustPriceService sellerEntrustPriceService;
    @Autowired
    private BuyerEntrustPriceQueueService buyerEntrustPriceQueueService;
    @Autowired
    private SellerEntrustPriceQueueService sellerEntrustPriceQueueService;
    @Autowired
    private StockAccountService stockAccountService;
    @Autowired
    private StockExistingService stockExistingService;
    @Autowired
    private BuyerSellerHistoryEntrustRecordService buyerSellerHistoryEntrustRecordService;
    /**
     * 新增
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/insert.do")
    public @ResponseBody
    RequestResultVO insert(HttpServletRequest request) {
        String buyerEntrustPriceString = request.getParameter("buyerEntrustPrice");
        BuyerEntrustPrice buyerEntrustPrice = null;
        try {
            buyerEntrustPrice = JsonFastUtil.parseObject(buyerEntrustPriceString, BuyerEntrustPrice.class);
        } catch (Exception e) {
            throw new BizException(Public.ERROR_700);
        }
        return buyerEntrustPriceService.insert(buyerEntrustPrice);
    }

    /**
     * 修改
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/update.do")
    public @ResponseBody
    RequestResultVO update(HttpServletRequest request) {
        String buyerEntrustPriceString = request.getParameter("buyerEntrustPrice");
        BuyerEntrustPrice buyerEntrustPrice = null;
        try {
            buyerEntrustPrice = JsonFastUtil.parseObject(buyerEntrustPriceString, BuyerEntrustPrice.class);
        } catch (Exception e) {
            throw new BizException(Public.ERROR_700);
        }
        return buyerEntrustPriceService.update(buyerEntrustPrice);
    }

    /**
     * 删除
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete.do")
    public @ResponseBody
    RequestResultVO delete(HttpServletRequest request) {
        String buyerEntrustPriceIdsString = request.getParameter("buyerEntrustPriceIds");
        List<Integer> buyerEntrustPriceIds;
        try {
            buyerEntrustPriceIds = CommonUtils.idsArrayToList(buyerEntrustPriceIdsString);
        } catch (Exception e) {
            throw new BizException(Public.ERROR_700);
        }
        return buyerEntrustPriceService.delete(buyerEntrustPriceIds);
    }

    /**
     * 分页查询
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getByPage.do")
    public @ResponseBody
    Object getByPage(HttpServletRequest request) {
        String keys = request.getParameter("keys");
        Integer length = Integer.parseInt(request.getParameter("length"));
        Integer start = Integer.parseInt(request.getParameter("start"));
        return buyerEntrustPriceService.getByPage(keys, length, start);
    }
    /**
     * 实时买入行为
     */
    @RequestMapping(value = "/buy_stock")
    public @ResponseBody String buy(Integer stock_id,Integer stock_count,Double stock_price,Integer user_id){
        int stock_count_copy = stock_count;
        int price_id;
        int sell_id = sellerEntrustPriceService.findPriceVaild(stock_id,stock_price);
        if(sell_id!=-1){
            List<SellerEntrustPriceQueue> sellStockList  = sellerEntrustPriceQueueService.findqueue(sell_id);
            sellStockList.sort(new Comparator<SellerEntrustPriceQueue>() {
                @Override
                public int compare(SellerEntrustPriceQueue o1, SellerEntrustPriceQueue o2) {
                    if(o1.getSellerEntrustPriceQueueId()<o2.getSellerEntrustPriceQueueId()){
                        return 1;
                    }
                    return -1;
                }
            });
            for(int i=0;i<sellStockList.size();i++){
                int count = sellStockList.get(i).getEntrustNum();
                stock_count -=count;
                if(stock_count <= 0){
                    /**
                     * 表示这个买家申请的所有股票都买到了
                     */
                    StockAccount stockAccount =stockAccountService.findStockAccountByUser(user_id.toString());
                    StockExisting stockExisting= stockExistingService.findStockExistingByAccountAndStock(stockAccount.getStockAccountId(),stock_id);
                    if(stockExisting!=null){ //说明他本来就有这个股票,更新他的成本价和持股数量即可
                        double stockValue = stockExisting.getStockOwnNum()*stockExisting.getCostPrice();
                        Integer nowNum = stockExisting.getStockOwnNum()+ stock_count_copy;
//                        Integer sellWaitNum = stockExisting.getStockAvailableSellNum() - sellerEntrustPriceQueue.getEntrustNum();
                        double buyNum = stock_price * stock_count_copy;
                        double avg_price = (stockValue + buyNum)/nowNum;
                        stockExisting.setStockOwnNum(nowNum);
                        stockExisting.setCostPrice(avg_price);
                        stockExistingService.update(stockExisting);
                    }else{
                        stockExisting = new StockExisting();
                        stockExisting.setStockAvailableSellNum(stock_count);
                        stockExisting.setStockOwnNum(stock_count);
                        stockExisting.setCostPrice(stock_price);
                        stockExisting.setStockId(stock_id);
                        stockExisting.setStockAccountId(stockAccount.getStockAccountId());
                        stockExistingService.insert(stockExisting);
                    }
                    return "success";
                }
                sellerEntrustPriceQueueService.mydelete(user_id,sellStockList.get(i));
            }
            /**
             * 表示所有的等待队列都已经买完了，但是还有想买的股票，只能继续挂单
             */
            price_id = buyerEntrustPriceService.myinsert(stock_id,stock_count,stock_price);
            BuyerEntrustPriceQueue buyerEntrustPriceQueue = new BuyerEntrustPriceQueue();
            buyerEntrustPriceQueue.setBuyerEntrustPriceId(price_id);
            buyerEntrustPriceQueue.setUserId(user_id);
            buyerEntrustPriceQueue.setEntrustNum(stock_count);
            buyerEntrustPriceQueue.setEntrustDate(new Date());
            buyerEntrustPriceQueueService.insert(buyerEntrustPriceQueue);
        }else{
            BuyerEntrustPrice stock = buyerEntrustPriceService.findBuyerEntrustPriceByPriceAndStock(stock_id,stock_price);
            if(stock != null){
                price_id = stock.getBuyerEntrustPriceId();
                stock.setTotalEntrustNum(stock.getTotalEntrustNum()+stock_count);
                buyerEntrustPriceService.update(stock);
            }else{
                price_id = buyerEntrustPriceService.myinsert(stock_id,stock_count,stock_price);
            }
            BuyerEntrustPriceQueue buyerEntrustPriceQueue = new BuyerEntrustPriceQueue();
            buyerEntrustPriceQueue.setBuyerEntrustPriceId(price_id);
            buyerEntrustPriceQueue.setUserId(user_id);
            buyerEntrustPriceQueue.setEntrustNum(stock_count);
            buyerEntrustPriceQueue.setEntrustDate(new Date());
            buyerEntrustPriceQueueService.insert(buyerEntrustPriceQueue);
        }
        return "wait";
    }
}

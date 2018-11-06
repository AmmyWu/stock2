package com.stock.service.stock.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.stock.dao.mapper.stock.BuyerEntrustPriceMapper;
import com.stock.dao.model.stock.*;
import com.stock.dao.model.sys.SysUser;
import com.stock.service.stock.*;
import com.stock.service.sys.SysUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.common.exceptions.BizException;
import com.stock.common.tool.log.ErrorLoggers;
import com.stock.common.tool.log.LogUtil;
import com.stock.dao.page.Page;
import com.stock.service.common.util.DateJsonValueProcessor;
import com.stock.service.sys.utils.HttpResponseConstants.Public;
import com.stock.pojo.vo.RequestResultVO;
import com.stock.service.common.DataAuthorizeService;
import com.stock.service.sys.utils.ResultBuilder;
import com.stock.service.sys.CommonService;
import com.stock.dao.mapper.stock.SellerEntrustPriceMapper;

import com.stock.pojo.vo.stock.SellerEntrustPriceVO;

@Service
public class SellerEntrustPriceServiceImpl implements SellerEntrustPriceService {

    @Autowired
    private SellerEntrustPriceMapper sellerEntrustPriceMapper;

    @Autowired
    private DataAuthorizeService dataAuthorizeService;

    @Autowired
    private BuyerEntrustPriceMapper buyerEntrustPriceMapper;

    @Autowired
    private StockAccountService stockAccountService;

    @Autowired
    private BuyerEntrustPriceService buyerEntrustPriceService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private StockExistingService stockExistingService;

    @Autowired
    private BuyerEntrustPriceQueueService buyerEntrustPriceQueueService;
    private CommonService<SellerEntrustPrice, SellerEntrustPriceMapper, SellerEntrustPriceExample> commonService;

    //注入commonService
    @Resource(name = "commonService")
    public void setCommonService(CommonService<SellerEntrustPrice, SellerEntrustPriceMapper, SellerEntrustPriceExample> commonService) {
        this.commonService = commonService;
    }

    @Override
    public RequestResultVO insert(SellerEntrustPrice sellerEntrustPrice) {
        if (sellerEntrustPrice == null) {
            throw new BizException(Public.ERROR_700);
        }
        dataAuthorizeService.addDataAuthorizeInfo(sellerEntrustPrice, "insert");
        sellerEntrustPriceMapper.insert(sellerEntrustPrice);
        return ResultBuilder.buildSuccessResult(Public.SUCCESS_200, "");
    }

    @Override
    public RequestResultVO update(SellerEntrustPrice sellerEntrustPrice) {
        if (sellerEntrustPrice == null || sellerEntrustPrice.getSellerEntrustPriceId() == null) {
            throw new BizException(Public.ERROR_700);
        }
        dataAuthorizeService.addDataAuthorizeInfo(sellerEntrustPrice, "update");
        sellerEntrustPriceMapper.updateByPrimaryKeySelective(sellerEntrustPrice);
        return ResultBuilder.buildSuccessResult(Public.SUCCESS_300, "");
    }

    @Override
    public RequestResultVO delete(List<Integer> sellerEntrustPriceIds) {
        if (sellerEntrustPriceIds == null || sellerEntrustPriceIds.size() == 0) {
            throw new BizException(Public.ERROR_700);
        }
        SellerEntrustPriceExample sellerEntrustPriceExample = new SellerEntrustPriceExample();
        sellerEntrustPriceExample.createCriteria().andSellerEntrustPriceIdIn(sellerEntrustPriceIds);
        sellerEntrustPriceMapper.deleteByExample(sellerEntrustPriceExample);
        return ResultBuilder.buildSuccessResult(Public.SUCCESS_400, "");
    }

    @Override
    public Map<String, Object> getByPage(String keys, Integer pageSize,
                                         Integer pageNow) {
        SellerEntrustPriceExample sellerEntrustPriceExample = new SellerEntrustPriceExample();
        this.setCriteria(keys, sellerEntrustPriceExample);
        int totalrecords = sellerEntrustPriceMapper.countByExample(sellerEntrustPriceExample);

        Page page = new Page();
        page.setBegin(pageNow);
        page.setLength(pageSize);
        sellerEntrustPriceExample.setOrderByClause("sellerEntrustPriceId desc");
        sellerEntrustPriceExample.setPage(page);
        List<SellerEntrustPrice> sellerEntrustPrices = sellerEntrustPriceMapper.selectByExample(sellerEntrustPriceExample);

        Map<String, Object> map = new HashMap<String, Object>();
        JsonConfig config = new JsonConfig();
        config.setIgnoreDefaultExcludes(false);
        config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
        try {
            map.put("aaData", JSONArray.fromObject(this.creatVos(sellerEntrustPrices), config));
        } catch (Exception e) {
            LogUtil.error(ErrorLoggers.ERROR_LOGGER, e.getMessage());
            throw new BizException(Public.ERROR_100);
        }
        map.put("recordsTotal", totalrecords);
        map.put("recordsFiltered", totalrecords);

        return map;
    }

    @Override
    public RequestResultVO sell(String price, String priceQueue) {
        BuyerEntrustPrice buyerEntrustPrice = buyerEntrustPriceService.findBuyerEntrustPriceByPriceAndStock(price);
        BuyerEntrustPriceQueue buyerEntrustPriceQueue = buyerEntrustPriceQueueService.findByBuyerEntrustPrice(buyerEntrustPrice.getBuyerEntrustPriceId());

        StockAccount buyerStockAccount;
        StockAccount sellerStockAccount;

        StockExisting buyerStockExisting;
        StockExisting sellerStockExisting;
        if (buyerEntrustPriceQueue != null) {
            int stockId = buyerEntrustPrice.getStockId();
            buyerStockAccount = stockAccountService.findStockAccountByUser(String.valueOf(buyerEntrustPriceQueue.getUserId()));
            sellerStockAccount = stockAccountService.findStockAccountByUser();

            buyerStockExisting = stockExistingService.findStockExistingByAccountAndStock(buyerStockAccount.getStockAccountId(), stockId);
            sellerStockExisting = stockExistingService.findStockExistingByAccountAndStock(sellerStockAccount.getStockAccountId(), stockId);


        }
    }

    private void setCriteria(String keys, SellerEntrustPriceExample sellerEntrustPriceExample) {
        if (keys == null || "{}".equals(keys))
            return;
        //JSONObject jKeys = JSONObject.fromObject(keys);
        //Criteria criteria = sellerEntrustPriceExample.createCriteria();

    }

    private List<SellerEntrustPriceVO> creatVos(List<SellerEntrustPrice> sellerEntrustPrices) throws Exception {
        List<SellerEntrustPriceVO> sellerEntrustPriceVOs = new ArrayList<SellerEntrustPriceVO>();
        for (SellerEntrustPrice sellerEntrustPrice : sellerEntrustPrices) {
            SellerEntrustPriceVO sellerEntrustPriceVO = new SellerEntrustPriceVO();
            BeanUtils.copyProperties(sellerEntrustPrice, sellerEntrustPriceVO);
            commonService.addBaseModel(sellerEntrustPrice, sellerEntrustPriceVO);
            sellerEntrustPriceVOs.add(sellerEntrustPriceVO);
        }
        return sellerEntrustPriceVOs;
    }

    public void
}





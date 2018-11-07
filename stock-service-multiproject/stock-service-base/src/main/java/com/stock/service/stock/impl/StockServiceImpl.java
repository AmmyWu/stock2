package com.stock.service.stock.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.stock.dao.model.stock.StockAccount;
import net.sf.json.JSONArray;
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
import com.stock.dao.mapper.stock.StockMapper;
import com.stock.dao.model.stock.Stock;
import com.stock.dao.model.stock.StockExample;

import com.stock.pojo.vo.stock.StockVO;
import com.stock.service.stock.StockService;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private DataAuthorizeService dataAuthorizeService;

    private CommonService<Stock, StockMapper, StockExample> commonService;

    //注入commonService
    @Resource(name = "commonService")
    public void setCommonService(CommonService<Stock, StockMapper, StockExample> commonService) {
        this.commonService = commonService;
    }

    @Override
    public RequestResultVO insert(Stock stock) {
        if (stock == null) {
            throw new BizException(Public.ERROR_700);
        }
        dataAuthorizeService.addDataAuthorizeInfo(stock, "insert");
        stockMapper.insert(stock);
        return ResultBuilder.buildSuccessResult(Public.SUCCESS_200, "");
    }

    @Override
    public RequestResultVO update(Stock stock) {
        if (stock == null || stock.getStockId() == null) {
            throw new BizException(Public.ERROR_700);
        }
        dataAuthorizeService.addDataAuthorizeInfo(stock, "update");
        stockMapper.updateByPrimaryKeySelective(stock);
        return ResultBuilder.buildSuccessResult(Public.SUCCESS_300, "");
    }

    @Override
    public RequestResultVO delete(List<Integer> stockIds) {
        if (stockIds == null || stockIds.size() == 0) {
            throw new BizException(Public.ERROR_700);
        }
        StockExample stockExample = new StockExample();
        stockExample.createCriteria().andStockIdIn(stockIds);
        stockMapper.deleteByExample(stockExample);
        return ResultBuilder.buildSuccessResult(Public.SUCCESS_400, "");
    }

    @Override
    public Map<String, Object> getByPage(String keys, Integer pageSize,
                                         Integer pageNow) {
        StockExample stockExample = new StockExample();
        this.setCriteria(keys, stockExample);
        int totalrecords = stockMapper.countByExample(stockExample);

        Page page = new Page();
        page.setBegin(pageNow);
        page.setLength(pageSize);
        stockExample.setOrderByClause("stockId desc");
        stockExample.setPage(page);
        List<Stock> stocks = stockMapper.selectByExample(stockExample);

        Map<String, Object> map = new HashMap<String, Object>();
        JsonConfig config = new JsonConfig();
        config.setIgnoreDefaultExcludes(false);
        config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
        try {
            map.put("aaData", JSONArray.fromObject(this.creatVos(stocks), config));
        } catch (Exception e) {
            LogUtil.error(ErrorLoggers.ERROR_LOGGER, e.getMessage());
            throw new BizException(Public.ERROR_100);
        }
        map.put("recordsTotal", totalrecords);
        map.put("recordsFiltered", totalrecords);

        return map;
    }

    @Override
    public Stock findByCode(String code) {
        StockExample stockExample = new StockExample();
        StockExample.Criteria criteria = stockExample.createCriteria();
        criteria.andStockCodeEqualTo(code);
        List<Stock> stocks = stockMapper.selectByExample(stockExample);
        if (stocks.size() != 0) {
            return stocks.get(0);
        }
        return null;
    }


    private void setCriteria(String keys, StockExample stockExample) {
        if (keys == null || "{}".equals(keys))
            return;
        //JSONObject jKeys = JSONObject.fromObject(keys);
        //Criteria criteria = stockExample.createCriteria();

    }

    private List<StockVO> creatVos(List<Stock> stocks) throws Exception {
        List<StockVO> stockVOs = new ArrayList<StockVO>();
        for (Stock stock : stocks) {
            StockVO stockVO = new StockVO();
            BeanUtils.copyProperties(stock, stockVO);
            commonService.addBaseModel(stock, stockVO);
            stockVOs.add(stockVO);
        }
        return stockVOs;
    }


}





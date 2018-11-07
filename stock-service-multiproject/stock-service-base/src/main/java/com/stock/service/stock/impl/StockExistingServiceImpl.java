package com.stock.service.stock.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.stock.dao.mapper.stock.StockAccountMapper;
import com.stock.dao.mapper.stock.StockMapper;
import com.stock.dao.model.stock.Stock;
import com.stock.dao.model.stock.StockAccount;
import com.stock.dao.model.sys.SysUser;
import com.stock.service.stock.StockAccountService;
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
import com.stock.dao.mapper.stock.StockExistingMapper;
import com.stock.dao.model.stock.StockExisting;
import com.stock.dao.model.stock.StockExistingExample;

import com.stock.pojo.vo.stock.StockExistingVO;
import com.stock.service.stock.StockExistingService;

@Service
public class StockExistingServiceImpl implements StockExistingService {

    @Autowired
    private StockExistingMapper stockExistingMapper;

    @Autowired
    private DataAuthorizeService dataAuthorizeService;

    @Autowired
    StockAccountService stockAccountService;

    @Autowired
    StockMapper stockMapper;

    private CommonService<StockExisting, StockExistingMapper, StockExistingExample> commonService;


    //注入commonService
    @Resource(name = "commonService")
    public void setCommonService(CommonService<StockExisting, StockExistingMapper, StockExistingExample> commonService) {
        this.commonService = commonService;
    }

    @Override
    public RequestResultVO insert(StockExisting stockExisting) {
        if (stockExisting == null) {
            throw new BizException(Public.ERROR_700);
        }
        dataAuthorizeService.addDataAuthorizeInfo(stockExisting, "insert");
        stockExistingMapper.insert(stockExisting);
        return ResultBuilder.buildSuccessResult(Public.SUCCESS_200, "");
    }

    @Override
    public RequestResultVO update(StockExisting stockExisting) {
        if (stockExisting == null || stockExisting.getExistingId() == null) {
            throw new BizException(Public.ERROR_700);
        }
        dataAuthorizeService.addDataAuthorizeInfo(stockExisting, "update");
        stockExistingMapper.updateByPrimaryKeySelective(stockExisting);
        return ResultBuilder.buildSuccessResult(Public.SUCCESS_300, "");
    }

    @Override
    public RequestResultVO delete(List<Integer> existingIds) {
        if (existingIds == null || existingIds.size() == 0) {
            throw new BizException(Public.ERROR_700);
        }
        StockExistingExample stockExistingExample = new StockExistingExample();
        stockExistingExample.createCriteria().andExistingIdIn(existingIds);
        stockExistingMapper.deleteByExample(stockExistingExample);
        return ResultBuilder.buildSuccessResult(Public.SUCCESS_400, "");
    }

    @Override
    public Map<String, Object> getByPage(String keys, Integer pageSize,
                                         Integer pageNow) {
        StockExistingExample stockExistingExample = new StockExistingExample();
        this.setCriteria(keys, stockExistingExample);
        int totalrecords = stockExistingMapper.countByExample(stockExistingExample);

        Page page = new Page();
        page.setBegin(pageNow);
        page.setLength(pageSize);
        stockExistingExample.setOrderByClause("existing_id desc");
        stockExistingExample.setPage(page);
        List<StockExisting> stockExistings = stockExistingMapper.selectByExample(stockExistingExample);
        Map<String, Object> map = new HashMap<String, Object>();
        JsonConfig config = new JsonConfig();

        config.setIgnoreDefaultExcludes(false);
        config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
        try {
            map.put("aaData", JSONArray.fromObject(this.creatVos(stockExistings), config));
        } catch (Exception e) {
            LogUtil.error(ErrorLoggers.ERROR_LOGGER, e.getMessage());
            throw new BizException(Public.ERROR_100);
        }
        map.put("recordsTotal", totalrecords);
        map.put("recordsFiltered", totalrecords);

        return map;
    }

    @Override
    public StockExisting findStockExistingByAccountAndStock(int accountId, int stockId) {
        StockExistingExample stockExistingExample = new StockExistingExample();
        StockExistingExample.Criteria criteria = stockExistingExample.createCriteria();
        criteria.andStockAccountIdEqualTo(accountId);
        criteria.andStockIdEqualTo(stockId);
        List<StockExisting> stockExistings = stockExistingMapper.selectByExample(stockExistingExample);
        if (stockExistings.size() != 0)
            return stockExistings.get(0);
        else
            return null;
    }

    private void setCriteria(String keys, StockExistingExample stockExistingExample) {
        if (keys == null || "{}".equals(keys))
            return;
        JSONObject jKeys = JSONObject.fromObject(keys);
        StockExistingExample.Criteria criteria = stockExistingExample.createCriteria();

        StockAccount stockAccount = stockAccountService.findStockAccountByUser();
        criteria.andStockAccountIdEqualTo(stockAccount.getStockAccountId());
    }

    private List<StockExistingVO> creatVos(List<StockExisting> stockExistings) throws Exception {
        List<StockExistingVO> stockExistingVOs = new ArrayList<StockExistingVO>();
        for (StockExisting stockExisting : stockExistings) {
            StockExistingVO stockExistingVO = new StockExistingVO();
            BeanUtils.copyProperties(stockExisting, stockExistingVO);
            Stock stock = stockMapper.selectByPrimaryKey(stockExistingVO.getStockId());
            stockExistingVO.setStockCode(stock.getStockCode());
            stockExistingVO.setStockName(stock.getStockName());
            commonService.addBaseModel(stockExisting, stockExistingVO);
            stockExistingVOs.add(stockExistingVO);
        }
        return stockExistingVOs;
    }
}





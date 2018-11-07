package com.stock.service.stock.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.stock.dao.mapper.stock.BuyerEntrustPriceQueueMapper;
import com.stock.dao.model.stock.BuyerEntrustPriceQueue;
import com.stock.dao.model.stock.BuyerEntrustPriceQueueExample;

import com.stock.pojo.vo.stock.BuyerEntrustPriceQueueVO;
import com.stock.service.stock.BuyerEntrustPriceQueueService;

@Service
public class BuyerEntrustPriceQueueServiceImpl implements BuyerEntrustPriceQueueService {

    @Autowired
    private BuyerEntrustPriceQueueMapper buyerEntrustPriceQueueMapper;

    @Autowired
    private DataAuthorizeService dataAuthorizeService;

    private CommonService<BuyerEntrustPriceQueue, BuyerEntrustPriceQueueMapper, BuyerEntrustPriceQueueExample> commonService;

    //注入commonService
    @Resource(name = "commonService")
    public void setCommonService(CommonService<BuyerEntrustPriceQueue, BuyerEntrustPriceQueueMapper, BuyerEntrustPriceQueueExample> commonService) {
        this.commonService = commonService;
    }

    @Override
    public RequestResultVO insert(BuyerEntrustPriceQueue buyerEntrustPriceQueue) {
        if (buyerEntrustPriceQueue == null) {
            throw new BizException(Public.ERROR_700);
        }
        dataAuthorizeService.addDataAuthorizeInfo(buyerEntrustPriceQueue, "insert");
        buyerEntrustPriceQueueMapper.insert(buyerEntrustPriceQueue);
        return ResultBuilder.buildSuccessResult(Public.SUCCESS_200, "");
    }

    @Override
    public RequestResultVO update(BuyerEntrustPriceQueue buyerEntrustPriceQueue) {
        if (buyerEntrustPriceQueue == null || buyerEntrustPriceQueue.getBuyerEntrustPriceQueueId() == null) {
            throw new BizException(Public.ERROR_700);
        }
        dataAuthorizeService.addDataAuthorizeInfo(buyerEntrustPriceQueue, "update");
        buyerEntrustPriceQueueMapper.updateByPrimaryKeySelective(buyerEntrustPriceQueue);
        return ResultBuilder.buildSuccessResult(Public.SUCCESS_300, "");
    }

    @Override
    public RequestResultVO delete(List<Integer> buyerEntrustPriceQueueIds) {
        if (buyerEntrustPriceQueueIds == null || buyerEntrustPriceQueueIds.size() == 0) {
            throw new BizException(Public.ERROR_700);
        }
        BuyerEntrustPriceQueueExample buyerEntrustPriceQueueExample = new BuyerEntrustPriceQueueExample();
        buyerEntrustPriceQueueExample.createCriteria().andBuyerEntrustPriceQueueIdIn(buyerEntrustPriceQueueIds);
        buyerEntrustPriceQueueMapper.deleteByExample(buyerEntrustPriceQueueExample);
        return ResultBuilder.buildSuccessResult(Public.SUCCESS_400, "");
    }

    @Override
    public Map<String, Object> getByPage(String keys, Integer pageSize,
                                         Integer pageNow) {
        BuyerEntrustPriceQueueExample buyerEntrustPriceQueueExample = new BuyerEntrustPriceQueueExample();
        this.setCriteria(keys, buyerEntrustPriceQueueExample);
        int totalrecords = buyerEntrustPriceQueueMapper.countByExample(buyerEntrustPriceQueueExample);

        Page page = new Page();
        page.setBegin(pageNow);
        page.setLength(pageSize);
        buyerEntrustPriceQueueExample.setOrderByClause("buyerEntrustPriceQueueId desc");
        buyerEntrustPriceQueueExample.setPage(page);
        List<BuyerEntrustPriceQueue> buyerEntrustPriceQueues = buyerEntrustPriceQueueMapper.selectByExample(buyerEntrustPriceQueueExample);

        Map<String, Object> map = new HashMap<String, Object>();
        JsonConfig config = new JsonConfig();
        config.setIgnoreDefaultExcludes(false);
        config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
        try {
            map.put("aaData", JSONArray.fromObject(this.creatVos(buyerEntrustPriceQueues), config));
        } catch (Exception e) {
            LogUtil.error(ErrorLoggers.ERROR_LOGGER, e.getMessage());
            throw new BizException(Public.ERROR_100);
        }
        map.put("recordsTotal", totalrecords);
        map.put("recordsFiltered", totalrecords);

        return map;
    }

    @Override
    public List<BuyerEntrustPriceQueue> findByBuyerEntrustPrice(int buyerEntrustPriceId) {
        BuyerEntrustPriceQueueExample buyerEntrustPriceQueueExample = new BuyerEntrustPriceQueueExample();
        BuyerEntrustPriceQueueExample.Criteria criteria = buyerEntrustPriceQueueExample.createCriteria();
        criteria.andBuyerEntrustPriceIdEqualTo(buyerEntrustPriceId);
        List<BuyerEntrustPriceQueue> buyerEntrustPriceQueues = buyerEntrustPriceQueueMapper.selectByExample(buyerEntrustPriceQueueExample);
        return buyerEntrustPriceQueues;
    }

    private void setCriteria(String keys, BuyerEntrustPriceQueueExample buyerEntrustPriceQueueExample) {
        if (keys == null || "{}".equals(keys))
            return;
        //JSONObject jKeys = JSONObject.fromObject(keys);
        //Criteria criteria = buyerEntrustPriceQueueExample.createCriteria();

    }

    private List<BuyerEntrustPriceQueueVO> creatVos(List<BuyerEntrustPriceQueue> buyerEntrustPriceQueues) throws Exception {
        List<BuyerEntrustPriceQueueVO> buyerEntrustPriceQueueVOs = new ArrayList<BuyerEntrustPriceQueueVO>();
        for (BuyerEntrustPriceQueue buyerEntrustPriceQueue : buyerEntrustPriceQueues) {
            BuyerEntrustPriceQueueVO buyerEntrustPriceQueueVO = new BuyerEntrustPriceQueueVO();
            BeanUtils.copyProperties(buyerEntrustPriceQueue, buyerEntrustPriceQueueVO);
            commonService.addBaseModel(buyerEntrustPriceQueue, buyerEntrustPriceQueueVO);
            buyerEntrustPriceQueueVOs.add(buyerEntrustPriceQueueVO);
        }
        return buyerEntrustPriceQueueVOs;
    }
}





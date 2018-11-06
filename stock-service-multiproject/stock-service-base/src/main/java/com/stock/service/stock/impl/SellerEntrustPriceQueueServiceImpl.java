package com.stock.service.stock.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.stock.dao.model.stock.BuyerEntrustPriceQueue;
import com.stock.dao.model.stock.BuyerEntrustPriceQueueExample;
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
import com.stock.dao.mapper.stock.SellerEntrustPriceQueueMapper;
import com.stock.dao.model.stock.SellerEntrustPriceQueue;
import com.stock.dao.model.stock.SellerEntrustPriceQueueExample;

import com.stock.pojo.vo.stock.SellerEntrustPriceQueueVO;
import com.stock.service.stock.SellerEntrustPriceQueueService;

@Service
public class SellerEntrustPriceQueueServiceImpl implements SellerEntrustPriceQueueService {

    @Autowired
    private SellerEntrustPriceQueueMapper sellerEntrustPriceQueueMapper;

    @Autowired
    private DataAuthorizeService dataAuthorizeService;

    private CommonService<SellerEntrustPriceQueue, SellerEntrustPriceQueueMapper, SellerEntrustPriceQueueExample> commonService;

    //注入commonService
    @Resource(name = "commonService")
    public void setCommonService(CommonService<SellerEntrustPriceQueue, SellerEntrustPriceQueueMapper, SellerEntrustPriceQueueExample> commonService) {
        this.commonService = commonService;
    }

    @Override
    public RequestResultVO insert(SellerEntrustPriceQueue sellerEntrustPriceQueue) {
        if (sellerEntrustPriceQueue == null) {
            throw new BizException(Public.ERROR_700);
        }
        dataAuthorizeService.addDataAuthorizeInfo(sellerEntrustPriceQueue, "insert");
        sellerEntrustPriceQueueMapper.insert(sellerEntrustPriceQueue);
        return ResultBuilder.buildSuccessResult(Public.SUCCESS_200, "");
    }

    @Override
    public RequestResultVO update(SellerEntrustPriceQueue sellerEntrustPriceQueue) {
        if (sellerEntrustPriceQueue == null || sellerEntrustPriceQueue.getSellerEntrustPriceQueueId() == null) {
            throw new BizException(Public.ERROR_700);
        }
        dataAuthorizeService.addDataAuthorizeInfo(sellerEntrustPriceQueue, "update");
        sellerEntrustPriceQueueMapper.updateByPrimaryKeySelective(sellerEntrustPriceQueue);
        return ResultBuilder.buildSuccessResult(Public.SUCCESS_300, "");
    }

    @Override
    public RequestResultVO delete(List<Integer> sellerEntrustPriceQueueIds) {
        if (sellerEntrustPriceQueueIds == null || sellerEntrustPriceQueueIds.size() == 0) {
            throw new BizException(Public.ERROR_700);
        }
        SellerEntrustPriceQueueExample sellerEntrustPriceQueueExample = new SellerEntrustPriceQueueExample();
        sellerEntrustPriceQueueExample.createCriteria().andSellerEntrustPriceQueueIdIn(sellerEntrustPriceQueueIds);
        sellerEntrustPriceQueueMapper.deleteByExample(sellerEntrustPriceQueueExample);
        return ResultBuilder.buildSuccessResult(Public.SUCCESS_400, "");
    }

    @Override
    public Map<String, Object> getByPage(String keys, Integer pageSize,
                                         Integer pageNow) {
        SellerEntrustPriceQueueExample sellerEntrustPriceQueueExample = new SellerEntrustPriceQueueExample();
        this.setCriteria(keys, sellerEntrustPriceQueueExample);
        int totalrecords = sellerEntrustPriceQueueMapper.countByExample(sellerEntrustPriceQueueExample);

        Page page = new Page();
        page.setBegin(pageNow);
        page.setLength(pageSize);
        sellerEntrustPriceQueueExample.setOrderByClause("sellerEntrustPriceQueueId desc");
        sellerEntrustPriceQueueExample.setPage(page);
        List<SellerEntrustPriceQueue> sellerEntrustPriceQueues = sellerEntrustPriceQueueMapper.selectByExample(sellerEntrustPriceQueueExample);

        Map<String, Object> map = new HashMap<String, Object>();
        JsonConfig config = new JsonConfig();
        config.setIgnoreDefaultExcludes(false);
        config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
        try {
            map.put("aaData", JSONArray.fromObject(this.creatVos(sellerEntrustPriceQueues), config));
        } catch (Exception e) {
            LogUtil.error(ErrorLoggers.ERROR_LOGGER, e.getMessage());
            throw new BizException(Public.ERROR_100);
        }
        map.put("recordsTotal", totalrecords);
        map.put("recordsFiltered", totalrecords);

        return map;
    }

    @Override
    public SellerEntrustPriceQueue findBySllerEntrustPrice(int sellerEntrustPriceId) {
        SellerEntrustPriceQueueExample sellerEntrustPriceQueueExample = new SellerEntrustPriceQueueExample();
        SellerEntrustPriceQueueExample.Criteria criteria = sellerEntrustPriceQueueExample.createCriteria();
        criteria.andSellerEntrustPriceIdEqualTo(sellerEntrustPriceId);
        List<SellerEntrustPriceQueue> sellerEntrustPriceQueues = sellerEntrustPriceQueueMapper.selectByExample(sellerEntrustPriceQueueExample);
        if (sellerEntrustPriceQueues.size() != 0) {
            return sellerEntrustPriceQueues.get(0);
        } else {
            return null;
        }
    }

    private void setCriteria(String keys, SellerEntrustPriceQueueExample sellerEntrustPriceQueueExample) {
        if (keys == null || "{}".equals(keys))
            return;
        //JSONObject jKeys = JSONObject.fromObject(keys);
        //Criteria criteria = sellerEntrustPriceQueueExample.createCriteria();

    }

    private List<SellerEntrustPriceQueueVO> creatVos(List<SellerEntrustPriceQueue> sellerEntrustPriceQueues) throws Exception {
        List<SellerEntrustPriceQueueVO> sellerEntrustPriceQueueVOs = new ArrayList<SellerEntrustPriceQueueVO>();
        for (SellerEntrustPriceQueue sellerEntrustPriceQueue : sellerEntrustPriceQueues) {
            SellerEntrustPriceQueueVO sellerEntrustPriceQueueVO = new SellerEntrustPriceQueueVO();
            BeanUtils.copyProperties(sellerEntrustPriceQueue, sellerEntrustPriceQueueVO);
            commonService.addBaseModel(sellerEntrustPriceQueue, sellerEntrustPriceQueueVO);
            sellerEntrustPriceQueueVOs.add(sellerEntrustPriceQueueVO);
        }
        return sellerEntrustPriceQueueVOs;
    }
}





package com.stock.service.stock.impl;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.stock.dao.model.stock.BuyerEntrustPriceQueue;
import com.stock.dao.model.stock.SellerHistoryEntrustRecord;
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
import com.stock.dao.mapper.stock.BuyerHistoryEntrustRecordMapper;
import com.stock.dao.model.stock.BuyerHistoryEntrustRecord;
import com.stock.dao.model.stock.BuyerHistoryEntrustRecordExample;

import com.stock.pojo.vo.stock.BuyerHistoryEntrustRecordVO;
import com.stock.service.stock.BuyerHistoryEntrustRecordService;

@Service
public class BuyerHistoryEntrustRecordServiceImpl implements BuyerHistoryEntrustRecordService {

	@Autowired
	private BuyerHistoryEntrustRecordMapper buyerHistoryEntrustRecordMapper;

	@Autowired
	private DataAuthorizeService dataAuthorizeService;

	private CommonService<BuyerHistoryEntrustRecord, BuyerHistoryEntrustRecordMapper, BuyerHistoryEntrustRecordExample> commonService;
	//注入commonService
	@Resource(name = "commonService")
	public void setCommonService(CommonService<BuyerHistoryEntrustRecord, BuyerHistoryEntrustRecordMapper, BuyerHistoryEntrustRecordExample> commonService) {
		this.commonService = commonService;
	}

	@Override
	public RequestResultVO insert(BuyerHistoryEntrustRecord buyerHistoryEntrustRecord) {
		if(buyerHistoryEntrustRecord == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(buyerHistoryEntrustRecord, "insert");
    buyerHistoryEntrustRecordMapper.insert(buyerHistoryEntrustRecord);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_200, "");
	}

	@Override
	public RequestResultVO update(BuyerHistoryEntrustRecord buyerHistoryEntrustRecord) {
		if(buyerHistoryEntrustRecord == null || buyerHistoryEntrustRecord.getBuyerHistoryEntrustRecordId() == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(buyerHistoryEntrustRecord, "update");
    buyerHistoryEntrustRecordMapper.updateByPrimaryKeySelective(buyerHistoryEntrustRecord);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_300, "");
	}

	@Override
	public RequestResultVO delete(List<Integer> buyerHistoryEntrustRecordIds) {
    if(buyerHistoryEntrustRecordIds == null || buyerHistoryEntrustRecordIds.size() == 0){
    throw new BizException(Public.ERROR_700);
    }
    BuyerHistoryEntrustRecordExample buyerHistoryEntrustRecordExample = new BuyerHistoryEntrustRecordExample();
    buyerHistoryEntrustRecordExample.createCriteria().andBuyerHistoryEntrustRecordIdIn(buyerHistoryEntrustRecordIds);
    buyerHistoryEntrustRecordMapper.deleteByExample(buyerHistoryEntrustRecordExample);
    return ResultBuilder.buildSuccessResult(Public.SUCCESS_400, "");
    }

    @Override
    public Map<String, Object> getByPage(String keys, Integer pageSize,
    Integer pageNow) {
    BuyerHistoryEntrustRecordExample buyerHistoryEntrustRecordExample = new BuyerHistoryEntrustRecordExample();
    this.setCriteria(keys, buyerHistoryEntrustRecordExample);
    int totalrecords = buyerHistoryEntrustRecordMapper.countByExample(buyerHistoryEntrustRecordExample);

    Page page = new Page();
    page.setBegin(pageNow);
    page.setLength(pageSize);
    buyerHistoryEntrustRecordExample.setOrderByClause("buyerHistoryEntrustRecordId desc");
    buyerHistoryEntrustRecordExample.setPage(page);
    List<BuyerHistoryEntrustRecord> buyerHistoryEntrustRecords = buyerHistoryEntrustRecordMapper.selectByExample(buyerHistoryEntrustRecordExample);

    Map<String, Object> map = new HashMap<String, Object>();
    JsonConfig config = new JsonConfig();
    config.setIgnoreDefaultExcludes(false);
    config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));
    try {
    map.put("aaData", JSONArray.fromObject(this.creatVos(buyerHistoryEntrustRecords), config));
    } catch (Exception e) {
    LogUtil.error(ErrorLoggers.ERROR_LOGGER, e.getMessage());
    throw new BizException(Public.ERROR_100);
    }
    map.put("recordsTotal", totalrecords);
    map.put("recordsFiltered", totalrecords);

    return map;
    }

    @Override
    public void addHistory(BuyerEntrustPriceQueue buyerEntrustPriceQueue, int stockId, double price) {
        BuyerHistoryEntrustRecord buyerHistoryEntrustRecord=new BuyerHistoryEntrustRecord();
        buyerHistoryEntrustRecord.setUserId(buyerEntrustPriceQueue.getUserId());
        buyerHistoryEntrustRecord.setStockId(stockId);
        buyerHistoryEntrustRecord.setEntrustDate(buyerEntrustPriceQueue.getAmendTime());
        //buyerHistoryEntrustRecord.setEntrustPrice(price);
        buyerHistoryEntrustRecord.setEntrustNum(buyerEntrustPriceQueue.getEntrustNum());
        buyerHistoryEntrustRecord.setDealNum(buyerEntrustPriceQueue.getEntrustNum());
        buyerHistoryEntrustRecord.setDealPrice(price);
        buyerHistoryEntrustRecord.setDealDate(buyerEntrustPriceQueue.getAmendTime());
        this.insert(buyerHistoryEntrustRecord);
    }

    private void setCriteria(String keys, BuyerHistoryEntrustRecordExample buyerHistoryEntrustRecordExample) {
    if (keys == null || "{}".equals(keys))
    return;
    //JSONObject jKeys = JSONObject.fromObject(keys);
    //Criteria criteria = buyerHistoryEntrustRecordExample.createCriteria();

    }
    private List<BuyerHistoryEntrustRecordVO> creatVos(List<BuyerHistoryEntrustRecord> buyerHistoryEntrustRecords) throws Exception{
        List<BuyerHistoryEntrustRecordVO> buyerHistoryEntrustRecordVOs = new ArrayList<BuyerHistoryEntrustRecordVO>();
            for(BuyerHistoryEntrustRecord buyerHistoryEntrustRecord : buyerHistoryEntrustRecords){
            BuyerHistoryEntrustRecordVO buyerHistoryEntrustRecordVO = new BuyerHistoryEntrustRecordVO();
            BeanUtils.copyProperties(buyerHistoryEntrustRecord, buyerHistoryEntrustRecordVO);
            commonService.addBaseModel(buyerHistoryEntrustRecord, buyerHistoryEntrustRecordVO);
            buyerHistoryEntrustRecordVOs.add(buyerHistoryEntrustRecordVO);
            }
            return buyerHistoryEntrustRecordVOs;
            }
            }





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
import com.stock.dao.mapper.stock.BuyerSellerHistoryEntrustRecordMapper;
import com.stock.dao.model.stock.BuyerSellerHistoryEntrustRecord;
import com.stock.dao.model.stock.BuyerSellerHistoryEntrustRecordExample;

import com.stock.pojo.vo.stock.BuyerSellerHistoryEntrustRecordVO;
import com.stock.service.stock.BuyerSellerHistoryEntrustRecordService;

@Service
public class BuyerSellerHistoryEntrustRecordServiceImpl implements BuyerSellerHistoryEntrustRecordService {

	@Autowired
	private BuyerSellerHistoryEntrustRecordMapper buyerSellerHistoryEntrustRecordMapper;

	@Autowired
	private DataAuthorizeService dataAuthorizeService;

	private CommonService<BuyerSellerHistoryEntrustRecord, BuyerSellerHistoryEntrustRecordMapper, BuyerSellerHistoryEntrustRecordExample> commonService;
	//注入commonService
	@Resource(name = "commonService")
	public void setCommonService(CommonService<BuyerSellerHistoryEntrustRecord, BuyerSellerHistoryEntrustRecordMapper, BuyerSellerHistoryEntrustRecordExample> commonService) {
		this.commonService = commonService;
	}

	@Override
	public RequestResultVO insert(BuyerSellerHistoryEntrustRecord buyerSellerHistoryEntrustRecord) {
		if(buyerSellerHistoryEntrustRecord == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(buyerSellerHistoryEntrustRecord, "insert");
    buyerSellerHistoryEntrustRecordMapper.insert(buyerSellerHistoryEntrustRecord);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_200, "");
	}

	@Override
	public RequestResultVO update(BuyerSellerHistoryEntrustRecord buyerSellerHistoryEntrustRecord) {
		if(buyerSellerHistoryEntrustRecord == null || buyerSellerHistoryEntrustRecord.getBuyerSellerHistoryEntrustRecordId() == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(buyerSellerHistoryEntrustRecord, "update");
    buyerSellerHistoryEntrustRecordMapper.updateByPrimaryKeySelective(buyerSellerHistoryEntrustRecord);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_300, "");
	}

	@Override
	public RequestResultVO delete(List<Integer> buyerSellerHistoryEntrustRecordIds) {
    if(buyerSellerHistoryEntrustRecordIds == null || buyerSellerHistoryEntrustRecordIds.size() == 0){
    throw new BizException(Public.ERROR_700);
    }
    BuyerSellerHistoryEntrustRecordExample buyerSellerHistoryEntrustRecordExample = new BuyerSellerHistoryEntrustRecordExample();
    buyerSellerHistoryEntrustRecordExample.createCriteria().andBuyerSellerHistoryEntrustRecordIdIn(buyerSellerHistoryEntrustRecordIds);
    buyerSellerHistoryEntrustRecordMapper.deleteByExample(buyerSellerHistoryEntrustRecordExample);
    return ResultBuilder.buildSuccessResult(Public.SUCCESS_400, "");
    }

    @Override
    public Map<String, Object> getByPage(String keys, Integer pageSize,
    Integer pageNow) {
    BuyerSellerHistoryEntrustRecordExample buyerSellerHistoryEntrustRecordExample = new BuyerSellerHistoryEntrustRecordExample();
    this.setCriteria(keys, buyerSellerHistoryEntrustRecordExample);
    int totalrecords = buyerSellerHistoryEntrustRecordMapper.countByExample(buyerSellerHistoryEntrustRecordExample);

    Page page = new Page();
    page.setBegin(pageNow);
    page.setLength(pageSize);
    buyerSellerHistoryEntrustRecordExample.setOrderByClause("buyerSellerHistoryEntrustRecordId desc");
    buyerSellerHistoryEntrustRecordExample.setPage(page);
    List<BuyerSellerHistoryEntrustRecord> buyerSellerHistoryEntrustRecords = buyerSellerHistoryEntrustRecordMapper.selectByExample(buyerSellerHistoryEntrustRecordExample);

    Map<String, Object> map = new HashMap<String, Object>();
    JsonConfig config = new JsonConfig();
    config.setIgnoreDefaultExcludes(false);
    config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));
    try {
    map.put("aaData", JSONArray.fromObject(this.creatVos(buyerSellerHistoryEntrustRecords), config));
    } catch (Exception e) {
    LogUtil.error(ErrorLoggers.ERROR_LOGGER, e.getMessage());
    throw new BizException(Public.ERROR_100);
    }
    map.put("recordsTotal", totalrecords);
    map.put("recordsFiltered", totalrecords);

    return map;
    }
    private void setCriteria(String keys, BuyerSellerHistoryEntrustRecordExample buyerSellerHistoryEntrustRecordExample) {
    if (keys == null || "{}".equals(keys))
    return;
    //JSONObject jKeys = JSONObject.fromObject(keys);
    //Criteria criteria = buyerSellerHistoryEntrustRecordExample.createCriteria();

    }
    private List<BuyerSellerHistoryEntrustRecordVO> creatVos(List<BuyerSellerHistoryEntrustRecord> buyerSellerHistoryEntrustRecords) throws Exception{
        List<BuyerSellerHistoryEntrustRecordVO> buyerSellerHistoryEntrustRecordVOs = new ArrayList<BuyerSellerHistoryEntrustRecordVO>();
            for(BuyerSellerHistoryEntrustRecord buyerSellerHistoryEntrustRecord : buyerSellerHistoryEntrustRecords){
            BuyerSellerHistoryEntrustRecordVO buyerSellerHistoryEntrustRecordVO = new BuyerSellerHistoryEntrustRecordVO();
            BeanUtils.copyProperties(buyerSellerHistoryEntrustRecord, buyerSellerHistoryEntrustRecordVO);
            commonService.addBaseModel(buyerSellerHistoryEntrustRecord, buyerSellerHistoryEntrustRecordVO);
            buyerSellerHistoryEntrustRecordVOs.add(buyerSellerHistoryEntrustRecordVO);
            }
            return buyerSellerHistoryEntrustRecordVOs;
            }
            }





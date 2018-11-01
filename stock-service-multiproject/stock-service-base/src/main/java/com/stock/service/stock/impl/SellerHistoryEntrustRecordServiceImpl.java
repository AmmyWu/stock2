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
import com.stock.dao.mapper.stock.SellerHistoryEntrustRecordMapper;
import com.stock.dao.model.stock.SellerHistoryEntrustRecord;
import com.stock.dao.model.stock.SellerHistoryEntrustRecordExample;

import com.stock.pojo.vo.stock.SellerHistoryEntrustRecordVO;
import com.stock.service.stock.SellerHistoryEntrustRecordService;

@Service
public class SellerHistoryEntrustRecordServiceImpl implements SellerHistoryEntrustRecordService {

	@Autowired
	private SellerHistoryEntrustRecordMapper sellerHistoryEntrustRecordMapper;

	@Autowired
	private DataAuthorizeService dataAuthorizeService;

	private CommonService<SellerHistoryEntrustRecord, SellerHistoryEntrustRecordMapper, SellerHistoryEntrustRecordExample> commonService;
	//注入commonService
	@Resource(name = "commonService")
	public void setCommonService(CommonService<SellerHistoryEntrustRecord, SellerHistoryEntrustRecordMapper, SellerHistoryEntrustRecordExample> commonService) {
		this.commonService = commonService;
	}

	@Override
	public RequestResultVO insert(SellerHistoryEntrustRecord sellerHistoryEntrustRecord) {
		if(sellerHistoryEntrustRecord == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(sellerHistoryEntrustRecord, "insert");
    sellerHistoryEntrustRecordMapper.insert(sellerHistoryEntrustRecord);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_200, "");
	}

	@Override
	public RequestResultVO update(SellerHistoryEntrustRecord sellerHistoryEntrustRecord) {
		if(sellerHistoryEntrustRecord == null || sellerHistoryEntrustRecord.getSellerHistoryEntrustRecordId() == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(sellerHistoryEntrustRecord, "update");
    sellerHistoryEntrustRecordMapper.updateByPrimaryKeySelective(sellerHistoryEntrustRecord);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_300, "");
	}

	@Override
	public RequestResultVO delete(List<Integer> sellerHistoryEntrustRecordIds) {
    if(sellerHistoryEntrustRecordIds == null || sellerHistoryEntrustRecordIds.size() == 0){
    throw new BizException(Public.ERROR_700);
    }
    SellerHistoryEntrustRecordExample sellerHistoryEntrustRecordExample = new SellerHistoryEntrustRecordExample();
    sellerHistoryEntrustRecordExample.createCriteria().andSellerHistoryEntrustRecordIdIn(sellerHistoryEntrustRecordIds);
    sellerHistoryEntrustRecordMapper.deleteByExample(sellerHistoryEntrustRecordExample);
    return ResultBuilder.buildSuccessResult(Public.SUCCESS_400, "");
    }

    @Override
    public Map<String, Object> getByPage(String keys, Integer pageSize,
    Integer pageNow) {
    SellerHistoryEntrustRecordExample sellerHistoryEntrustRecordExample = new SellerHistoryEntrustRecordExample();
    this.setCriteria(keys, sellerHistoryEntrustRecordExample);
    int totalrecords = sellerHistoryEntrustRecordMapper.countByExample(sellerHistoryEntrustRecordExample);

    Page page = new Page();
    page.setBegin(pageNow);
    page.setLength(pageSize);
    sellerHistoryEntrustRecordExample.setOrderByClause("sellerHistoryEntrustRecordId desc");
    sellerHistoryEntrustRecordExample.setPage(page);
    List<SellerHistoryEntrustRecord> sellerHistoryEntrustRecords = sellerHistoryEntrustRecordMapper.selectByExample(sellerHistoryEntrustRecordExample);

    Map<String, Object> map = new HashMap<String, Object>();
    JsonConfig config = new JsonConfig();
    config.setIgnoreDefaultExcludes(false);
    config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));
    try {
    map.put("aaData", JSONArray.fromObject(this.creatVos(sellerHistoryEntrustRecords), config));
    } catch (Exception e) {
    LogUtil.error(ErrorLoggers.ERROR_LOGGER, e.getMessage());
    throw new BizException(Public.ERROR_100);
    }
    map.put("recordsTotal", totalrecords);
    map.put("recordsFiltered", totalrecords);

    return map;
    }
    private void setCriteria(String keys, SellerHistoryEntrustRecordExample sellerHistoryEntrustRecordExample) {
    if (keys == null || "{}".equals(keys))
    return;
    //JSONObject jKeys = JSONObject.fromObject(keys);
    //Criteria criteria = sellerHistoryEntrustRecordExample.createCriteria();

    }
    private List<SellerHistoryEntrustRecordVO> creatVos(List<SellerHistoryEntrustRecord> sellerHistoryEntrustRecords) throws Exception{
        List<SellerHistoryEntrustRecordVO> sellerHistoryEntrustRecordVOs = new ArrayList<SellerHistoryEntrustRecordVO>();
            for(SellerHistoryEntrustRecord sellerHistoryEntrustRecord : sellerHistoryEntrustRecords){
            SellerHistoryEntrustRecordVO sellerHistoryEntrustRecordVO = new SellerHistoryEntrustRecordVO();
            BeanUtils.copyProperties(sellerHistoryEntrustRecord, sellerHistoryEntrustRecordVO);
            commonService.addBaseModel(sellerHistoryEntrustRecord, sellerHistoryEntrustRecordVO);
            sellerHistoryEntrustRecordVOs.add(sellerHistoryEntrustRecordVO);
            }
            return sellerHistoryEntrustRecordVOs;
            }
            }





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
import com.stock.dao.mapper.stock.StockAccountMapper;
import com.stock.dao.model.stock.StockAccount;
import com.stock.dao.model.stock.StockAccountExample;

import com.stock.pojo.vo.stock.StockAccountVO;
import com.stock.service.stock.StockAccountService;

@Service
public class StockAccountServiceImpl implements StockAccountService {

	@Autowired
	private StockAccountMapper stockAccountMapper;

	@Autowired
	private DataAuthorizeService dataAuthorizeService;

	private CommonService<StockAccount, StockAccountMapper, StockAccountExample> commonService;
	//注入commonService
	@Resource(name = "commonService")
	public void setCommonService(CommonService<StockAccount, StockAccountMapper, StockAccountExample> commonService) {
		this.commonService = commonService;
	}

	@Override
	public RequestResultVO insert(StockAccount stockAccount) {
		if(stockAccount == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(stockAccount, "insert");
    stockAccountMapper.insert(stockAccount);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_200, "");
	}

	@Override
	public RequestResultVO update(StockAccount stockAccount) {
		if(stockAccount == null || stockAccount.getStockAccountId() == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(stockAccount, "update");
    stockAccountMapper.updateByPrimaryKeySelective(stockAccount);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_300, "");
	}

	@Override
	public RequestResultVO delete(List<Integer> stockAccountIds) {
    if(stockAccountIds == null || stockAccountIds.size() == 0){
    throw new BizException(Public.ERROR_700);
    }
    StockAccountExample stockAccountExample = new StockAccountExample();
    stockAccountExample.createCriteria().andStockAccountIdIn(stockAccountIds);
    stockAccountMapper.deleteByExample(stockAccountExample);
    return ResultBuilder.buildSuccessResult(Public.SUCCESS_400, "");
    }

    @Override
    public Map<String, Object> getByPage(String keys, Integer pageSize,
    Integer pageNow) {
    StockAccountExample stockAccountExample = new StockAccountExample();
    this.setCriteria(keys, stockAccountExample);
    int totalrecords = stockAccountMapper.countByExample(stockAccountExample);

    Page page = new Page();
    page.setBegin(pageNow);
    page.setLength(pageSize);
    stockAccountExample.setOrderByClause("stockAccountId desc");
    stockAccountExample.setPage(page);
    List<StockAccount> stockAccounts = stockAccountMapper.selectByExample(stockAccountExample);

    Map<String, Object> map = new HashMap<String, Object>();
    JsonConfig config = new JsonConfig();
    config.setIgnoreDefaultExcludes(false);
    config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));
    try {
    map.put("aaData", JSONArray.fromObject(this.creatVos(stockAccounts), config));
    } catch (Exception e) {
    LogUtil.error(ErrorLoggers.ERROR_LOGGER, e.getMessage());
    throw new BizException(Public.ERROR_100);
    }
    map.put("recordsTotal", totalrecords);
    map.put("recordsFiltered", totalrecords);

    return map;
    }
    private void setCriteria(String keys, StockAccountExample stockAccountExample) {
    if (keys == null || "{}".equals(keys))
    return;
    //JSONObject jKeys = JSONObject.fromObject(keys);
    //Criteria criteria = stockAccountExample.createCriteria();

    }
    private List<StockAccountVO> creatVos(List<StockAccount> stockAccounts) throws Exception{
        List<StockAccountVO> stockAccountVOs = new ArrayList<StockAccountVO>();
            for(StockAccount stockAccount : stockAccounts){
            StockAccountVO stockAccountVO = new StockAccountVO();
            BeanUtils.copyProperties(stockAccount, stockAccountVO);
            commonService.addBaseModel(stockAccount, stockAccountVO);
            stockAccountVOs.add(stockAccountVO);
            }
            return stockAccountVOs;
            }
            }





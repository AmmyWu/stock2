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
import com.stock.dao.mapper.stock.StockTurnoverRateMapper;
import com.stock.dao.model.stock.StockTurnoverRate;
import com.stock.dao.model.stock.StockTurnoverRateExample;

import com.stock.pojo.vo.stock.StockTurnoverRateVO;
import com.stock.service.stock.StockTurnoverRateService;

@Service
public class StockTurnoverRateServiceImpl implements StockTurnoverRateService {

	@Autowired
	private StockTurnoverRateMapper stockTurnoverRateMapper;

	@Autowired
	private DataAuthorizeService dataAuthorizeService;

	private CommonService<StockTurnoverRate, StockTurnoverRateMapper, StockTurnoverRateExample> commonService;
	//注入commonService
	@Resource(name = "commonService")
	public void setCommonService(CommonService<StockTurnoverRate, StockTurnoverRateMapper, StockTurnoverRateExample> commonService) {
		this.commonService = commonService;
	}

	@Override
	public RequestResultVO insert(StockTurnoverRate stockTurnoverRate) {
		if(stockTurnoverRate == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(stockTurnoverRate, "insert");
    stockTurnoverRateMapper.insert(stockTurnoverRate);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_200, "");
	}

	@Override
	public RequestResultVO update(StockTurnoverRate stockTurnoverRate) {
		if(stockTurnoverRate == null || stockTurnoverRate.getStockTernoverRateId() == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(stockTurnoverRate, "update");
    stockTurnoverRateMapper.updateByPrimaryKeySelective(stockTurnoverRate);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_300, "");
	}

	@Override
	public RequestResultVO delete(List<Integer> stockTernoverRateIds) {
    if(stockTernoverRateIds == null || stockTernoverRateIds.size() == 0){
    throw new BizException(Public.ERROR_700);
    }
    StockTurnoverRateExample stockTurnoverRateExample = new StockTurnoverRateExample();
    stockTurnoverRateExample.createCriteria().andStockTernoverRateIdIn(stockTernoverRateIds);
    stockTurnoverRateMapper.deleteByExample(stockTurnoverRateExample);
    return ResultBuilder.buildSuccessResult(Public.SUCCESS_400, "");
    }

    @Override
    public Map<String, Object> getByPage(String keys, Integer pageSize,
    Integer pageNow) {
    StockTurnoverRateExample stockTurnoverRateExample = new StockTurnoverRateExample();
    this.setCriteria(keys, stockTurnoverRateExample);
    int totalrecords = stockTurnoverRateMapper.countByExample(stockTurnoverRateExample);

    Page page = new Page();
    page.setBegin(pageNow);
    page.setLength(pageSize);
    stockTurnoverRateExample.setOrderByClause("stockTernoverRateId desc");
    stockTurnoverRateExample.setPage(page);
    List<StockTurnoverRate> stockTurnoverRates = stockTurnoverRateMapper.selectByExample(stockTurnoverRateExample);

    Map<String, Object> map = new HashMap<String, Object>();
    JsonConfig config = new JsonConfig();
    config.setIgnoreDefaultExcludes(false);
    config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));
    try {
    map.put("aaData", JSONArray.fromObject(this.creatVos(stockTurnoverRates), config));
    } catch (Exception e) {
    LogUtil.error(ErrorLoggers.ERROR_LOGGER, e.getMessage());
    throw new BizException(Public.ERROR_100);
    }
    map.put("recordsTotal", totalrecords);
    map.put("recordsFiltered", totalrecords);

    return map;
    }
    private void setCriteria(String keys, StockTurnoverRateExample stockTurnoverRateExample) {
    if (keys == null || "{}".equals(keys))
    return;
    //JSONObject jKeys = JSONObject.fromObject(keys);
    //Criteria criteria = stockTurnoverRateExample.createCriteria();

    }
    private List<StockTurnoverRateVO> creatVos(List<StockTurnoverRate> stockTurnoverRates) throws Exception{
        List<StockTurnoverRateVO> stockTurnoverRateVOs = new ArrayList<StockTurnoverRateVO>();
            for(StockTurnoverRate stockTurnoverRate : stockTurnoverRates){
            StockTurnoverRateVO stockTurnoverRateVO = new StockTurnoverRateVO();
            BeanUtils.copyProperties(stockTurnoverRate, stockTurnoverRateVO);
            commonService.addBaseModel(stockTurnoverRate, stockTurnoverRateVO);
            stockTurnoverRateVOs.add(stockTurnoverRateVO);
            }
            return stockTurnoverRateVOs;
            }
            }





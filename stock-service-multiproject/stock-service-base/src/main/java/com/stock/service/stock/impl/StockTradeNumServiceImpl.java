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
import com.stock.dao.mapper.stock.StockTradeNumMapper;
import com.stock.dao.model.stock.StockTradeNum;
import com.stock.dao.model.stock.StockTradeNumExample;

import com.stock.pojo.vo.stock.StockTradeNumVO;
import com.stock.service.stock.StockTradeNumService;

@Service
public class StockTradeNumServiceImpl implements StockTradeNumService {

	@Autowired
	private StockTradeNumMapper stockTradeNumMapper;

	@Autowired
	private DataAuthorizeService dataAuthorizeService;

	private CommonService<StockTradeNum, StockTradeNumMapper, StockTradeNumExample> commonService;
	//注入commonService
	@Resource(name = "commonService")
	public void setCommonService(CommonService<StockTradeNum, StockTradeNumMapper, StockTradeNumExample> commonService) {
		this.commonService = commonService;
	}

	@Override
	public RequestResultVO insert(StockTradeNum stockTradeNum) {
		if(stockTradeNum == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(stockTradeNum, "insert");
    stockTradeNumMapper.insert(stockTradeNum);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_200, "");
	}

	@Override
	public RequestResultVO update(StockTradeNum stockTradeNum) {
		if(stockTradeNum == null || stockTradeNum.getStockTradeNumId() == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(stockTradeNum, "update");
    stockTradeNumMapper.updateByPrimaryKeySelective(stockTradeNum);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_300, "");
	}

	@Override
	public RequestResultVO delete(List<Integer> stockTradeNumIds) {
    if(stockTradeNumIds == null || stockTradeNumIds.size() == 0){
    throw new BizException(Public.ERROR_700);
    }
    StockTradeNumExample stockTradeNumExample = new StockTradeNumExample();
    stockTradeNumExample.createCriteria().andStockTradeNumIdIn(stockTradeNumIds);
    stockTradeNumMapper.deleteByExample(stockTradeNumExample);
    return ResultBuilder.buildSuccessResult(Public.SUCCESS_400, "");
    }

    @Override
    public Map<String, Object> getByPage(String keys, Integer pageSize,
    Integer pageNow) {
    StockTradeNumExample stockTradeNumExample = new StockTradeNumExample();
    this.setCriteria(keys, stockTradeNumExample);
    int totalrecords = stockTradeNumMapper.countByExample(stockTradeNumExample);

    Page page = new Page();
    page.setBegin(pageNow);
    page.setLength(pageSize);
    stockTradeNumExample.setOrderByClause("stockTradeNumId desc");
    stockTradeNumExample.setPage(page);
    List<StockTradeNum> stockTradeNums = stockTradeNumMapper.selectByExample(stockTradeNumExample);

    Map<String, Object> map = new HashMap<String, Object>();
    JsonConfig config = new JsonConfig();
    config.setIgnoreDefaultExcludes(false);
    config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));
    try {
    map.put("aaData", JSONArray.fromObject(this.creatVos(stockTradeNums), config));
    } catch (Exception e) {
    LogUtil.error(ErrorLoggers.ERROR_LOGGER, e.getMessage());
    throw new BizException(Public.ERROR_100);
    }
    map.put("recordsTotal", totalrecords);
    map.put("recordsFiltered", totalrecords);

    return map;
    }
    private void setCriteria(String keys, StockTradeNumExample stockTradeNumExample) {
    if (keys == null || "{}".equals(keys))
    return;
    //JSONObject jKeys = JSONObject.fromObject(keys);
    //Criteria criteria = stockTradeNumExample.createCriteria();

    }
    private List<StockTradeNumVO> creatVos(List<StockTradeNum> stockTradeNums) throws Exception{
        List<StockTradeNumVO> stockTradeNumVOs = new ArrayList<StockTradeNumVO>();
            for(StockTradeNum stockTradeNum : stockTradeNums){
            StockTradeNumVO stockTradeNumVO = new StockTradeNumVO();
            BeanUtils.copyProperties(stockTradeNum, stockTradeNumVO);
            commonService.addBaseModel(stockTradeNum, stockTradeNumVO);
            stockTradeNumVOs.add(stockTradeNumVO);
            }
            return stockTradeNumVOs;
            }
            }





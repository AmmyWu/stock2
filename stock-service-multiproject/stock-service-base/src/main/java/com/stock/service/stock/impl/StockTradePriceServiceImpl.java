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
import com.stock.dao.mapper.stock.StockTradePriceMapper;
import com.stock.dao.model.stock.StockTradePrice;
import com.stock.dao.model.stock.StockTradePriceExample;

import com.stock.pojo.vo.stock.StockTradePriceVO;
import com.stock.service.stock.StockTradePriceService;

@Service
public class StockTradePriceServiceImpl implements StockTradePriceService {

	@Autowired
	private StockTradePriceMapper stockTradePriceMapper;

	@Autowired
	private DataAuthorizeService dataAuthorizeService;

	private CommonService<StockTradePrice, StockTradePriceMapper, StockTradePriceExample> commonService;
	//注入commonService
	@Resource(name = "commonService")
	public void setCommonService(CommonService<StockTradePrice, StockTradePriceMapper, StockTradePriceExample> commonService) {
		this.commonService = commonService;
	}

	@Override
	public RequestResultVO insert(StockTradePrice stockTradePrice) {
		if(stockTradePrice == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(stockTradePrice, "insert");
    stockTradePriceMapper.insert(stockTradePrice);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_200, "");
	}

	@Override
	public RequestResultVO update(StockTradePrice stockTradePrice) {
		if(stockTradePrice == null || stockTradePrice.getStockTradePriceId() == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(stockTradePrice, "update");
    stockTradePriceMapper.updateByPrimaryKeySelective(stockTradePrice);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_300, "");
	}

	@Override
	public RequestResultVO delete(List<Integer> stockTradePriceIds) {
    if(stockTradePriceIds == null || stockTradePriceIds.size() == 0){
    throw new BizException(Public.ERROR_700);
    }
    StockTradePriceExample stockTradePriceExample = new StockTradePriceExample();
    stockTradePriceExample.createCriteria().andStockTradePriceIdIn(stockTradePriceIds);
    stockTradePriceMapper.deleteByExample(stockTradePriceExample);
    return ResultBuilder.buildSuccessResult(Public.SUCCESS_400, "");
    }

    @Override
    public Map<String, Object> getByPage(String keys, Integer pageSize,
    Integer pageNow) {
    StockTradePriceExample stockTradePriceExample = new StockTradePriceExample();
    this.setCriteria(keys, stockTradePriceExample);
    int totalrecords = stockTradePriceMapper.countByExample(stockTradePriceExample);

    Page page = new Page();
    page.setBegin(pageNow);
    page.setLength(pageSize);
    stockTradePriceExample.setOrderByClause("stockTradePriceId desc");
    stockTradePriceExample.setPage(page);
    List<StockTradePrice> stockTradePrices = stockTradePriceMapper.selectByExample(stockTradePriceExample);

    Map<String, Object> map = new HashMap<String, Object>();
    JsonConfig config = new JsonConfig();
    config.setIgnoreDefaultExcludes(false);
    config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));
    try {
    map.put("aaData", JSONArray.fromObject(this.creatVos(stockTradePrices), config));
    } catch (Exception e) {
    LogUtil.error(ErrorLoggers.ERROR_LOGGER, e.getMessage());
    throw new BizException(Public.ERROR_100);
    }
    map.put("recordsTotal", totalrecords);
    map.put("recordsFiltered", totalrecords);

    return map;
    }
    private void setCriteria(String keys, StockTradePriceExample stockTradePriceExample) {
    if (keys == null || "{}".equals(keys))
    return;
    //JSONObject jKeys = JSONObject.fromObject(keys);
    //Criteria criteria = stockTradePriceExample.createCriteria();

    }
    private List<StockTradePriceVO> creatVos(List<StockTradePrice> stockTradePrices) throws Exception{
        List<StockTradePriceVO> stockTradePriceVOs = new ArrayList<StockTradePriceVO>();
            for(StockTradePrice stockTradePrice : stockTradePrices){
            StockTradePriceVO stockTradePriceVO = new StockTradePriceVO();
            BeanUtils.copyProperties(stockTradePrice, stockTradePriceVO);
            commonService.addBaseModel(stockTradePrice, stockTradePriceVO);
            stockTradePriceVOs.add(stockTradePriceVO);
            }
            return stockTradePriceVOs;
            }
            }





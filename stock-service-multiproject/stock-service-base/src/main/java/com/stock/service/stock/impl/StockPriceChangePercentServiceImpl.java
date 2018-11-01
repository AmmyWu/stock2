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
import com.stock.dao.mapper.stock.StockPriceChangePercentMapper;
import com.stock.dao.model.stock.StockPriceChangePercent;
import com.stock.dao.model.stock.StockPriceChangePercentExample;

import com.stock.pojo.vo.stock.StockPriceChangePercentVO;
import com.stock.service.stock.StockPriceChangePercentService;

@Service
public class StockPriceChangePercentServiceImpl implements StockPriceChangePercentService {

	@Autowired
	private StockPriceChangePercentMapper stockPriceChangePercentMapper;

	@Autowired
	private DataAuthorizeService dataAuthorizeService;

	private CommonService<StockPriceChangePercent, StockPriceChangePercentMapper, StockPriceChangePercentExample> commonService;
	//注入commonService
	@Resource(name = "commonService")
	public void setCommonService(CommonService<StockPriceChangePercent, StockPriceChangePercentMapper, StockPriceChangePercentExample> commonService) {
		this.commonService = commonService;
	}

	@Override
	public RequestResultVO insert(StockPriceChangePercent stockPriceChangePercent) {
		if(stockPriceChangePercent == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(stockPriceChangePercent, "insert");
    stockPriceChangePercentMapper.insert(stockPriceChangePercent);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_200, "");
	}

	@Override
	public RequestResultVO update(StockPriceChangePercent stockPriceChangePercent) {
		if(stockPriceChangePercent == null || stockPriceChangePercent.getStockPriceChangePercentId() == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(stockPriceChangePercent, "update");
    stockPriceChangePercentMapper.updateByPrimaryKeySelective(stockPriceChangePercent);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_300, "");
	}

	@Override
	public RequestResultVO delete(List<Integer> stockPriceChangePercentIds) {
    if(stockPriceChangePercentIds == null || stockPriceChangePercentIds.size() == 0){
    throw new BizException(Public.ERROR_700);
    }
    StockPriceChangePercentExample stockPriceChangePercentExample = new StockPriceChangePercentExample();
    stockPriceChangePercentExample.createCriteria().andStockPriceChangePercentIdIn(stockPriceChangePercentIds);
    stockPriceChangePercentMapper.deleteByExample(stockPriceChangePercentExample);
    return ResultBuilder.buildSuccessResult(Public.SUCCESS_400, "");
    }

    @Override
    public Map<String, Object> getByPage(String keys, Integer pageSize,
    Integer pageNow) {
    StockPriceChangePercentExample stockPriceChangePercentExample = new StockPriceChangePercentExample();
    this.setCriteria(keys, stockPriceChangePercentExample);
    int totalrecords = stockPriceChangePercentMapper.countByExample(stockPriceChangePercentExample);

    Page page = new Page();
    page.setBegin(pageNow);
    page.setLength(pageSize);
    stockPriceChangePercentExample.setOrderByClause("stockPriceChangePercentId desc");
    stockPriceChangePercentExample.setPage(page);
    List<StockPriceChangePercent> stockPriceChangePercents = stockPriceChangePercentMapper.selectByExample(stockPriceChangePercentExample);

    Map<String, Object> map = new HashMap<String, Object>();
    JsonConfig config = new JsonConfig();
    config.setIgnoreDefaultExcludes(false);
    config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));
    try {
    map.put("aaData", JSONArray.fromObject(this.creatVos(stockPriceChangePercents), config));
    } catch (Exception e) {
    LogUtil.error(ErrorLoggers.ERROR_LOGGER, e.getMessage());
    throw new BizException(Public.ERROR_100);
    }
    map.put("recordsTotal", totalrecords);
    map.put("recordsFiltered", totalrecords);

    return map;
    }
    private void setCriteria(String keys, StockPriceChangePercentExample stockPriceChangePercentExample) {
    if (keys == null || "{}".equals(keys))
    return;
    //JSONObject jKeys = JSONObject.fromObject(keys);
    //Criteria criteria = stockPriceChangePercentExample.createCriteria();

    }
    private List<StockPriceChangePercentVO> creatVos(List<StockPriceChangePercent> stockPriceChangePercents) throws Exception{
        List<StockPriceChangePercentVO> stockPriceChangePercentVOs = new ArrayList<StockPriceChangePercentVO>();
            for(StockPriceChangePercent stockPriceChangePercent : stockPriceChangePercents){
            StockPriceChangePercentVO stockPriceChangePercentVO = new StockPriceChangePercentVO();
            BeanUtils.copyProperties(stockPriceChangePercent, stockPriceChangePercentVO);
            commonService.addBaseModel(stockPriceChangePercent, stockPriceChangePercentVO);
            stockPriceChangePercentVOs.add(stockPriceChangePercentVO);
            }
            return stockPriceChangePercentVOs;
            }
            }





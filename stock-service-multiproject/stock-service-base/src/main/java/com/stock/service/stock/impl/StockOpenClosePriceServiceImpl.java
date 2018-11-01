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
import com.stock.dao.mapper.stock.StockOpenClosePriceMapper;
import com.stock.dao.model.stock.StockOpenClosePrice;
import com.stock.dao.model.stock.StockOpenClosePriceExample;

import com.stock.pojo.vo.stock.StockOpenClosePriceVO;
import com.stock.service.stock.StockOpenClosePriceService;

@Service
public class StockOpenClosePriceServiceImpl implements StockOpenClosePriceService {

	@Autowired
	private StockOpenClosePriceMapper stockOpenClosePriceMapper;

	@Autowired
	private DataAuthorizeService dataAuthorizeService;

	private CommonService<StockOpenClosePrice, StockOpenClosePriceMapper, StockOpenClosePriceExample> commonService;
	//注入commonService
	@Resource(name = "commonService")
	public void setCommonService(CommonService<StockOpenClosePrice, StockOpenClosePriceMapper, StockOpenClosePriceExample> commonService) {
		this.commonService = commonService;
	}

	@Override
	public RequestResultVO insert(StockOpenClosePrice stockOpenClosePrice) {
		if(stockOpenClosePrice == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(stockOpenClosePrice, "insert");
    stockOpenClosePriceMapper.insert(stockOpenClosePrice);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_200, "");
	}

	@Override
	public RequestResultVO update(StockOpenClosePrice stockOpenClosePrice) {
		if(stockOpenClosePrice == null || stockOpenClosePrice.getStockOpenClosePriceId() == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(stockOpenClosePrice, "update");
    stockOpenClosePriceMapper.updateByPrimaryKeySelective(stockOpenClosePrice);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_300, "");
	}

	@Override
	public RequestResultVO delete(List<Integer> stockOpenClosePriceIds) {
    if(stockOpenClosePriceIds == null || stockOpenClosePriceIds.size() == 0){
    throw new BizException(Public.ERROR_700);
    }
    StockOpenClosePriceExample stockOpenClosePriceExample = new StockOpenClosePriceExample();
    stockOpenClosePriceExample.createCriteria().andStockOpenClosePriceIdIn(stockOpenClosePriceIds);
    stockOpenClosePriceMapper.deleteByExample(stockOpenClosePriceExample);
    return ResultBuilder.buildSuccessResult(Public.SUCCESS_400, "");
    }

    @Override
    public Map<String, Object> getByPage(String keys, Integer pageSize,
    Integer pageNow) {
    StockOpenClosePriceExample stockOpenClosePriceExample = new StockOpenClosePriceExample();
    this.setCriteria(keys, stockOpenClosePriceExample);
    int totalrecords = stockOpenClosePriceMapper.countByExample(stockOpenClosePriceExample);

    Page page = new Page();
    page.setBegin(pageNow);
    page.setLength(pageSize);
    stockOpenClosePriceExample.setOrderByClause("stockOpenClosePriceId desc");
    stockOpenClosePriceExample.setPage(page);
    List<StockOpenClosePrice> stockOpenClosePrices = stockOpenClosePriceMapper.selectByExample(stockOpenClosePriceExample);

    Map<String, Object> map = new HashMap<String, Object>();
    JsonConfig config = new JsonConfig();
    config.setIgnoreDefaultExcludes(false);
    config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));
    try {
    map.put("aaData", JSONArray.fromObject(this.creatVos(stockOpenClosePrices), config));
    } catch (Exception e) {
    LogUtil.error(ErrorLoggers.ERROR_LOGGER, e.getMessage());
    throw new BizException(Public.ERROR_100);
    }
    map.put("recordsTotal", totalrecords);
    map.put("recordsFiltered", totalrecords);

    return map;
    }
    private void setCriteria(String keys, StockOpenClosePriceExample stockOpenClosePriceExample) {
    if (keys == null || "{}".equals(keys))
    return;
    //JSONObject jKeys = JSONObject.fromObject(keys);
    //Criteria criteria = stockOpenClosePriceExample.createCriteria();

    }
    private List<StockOpenClosePriceVO> creatVos(List<StockOpenClosePrice> stockOpenClosePrices) throws Exception{
        List<StockOpenClosePriceVO> stockOpenClosePriceVOs = new ArrayList<StockOpenClosePriceVO>();
            for(StockOpenClosePrice stockOpenClosePrice : stockOpenClosePrices){
            StockOpenClosePriceVO stockOpenClosePriceVO = new StockOpenClosePriceVO();
            BeanUtils.copyProperties(stockOpenClosePrice, stockOpenClosePriceVO);
            commonService.addBaseModel(stockOpenClosePrice, stockOpenClosePriceVO);
            stockOpenClosePriceVOs.add(stockOpenClosePriceVO);
            }
            return stockOpenClosePriceVOs;
            }
            }





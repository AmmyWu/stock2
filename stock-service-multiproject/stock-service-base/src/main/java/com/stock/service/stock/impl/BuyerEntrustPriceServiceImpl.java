package com.stock.service.stock.impl;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
import com.stock.dao.mapper.stock.BuyerEntrustPriceMapper;
import com.stock.dao.model.stock.BuyerEntrustPrice;
import com.stock.dao.model.stock.BuyerEntrustPriceExample;

import com.stock.pojo.vo.stock.BuyerEntrustPriceVO;
import com.stock.service.stock.BuyerEntrustPriceService;

@Service
public class BuyerEntrustPriceServiceImpl implements BuyerEntrustPriceService {

	@Autowired
	private BuyerEntrustPriceMapper buyerEntrustPriceMapper;

	@Autowired
	private DataAuthorizeService dataAuthorizeService;

	private CommonService<BuyerEntrustPrice, BuyerEntrustPriceMapper, BuyerEntrustPriceExample> commonService;
	//注入commonService
	@Resource(name = "commonService")
	public void setCommonService(CommonService<BuyerEntrustPrice, BuyerEntrustPriceMapper, BuyerEntrustPriceExample> commonService) {
		this.commonService = commonService;
	}

	@Override
	public RequestResultVO insert(BuyerEntrustPrice buyerEntrustPrice) {
		if(buyerEntrustPrice == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(buyerEntrustPrice, "insert");
    buyerEntrustPriceMapper.insert(buyerEntrustPrice);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_200, "");
	}

	@Override
	public RequestResultVO update(BuyerEntrustPrice buyerEntrustPrice) {
		if(buyerEntrustPrice == null || buyerEntrustPrice.getBuyerEntrustPriceId() == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(buyerEntrustPrice, "update");
    buyerEntrustPriceMapper.updateByPrimaryKeySelective(buyerEntrustPrice);
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_300, "");
	}

	@Override
	public RequestResultVO delete(List<Integer> buyerEntrustPriceIds) {
    if(buyerEntrustPriceIds == null || buyerEntrustPriceIds.size() == 0){
    throw new BizException(Public.ERROR_700);
    }
    BuyerEntrustPriceExample buyerEntrustPriceExample = new BuyerEntrustPriceExample();
    buyerEntrustPriceExample.createCriteria().andBuyerEntrustPriceIdIn(buyerEntrustPriceIds);
    buyerEntrustPriceMapper.deleteByExample(buyerEntrustPriceExample);
    return ResultBuilder.buildSuccessResult(Public.SUCCESS_400, "");
    }

    @Override
    public Map<String, Object> getByPage(String keys, Integer pageSize,
    Integer pageNow) {
    BuyerEntrustPriceExample buyerEntrustPriceExample = new BuyerEntrustPriceExample();
    this.setCriteria(keys, buyerEntrustPriceExample);
    int totalrecords = buyerEntrustPriceMapper.countByExample(buyerEntrustPriceExample);

    Page page = new Page();
    page.setBegin(pageNow);
    page.setLength(pageSize);
    buyerEntrustPriceExample.setOrderByClause("buyerEntrustPriceId desc");
    buyerEntrustPriceExample.setPage(page);
    List<BuyerEntrustPrice> buyerEntrustPrices = buyerEntrustPriceMapper.selectByExample(buyerEntrustPriceExample);

    Map<String, Object> map = new HashMap<String, Object>();
    JsonConfig config = new JsonConfig();
    config.setIgnoreDefaultExcludes(false);
    config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));
    try {
    map.put("aaData", JSONArray.fromObject(this.creatVos(buyerEntrustPrices), config));
    } catch (Exception e) {
    LogUtil.error(ErrorLoggers.ERROR_LOGGER, e.getMessage());
    throw new BizException(Public.ERROR_100);
    }
    map.put("recordsTotal", totalrecords);
    map.put("recordsFiltered", totalrecords);

    return map;
    }

    @Override
    public BuyerEntrustPrice findBuyerEntrustPriceByPriceAndStock(String priceJson) {
        BuyerEntrustPriceExample buyerEntrustPriceExample = new BuyerEntrustPriceExample();
        BuyerEntrustPriceExample.Criteria criteria = buyerEntrustPriceExample.createCriteria();
        JSONObject jKeys = JSONObject.fromObject(priceJson);
        criteria.andEntrustPriceEqualTo(Double.parseDouble(jKeys.getString("entrustPrice")));
        criteria.andStockIdEqualTo(Integer.parseInt(jKeys.getString("stockId")));
        List<BuyerEntrustPrice> buyerEntrustPrices = buyerEntrustPriceMapper.selectByExample(buyerEntrustPriceExample);
        return buyerEntrustPrices.get(0);
    }


    private void setCriteria(String keys, BuyerEntrustPriceExample buyerEntrustPriceExample) {
    if (keys == null || "{}".equals(keys))
    return;
    //JSONObject jKeys = JSONObject.fromObject(keys);
    //Criteria criteria = buyerEntrustPriceExample.createCriteria();

    }
    private List<BuyerEntrustPriceVO> creatVos(List<BuyerEntrustPrice> buyerEntrustPrices) throws Exception{
        List<BuyerEntrustPriceVO> buyerEntrustPriceVOs = new ArrayList<BuyerEntrustPriceVO>();
            for(BuyerEntrustPrice buyerEntrustPrice : buyerEntrustPrices){
            BuyerEntrustPriceVO buyerEntrustPriceVO = new BuyerEntrustPriceVO();
            BeanUtils.copyProperties(buyerEntrustPrice, buyerEntrustPriceVO);
            commonService.addBaseModel(buyerEntrustPrice, buyerEntrustPriceVO);
            buyerEntrustPriceVOs.add(buyerEntrustPriceVO);
            }
            return buyerEntrustPriceVOs;
            }
            }





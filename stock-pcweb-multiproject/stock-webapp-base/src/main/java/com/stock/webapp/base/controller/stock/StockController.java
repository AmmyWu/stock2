package com.stock.webapp.base.controller.stock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.model.stock.Stock;
import com.stock.service.stock.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.stock.common.exceptions.BizException;
import com.stock.common.util.CommonUtils;
import com.stock.common.util.JsonFastUtil;
import com.stock.pojo.vo.RequestResultVO;
import com.stock.service.sys.utils.HttpResponseConstants.Public;

@Controller
@RequestMapping(value = "/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    /**
     * 查询有效性
     */
    @RequestMapping(value = "check_stock_valid")
    public @ResponseBody
    Integer check(String id, String name) {
        return stockService.check(id, name);
    }

    /**
     * 查询股票主键by股票名称
     * @param name
     * @return
     */
    @RequestMapping(value = "find_key_by_name")
    public @ResponseBody Integer findKeyByName(String name){
        return stockService.findKeyByName(name);
    }
    @RequestMapping(value = "find_key_by_id")
    public @ResponseBody Integer findKeyById(String code){
        return stockService.findKeyByCode(code);
    }

    /**
     * 新增
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/insert.do")
    public @ResponseBody
    RequestResultVO insert(HttpServletRequest request) {
        String stockString = request.getParameter("stock");
        Stock stock = null;
        try {
            stock = JsonFastUtil.parseObject(stockString, Stock.class);
        } catch (Exception e) {
            throw new BizException(Public.ERROR_700);
        }
        return stockService.insert(stock);
    }

    /**
     * 修改
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/update.do")
    public @ResponseBody
    RequestResultVO update(HttpServletRequest request) {
        String stockString = request.getParameter("stock");
        Stock stock = null;
        try {
            stock = JsonFastUtil.parseObject(stockString, Stock.class);
        } catch (Exception e) {
            throw new BizException(Public.ERROR_700);
        }
        return stockService.update(stock);
    }

    /**
     * 删除
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete.do")
    public @ResponseBody
    RequestResultVO delete(HttpServletRequest request) {
        String stockIdsString = request.getParameter("stockIds");
        List<Integer> stockIds;
        try {
            stockIds = CommonUtils.idsArrayToList(stockIdsString);
        } catch (Exception e) {
            throw new BizException(Public.ERROR_700);
        }
        return stockService.delete(stockIds);
    }

    /**
     * 分页查询
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getByPage.do")
    public @ResponseBody
    Object getByPage(HttpServletRequest request) {
        String keys = request.getParameter("keys");
        Integer length = Integer.parseInt(request.getParameter("length"));
        Integer start = Integer.parseInt(request.getParameter("start"));
        return stockService.getByPage(keys, length, start);
    }

}

package com.stock.dao.mapper.stock;

import com.stock.dao.model.stock.StockTradePrice;
import com.stock.dao.model.stock.StockTradePriceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockTradePriceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_trade_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int countByExample(StockTradePriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_trade_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int deleteByExample(StockTradePriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_trade_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int deleteByPrimaryKey(Integer stockTradePriceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_trade_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int insert(StockTradePrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_trade_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int insertSelective(StockTradePrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_trade_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    List<StockTradePrice> selectByExample(StockTradePriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_trade_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    StockTradePrice selectByPrimaryKey(Integer stockTradePriceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_trade_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int updateByExampleSelective(@Param("record") StockTradePrice record, @Param("example") StockTradePriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_trade_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int updateByExample(@Param("record") StockTradePrice record, @Param("example") StockTradePriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_trade_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int updateByPrimaryKeySelective(StockTradePrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_trade_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int updateByPrimaryKey(StockTradePrice record);
}
package com.stock.dao.mapper.stock;

import com.stock.dao.model.stock.BuyerEntrustPriceQueue;
import com.stock.dao.model.stock.BuyerEntrustPriceQueueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BuyerEntrustPriceQueueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price_queue
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int countByExample(BuyerEntrustPriceQueueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price_queue
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int deleteByExample(BuyerEntrustPriceQueueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price_queue
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int deleteByPrimaryKey(Integer buyerEntrustPriceQueueId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price_queue
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int insert(BuyerEntrustPriceQueue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price_queue
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int insertSelective(BuyerEntrustPriceQueue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price_queue
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    List<BuyerEntrustPriceQueue> selectByExample(BuyerEntrustPriceQueueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price_queue
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    BuyerEntrustPriceQueue selectByPrimaryKey(Integer buyerEntrustPriceQueueId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price_queue
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int updateByExampleSelective(@Param("record") BuyerEntrustPriceQueue record, @Param("example") BuyerEntrustPriceQueueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price_queue
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int updateByExample(@Param("record") BuyerEntrustPriceQueue record, @Param("example") BuyerEntrustPriceQueueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price_queue
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int updateByPrimaryKeySelective(BuyerEntrustPriceQueue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price_queue
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int updateByPrimaryKey(BuyerEntrustPriceQueue record);
}
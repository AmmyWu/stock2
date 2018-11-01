package com.stock.dao.mapper.stock;

import com.stock.dao.model.stock.BuyerEntrustPrice;
import com.stock.dao.model.stock.BuyerEntrustPriceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BuyerEntrustPriceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int countByExample(BuyerEntrustPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int deleteByExample(BuyerEntrustPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int deleteByPrimaryKey(Integer buyerEntrustPriceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int insert(BuyerEntrustPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int insertSelective(BuyerEntrustPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    List<BuyerEntrustPrice> selectByExample(BuyerEntrustPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    BuyerEntrustPrice selectByPrimaryKey(Integer buyerEntrustPriceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int updateByExampleSelective(@Param("record") BuyerEntrustPrice record, @Param("example") BuyerEntrustPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int updateByExample(@Param("record") BuyerEntrustPrice record, @Param("example") BuyerEntrustPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int updateByPrimaryKeySelective(BuyerEntrustPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_entrust_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int updateByPrimaryKey(BuyerEntrustPrice record);
}
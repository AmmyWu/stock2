package com.stock.dao.mapper.stock;

import com.stock.dao.model.stock.SellerHistoryEntrustRecord;
import com.stock.dao.model.stock.SellerHistoryEntrustRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SellerHistoryEntrustRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seller_history_entrust_record
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int countByExample(SellerHistoryEntrustRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seller_history_entrust_record
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int deleteByExample(SellerHistoryEntrustRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seller_history_entrust_record
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int deleteByPrimaryKey(Integer sellerHistoryEntrustRecordId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seller_history_entrust_record
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int insert(SellerHistoryEntrustRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seller_history_entrust_record
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int insertSelective(SellerHistoryEntrustRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seller_history_entrust_record
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    List<SellerHistoryEntrustRecord> selectByExample(SellerHistoryEntrustRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seller_history_entrust_record
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    SellerHistoryEntrustRecord selectByPrimaryKey(Integer sellerHistoryEntrustRecordId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seller_history_entrust_record
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int updateByExampleSelective(@Param("record") SellerHistoryEntrustRecord record, @Param("example") SellerHistoryEntrustRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seller_history_entrust_record
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int updateByExample(@Param("record") SellerHistoryEntrustRecord record, @Param("example") SellerHistoryEntrustRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seller_history_entrust_record
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int updateByPrimaryKeySelective(SellerHistoryEntrustRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seller_history_entrust_record
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    int updateByPrimaryKey(SellerHistoryEntrustRecord record);
}
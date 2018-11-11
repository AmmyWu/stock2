package com.stock.dao.model.stock;

import java.io.Serializable;
import java.util.Date;

public class BuyerHistoryEntrustRecord implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column buyer_history_entrust_record.buyer_history_entrust_record_id
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    private Integer buyerHistoryEntrustRecordId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column buyer_history_entrust_record.user_id
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column buyer_history_entrust_record.stock_id
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    private Integer stockId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column buyer_history_entrust_record.entrust_date
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    private Date entrustDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column buyer_history_entrust_record.entrust_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    private Double entrustPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column buyer_history_entrust_record.entrust_num
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    private Integer entrustNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column buyer_history_entrust_record.deal_num
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    private Integer dealNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column buyer_history_entrust_record.deal_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    private Double dealPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column buyer_history_entrust_record.deal_date
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    private Date dealDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column buyer_history_entrust_record.status
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column buyer_history_entrust_record.creator
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    private Integer creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column buyer_history_entrust_record.create_time
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column buyer_history_entrust_record.amender
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    private Integer amender;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column buyer_history_entrust_record.amend_time
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    private Date amendTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column buyer_history_entrust_record.access_group
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    private Integer accessGroup;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table buyer_history_entrust_record
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column buyer_history_entrust_record.buyer_history_entrust_record_id
     *
     * @return the value of buyer_history_entrust_record.buyer_history_entrust_record_id
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public Integer getBuyerHistoryEntrustRecordId() {
        return buyerHistoryEntrustRecordId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column buyer_history_entrust_record.buyer_history_entrust_record_id
     *
     * @param buyerHistoryEntrustRecordId the value for buyer_history_entrust_record.buyer_history_entrust_record_id
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void setBuyerHistoryEntrustRecordId(Integer buyerHistoryEntrustRecordId) {
        this.buyerHistoryEntrustRecordId = buyerHistoryEntrustRecordId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column buyer_history_entrust_record.user_id
     *
     * @return the value of buyer_history_entrust_record.user_id
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column buyer_history_entrust_record.user_id
     *
     * @param userId the value for buyer_history_entrust_record.user_id
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column buyer_history_entrust_record.stock_id
     *
     * @return the value of buyer_history_entrust_record.stock_id
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public Integer getStockId() {
        return stockId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column buyer_history_entrust_record.stock_id
     *
     * @param stockId the value for buyer_history_entrust_record.stock_id
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column buyer_history_entrust_record.entrust_date
     *
     * @return the value of buyer_history_entrust_record.entrust_date
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public Date getEntrustDate() {
        return entrustDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column buyer_history_entrust_record.entrust_date
     *
     * @param entrustDate the value for buyer_history_entrust_record.entrust_date
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void setEntrustDate(Date entrustDate) {
        this.entrustDate = entrustDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column buyer_history_entrust_record.entrust_price
     *
     * @return the value of buyer_history_entrust_record.entrust_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public Double getEntrustPrice() {
        return entrustPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column buyer_history_entrust_record.entrust_price
     *
     * @param entrustPrice the value for buyer_history_entrust_record.entrust_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void setEntrustPrice(Double entrustPrice) {
        this.entrustPrice = entrustPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column buyer_history_entrust_record.entrust_num
     *
     * @return the value of buyer_history_entrust_record.entrust_num
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public Integer getEntrustNum() {
        return entrustNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column buyer_history_entrust_record.entrust_num
     *
     * @param entrustNum the value for buyer_history_entrust_record.entrust_num
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void setEntrustNum(Integer entrustNum) {
        this.entrustNum = entrustNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column buyer_history_entrust_record.deal_num
     *
     * @return the value of buyer_history_entrust_record.deal_num
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public Integer getDealNum() {
        return dealNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column buyer_history_entrust_record.deal_num
     *
     * @param dealNum the value for buyer_history_entrust_record.deal_num
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void setDealNum(Integer dealNum) {
        this.dealNum = dealNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column buyer_history_entrust_record.deal_price
     *
     * @return the value of buyer_history_entrust_record.deal_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public Double getDealPrice() {
        return dealPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column buyer_history_entrust_record.deal_price
     *
     * @param dealPrice the value for buyer_history_entrust_record.deal_price
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void setDealPrice(Double dealPrice) {
        this.dealPrice = dealPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column buyer_history_entrust_record.deal_date
     *
     * @return the value of buyer_history_entrust_record.deal_date
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public Date getDealDate() {
        return dealDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column buyer_history_entrust_record.deal_date
     *
     * @param dealDate the value for buyer_history_entrust_record.deal_date
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column buyer_history_entrust_record.status
     *
     * @return the value of buyer_history_entrust_record.status
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column buyer_history_entrust_record.status
     *
     * @param status the value for buyer_history_entrust_record.status
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column buyer_history_entrust_record.creator
     *
     * @return the value of buyer_history_entrust_record.creator
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column buyer_history_entrust_record.creator
     *
     * @param creator the value for buyer_history_entrust_record.creator
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column buyer_history_entrust_record.create_time
     *
     * @return the value of buyer_history_entrust_record.create_time
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column buyer_history_entrust_record.create_time
     *
     * @param createTime the value for buyer_history_entrust_record.create_time
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column buyer_history_entrust_record.amender
     *
     * @return the value of buyer_history_entrust_record.amender
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public Integer getAmender() {
        return amender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column buyer_history_entrust_record.amender
     *
     * @param amender the value for buyer_history_entrust_record.amender
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void setAmender(Integer amender) {
        this.amender = amender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column buyer_history_entrust_record.amend_time
     *
     * @return the value of buyer_history_entrust_record.amend_time
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public Date getAmendTime() {
        return amendTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column buyer_history_entrust_record.amend_time
     *
     * @param amendTime the value for buyer_history_entrust_record.amend_time
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void setAmendTime(Date amendTime) {
        this.amendTime = amendTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column buyer_history_entrust_record.access_group
     *
     * @return the value of buyer_history_entrust_record.access_group
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public Integer getAccessGroup() {
        return accessGroup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column buyer_history_entrust_record.access_group
     *
     * @param accessGroup the value for buyer_history_entrust_record.access_group
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void setAccessGroup(Integer accessGroup) {
        this.accessGroup = accessGroup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_history_entrust_record
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        BuyerHistoryEntrustRecord other = (BuyerHistoryEntrustRecord) that;
        return (this.getBuyerHistoryEntrustRecordId() == null ? other.getBuyerHistoryEntrustRecordId() == null : this.getBuyerHistoryEntrustRecordId().equals(other.getBuyerHistoryEntrustRecordId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getStockId() == null ? other.getStockId() == null : this.getStockId().equals(other.getStockId()))
            && (this.getEntrustDate() == null ? other.getEntrustDate() == null : this.getEntrustDate().equals(other.getEntrustDate()))
            && (this.getEntrustPrice() == null ? other.getEntrustPrice() == null : this.getEntrustPrice().equals(other.getEntrustPrice()))
            && (this.getEntrustNum() == null ? other.getEntrustNum() == null : this.getEntrustNum().equals(other.getEntrustNum()))
            && (this.getDealNum() == null ? other.getDealNum() == null : this.getDealNum().equals(other.getDealNum()))
            && (this.getDealPrice() == null ? other.getDealPrice() == null : this.getDealPrice().equals(other.getDealPrice()))
            && (this.getDealDate() == null ? other.getDealDate() == null : this.getDealDate().equals(other.getDealDate()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getAmender() == null ? other.getAmender() == null : this.getAmender().equals(other.getAmender()))
            && (this.getAmendTime() == null ? other.getAmendTime() == null : this.getAmendTime().equals(other.getAmendTime()))
            && (this.getAccessGroup() == null ? other.getAccessGroup() == null : this.getAccessGroup().equals(other.getAccessGroup()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_history_entrust_record
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBuyerHistoryEntrustRecordId() == null) ? 0 : getBuyerHistoryEntrustRecordId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getStockId() == null) ? 0 : getStockId().hashCode());
        result = prime * result + ((getEntrustDate() == null) ? 0 : getEntrustDate().hashCode());
        result = prime * result + ((getEntrustPrice() == null) ? 0 : getEntrustPrice().hashCode());
        result = prime * result + ((getEntrustNum() == null) ? 0 : getEntrustNum().hashCode());
        result = prime * result + ((getDealNum() == null) ? 0 : getDealNum().hashCode());
        result = prime * result + ((getDealPrice() == null) ? 0 : getDealPrice().hashCode());
        result = prime * result + ((getDealDate() == null) ? 0 : getDealDate().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getAmender() == null) ? 0 : getAmender().hashCode());
        result = prime * result + ((getAmendTime() == null) ? 0 : getAmendTime().hashCode());
        result = prime * result + ((getAccessGroup() == null) ? 0 : getAccessGroup().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table buyer_history_entrust_record
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", buyerHistoryEntrustRecordId=").append(buyerHistoryEntrustRecordId);
        sb.append(", userId=").append(userId);
        sb.append(", stockId=").append(stockId);
        sb.append(", entrustDate=").append(entrustDate);
        sb.append(", entrustPrice=").append(entrustPrice);
        sb.append(", entrustNum=").append(entrustNum);
        sb.append(", dealNum=").append(dealNum);
        sb.append(", dealPrice=").append(dealPrice);
        sb.append(", dealDate=").append(dealDate);
        sb.append(", status=").append(status);
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", amender=").append(amender);
        sb.append(", amendTime=").append(amendTime);
        sb.append(", accessGroup=").append(accessGroup);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
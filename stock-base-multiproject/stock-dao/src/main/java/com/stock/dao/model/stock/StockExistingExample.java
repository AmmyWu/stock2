package com.stock.dao.model.stock;

import com.stock.dao.page.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockExistingExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table stock_existing
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table stock_existing
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table stock_existing
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table stock_existing
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    protected Page page;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_existing
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public StockExistingExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_existing
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_existing
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_existing
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_existing
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_existing
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_existing
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_existing
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_existing
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_existing
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_existing
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_existing
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public void setPage(Page page) {
        this.page=page;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock_existing
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public Page getPage() {
        return page;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table stock_existing
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andExistingIdIsNull() {
            addCriterion("existing_id is null");
            return (Criteria) this;
        }

        public Criteria andExistingIdIsNotNull() {
            addCriterion("existing_id is not null");
            return (Criteria) this;
        }

        public Criteria andExistingIdEqualTo(Integer value) {
            addCriterion("existing_id =", value, "existingId");
            return (Criteria) this;
        }

        public Criteria andExistingIdNotEqualTo(Integer value) {
            addCriterion("existing_id <>", value, "existingId");
            return (Criteria) this;
        }

        public Criteria andExistingIdGreaterThan(Integer value) {
            addCriterion("existing_id >", value, "existingId");
            return (Criteria) this;
        }

        public Criteria andExistingIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("existing_id >=", value, "existingId");
            return (Criteria) this;
        }

        public Criteria andExistingIdLessThan(Integer value) {
            addCriterion("existing_id <", value, "existingId");
            return (Criteria) this;
        }

        public Criteria andExistingIdLessThanOrEqualTo(Integer value) {
            addCriterion("existing_id <=", value, "existingId");
            return (Criteria) this;
        }

        public Criteria andExistingIdIn(List<Integer> values) {
            addCriterion("existing_id in", values, "existingId");
            return (Criteria) this;
        }

        public Criteria andExistingIdNotIn(List<Integer> values) {
            addCriterion("existing_id not in", values, "existingId");
            return (Criteria) this;
        }

        public Criteria andExistingIdBetween(Integer value1, Integer value2) {
            addCriterion("existing_id between", value1, value2, "existingId");
            return (Criteria) this;
        }

        public Criteria andExistingIdNotBetween(Integer value1, Integer value2) {
            addCriterion("existing_id not between", value1, value2, "existingId");
            return (Criteria) this;
        }

        public Criteria andStockIdIsNull() {
            addCriterion("stock_id is null");
            return (Criteria) this;
        }

        public Criteria andStockIdIsNotNull() {
            addCriterion("stock_id is not null");
            return (Criteria) this;
        }

        public Criteria andStockIdEqualTo(Integer value) {
            addCriterion("stock_id =", value, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdNotEqualTo(Integer value) {
            addCriterion("stock_id <>", value, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdGreaterThan(Integer value) {
            addCriterion("stock_id >", value, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("stock_id >=", value, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdLessThan(Integer value) {
            addCriterion("stock_id <", value, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdLessThanOrEqualTo(Integer value) {
            addCriterion("stock_id <=", value, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdIn(List<Integer> values) {
            addCriterion("stock_id in", values, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdNotIn(List<Integer> values) {
            addCriterion("stock_id not in", values, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdBetween(Integer value1, Integer value2) {
            addCriterion("stock_id between", value1, value2, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockIdNotBetween(Integer value1, Integer value2) {
            addCriterion("stock_id not between", value1, value2, "stockId");
            return (Criteria) this;
        }

        public Criteria andStockAccountIdIsNull() {
            addCriterion("stock_account_id is null");
            return (Criteria) this;
        }

        public Criteria andStockAccountIdIsNotNull() {
            addCriterion("stock_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andStockAccountIdEqualTo(Integer value) {
            addCriterion("stock_account_id =", value, "stockAccountId");
            return (Criteria) this;
        }

        public Criteria andStockAccountIdNotEqualTo(Integer value) {
            addCriterion("stock_account_id <>", value, "stockAccountId");
            return (Criteria) this;
        }

        public Criteria andStockAccountIdGreaterThan(Integer value) {
            addCriterion("stock_account_id >", value, "stockAccountId");
            return (Criteria) this;
        }

        public Criteria andStockAccountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("stock_account_id >=", value, "stockAccountId");
            return (Criteria) this;
        }

        public Criteria andStockAccountIdLessThan(Integer value) {
            addCriterion("stock_account_id <", value, "stockAccountId");
            return (Criteria) this;
        }

        public Criteria andStockAccountIdLessThanOrEqualTo(Integer value) {
            addCriterion("stock_account_id <=", value, "stockAccountId");
            return (Criteria) this;
        }

        public Criteria andStockAccountIdIn(List<Integer> values) {
            addCriterion("stock_account_id in", values, "stockAccountId");
            return (Criteria) this;
        }

        public Criteria andStockAccountIdNotIn(List<Integer> values) {
            addCriterion("stock_account_id not in", values, "stockAccountId");
            return (Criteria) this;
        }

        public Criteria andStockAccountIdBetween(Integer value1, Integer value2) {
            addCriterion("stock_account_id between", value1, value2, "stockAccountId");
            return (Criteria) this;
        }

        public Criteria andStockAccountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("stock_account_id not between", value1, value2, "stockAccountId");
            return (Criteria) this;
        }

        public Criteria andStockAvailableSellNumIsNull() {
            addCriterion("stock_available_sell_num is null");
            return (Criteria) this;
        }

        public Criteria andStockAvailableSellNumIsNotNull() {
            addCriterion("stock_available_sell_num is not null");
            return (Criteria) this;
        }

        public Criteria andStockAvailableSellNumEqualTo(Integer value) {
            addCriterion("stock_available_sell_num =", value, "stockAvailableSellNum");
            return (Criteria) this;
        }

        public Criteria andStockAvailableSellNumNotEqualTo(Integer value) {
            addCriterion("stock_available_sell_num <>", value, "stockAvailableSellNum");
            return (Criteria) this;
        }

        public Criteria andStockAvailableSellNumGreaterThan(Integer value) {
            addCriterion("stock_available_sell_num >", value, "stockAvailableSellNum");
            return (Criteria) this;
        }

        public Criteria andStockAvailableSellNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("stock_available_sell_num >=", value, "stockAvailableSellNum");
            return (Criteria) this;
        }

        public Criteria andStockAvailableSellNumLessThan(Integer value) {
            addCriterion("stock_available_sell_num <", value, "stockAvailableSellNum");
            return (Criteria) this;
        }

        public Criteria andStockAvailableSellNumLessThanOrEqualTo(Integer value) {
            addCriterion("stock_available_sell_num <=", value, "stockAvailableSellNum");
            return (Criteria) this;
        }

        public Criteria andStockAvailableSellNumIn(List<Integer> values) {
            addCriterion("stock_available_sell_num in", values, "stockAvailableSellNum");
            return (Criteria) this;
        }

        public Criteria andStockAvailableSellNumNotIn(List<Integer> values) {
            addCriterion("stock_available_sell_num not in", values, "stockAvailableSellNum");
            return (Criteria) this;
        }

        public Criteria andStockAvailableSellNumBetween(Integer value1, Integer value2) {
            addCriterion("stock_available_sell_num between", value1, value2, "stockAvailableSellNum");
            return (Criteria) this;
        }

        public Criteria andStockAvailableSellNumNotBetween(Integer value1, Integer value2) {
            addCriterion("stock_available_sell_num not between", value1, value2, "stockAvailableSellNum");
            return (Criteria) this;
        }

        public Criteria andStockOwnNumIsNull() {
            addCriterion("stock_own_num is null");
            return (Criteria) this;
        }

        public Criteria andStockOwnNumIsNotNull() {
            addCriterion("stock_own_num is not null");
            return (Criteria) this;
        }

        public Criteria andStockOwnNumEqualTo(Integer value) {
            addCriterion("stock_own_num =", value, "stockOwnNum");
            return (Criteria) this;
        }

        public Criteria andStockOwnNumNotEqualTo(Integer value) {
            addCriterion("stock_own_num <>", value, "stockOwnNum");
            return (Criteria) this;
        }

        public Criteria andStockOwnNumGreaterThan(Integer value) {
            addCriterion("stock_own_num >", value, "stockOwnNum");
            return (Criteria) this;
        }

        public Criteria andStockOwnNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("stock_own_num >=", value, "stockOwnNum");
            return (Criteria) this;
        }

        public Criteria andStockOwnNumLessThan(Integer value) {
            addCriterion("stock_own_num <", value, "stockOwnNum");
            return (Criteria) this;
        }

        public Criteria andStockOwnNumLessThanOrEqualTo(Integer value) {
            addCriterion("stock_own_num <=", value, "stockOwnNum");
            return (Criteria) this;
        }

        public Criteria andStockOwnNumIn(List<Integer> values) {
            addCriterion("stock_own_num in", values, "stockOwnNum");
            return (Criteria) this;
        }

        public Criteria andStockOwnNumNotIn(List<Integer> values) {
            addCriterion("stock_own_num not in", values, "stockOwnNum");
            return (Criteria) this;
        }

        public Criteria andStockOwnNumBetween(Integer value1, Integer value2) {
            addCriterion("stock_own_num between", value1, value2, "stockOwnNum");
            return (Criteria) this;
        }

        public Criteria andStockOwnNumNotBetween(Integer value1, Integer value2) {
            addCriterion("stock_own_num not between", value1, value2, "stockOwnNum");
            return (Criteria) this;
        }

        public Criteria andCostPriceIsNull() {
            addCriterion("cost_price is null");
            return (Criteria) this;
        }

        public Criteria andCostPriceIsNotNull() {
            addCriterion("cost_price is not null");
            return (Criteria) this;
        }

        public Criteria andCostPriceEqualTo(Double value) {
            addCriterion("cost_price =", value, "costPrice");
            return (Criteria) this;
        }

        public Criteria andCostPriceNotEqualTo(Double value) {
            addCriterion("cost_price <>", value, "costPrice");
            return (Criteria) this;
        }

        public Criteria andCostPriceGreaterThan(Double value) {
            addCriterion("cost_price >", value, "costPrice");
            return (Criteria) this;
        }

        public Criteria andCostPriceGreaterThanOrEqualTo(Double value) {
            addCriterion("cost_price >=", value, "costPrice");
            return (Criteria) this;
        }

        public Criteria andCostPriceLessThan(Double value) {
            addCriterion("cost_price <", value, "costPrice");
            return (Criteria) this;
        }

        public Criteria andCostPriceLessThanOrEqualTo(Double value) {
            addCriterion("cost_price <=", value, "costPrice");
            return (Criteria) this;
        }

        public Criteria andCostPriceIn(List<Double> values) {
            addCriterion("cost_price in", values, "costPrice");
            return (Criteria) this;
        }

        public Criteria andCostPriceNotIn(List<Double> values) {
            addCriterion("cost_price not in", values, "costPrice");
            return (Criteria) this;
        }

        public Criteria andCostPriceBetween(Double value1, Double value2) {
            addCriterion("cost_price between", value1, value2, "costPrice");
            return (Criteria) this;
        }

        public Criteria andCostPriceNotBetween(Double value1, Double value2) {
            addCriterion("cost_price not between", value1, value2, "costPrice");
            return (Criteria) this;
        }

        public Criteria andBalanceIdIsNull() {
            addCriterion("balance_id is null");
            return (Criteria) this;
        }

        public Criteria andBalanceIdIsNotNull() {
            addCriterion("balance_id is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceIdEqualTo(Integer value) {
            addCriterion("balance_id =", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdNotEqualTo(Integer value) {
            addCriterion("balance_id <>", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdGreaterThan(Integer value) {
            addCriterion("balance_id >", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("balance_id >=", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdLessThan(Integer value) {
            addCriterion("balance_id <", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdLessThanOrEqualTo(Integer value) {
            addCriterion("balance_id <=", value, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdIn(List<Integer> values) {
            addCriterion("balance_id in", values, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdNotIn(List<Integer> values) {
            addCriterion("balance_id not in", values, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdBetween(Integer value1, Integer value2) {
            addCriterion("balance_id between", value1, value2, "balanceId");
            return (Criteria) this;
        }

        public Criteria andBalanceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("balance_id not between", value1, value2, "balanceId");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(Integer value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(Integer value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(Integer value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(Integer value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(Integer value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(Integer value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<Integer> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<Integer> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(Integer value1, Integer value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(Integer value1, Integer value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andAmenderIsNull() {
            addCriterion("amender is null");
            return (Criteria) this;
        }

        public Criteria andAmenderIsNotNull() {
            addCriterion("amender is not null");
            return (Criteria) this;
        }

        public Criteria andAmenderEqualTo(Integer value) {
            addCriterion("amender =", value, "amender");
            return (Criteria) this;
        }

        public Criteria andAmenderNotEqualTo(Integer value) {
            addCriterion("amender <>", value, "amender");
            return (Criteria) this;
        }

        public Criteria andAmenderGreaterThan(Integer value) {
            addCriterion("amender >", value, "amender");
            return (Criteria) this;
        }

        public Criteria andAmenderGreaterThanOrEqualTo(Integer value) {
            addCriterion("amender >=", value, "amender");
            return (Criteria) this;
        }

        public Criteria andAmenderLessThan(Integer value) {
            addCriterion("amender <", value, "amender");
            return (Criteria) this;
        }

        public Criteria andAmenderLessThanOrEqualTo(Integer value) {
            addCriterion("amender <=", value, "amender");
            return (Criteria) this;
        }

        public Criteria andAmenderIn(List<Integer> values) {
            addCriterion("amender in", values, "amender");
            return (Criteria) this;
        }

        public Criteria andAmenderNotIn(List<Integer> values) {
            addCriterion("amender not in", values, "amender");
            return (Criteria) this;
        }

        public Criteria andAmenderBetween(Integer value1, Integer value2) {
            addCriterion("amender between", value1, value2, "amender");
            return (Criteria) this;
        }

        public Criteria andAmenderNotBetween(Integer value1, Integer value2) {
            addCriterion("amender not between", value1, value2, "amender");
            return (Criteria) this;
        }

        public Criteria andAmendTimeIsNull() {
            addCriterion("amend_time is null");
            return (Criteria) this;
        }

        public Criteria andAmendTimeIsNotNull() {
            addCriterion("amend_time is not null");
            return (Criteria) this;
        }

        public Criteria andAmendTimeEqualTo(Date value) {
            addCriterion("amend_time =", value, "amendTime");
            return (Criteria) this;
        }

        public Criteria andAmendTimeNotEqualTo(Date value) {
            addCriterion("amend_time <>", value, "amendTime");
            return (Criteria) this;
        }

        public Criteria andAmendTimeGreaterThan(Date value) {
            addCriterion("amend_time >", value, "amendTime");
            return (Criteria) this;
        }

        public Criteria andAmendTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("amend_time >=", value, "amendTime");
            return (Criteria) this;
        }

        public Criteria andAmendTimeLessThan(Date value) {
            addCriterion("amend_time <", value, "amendTime");
            return (Criteria) this;
        }

        public Criteria andAmendTimeLessThanOrEqualTo(Date value) {
            addCriterion("amend_time <=", value, "amendTime");
            return (Criteria) this;
        }

        public Criteria andAmendTimeIn(List<Date> values) {
            addCriterion("amend_time in", values, "amendTime");
            return (Criteria) this;
        }

        public Criteria andAmendTimeNotIn(List<Date> values) {
            addCriterion("amend_time not in", values, "amendTime");
            return (Criteria) this;
        }

        public Criteria andAmendTimeBetween(Date value1, Date value2) {
            addCriterion("amend_time between", value1, value2, "amendTime");
            return (Criteria) this;
        }

        public Criteria andAmendTimeNotBetween(Date value1, Date value2) {
            addCriterion("amend_time not between", value1, value2, "amendTime");
            return (Criteria) this;
        }

        public Criteria andAccessGroupIsNull() {
            addCriterion("access_group is null");
            return (Criteria) this;
        }

        public Criteria andAccessGroupIsNotNull() {
            addCriterion("access_group is not null");
            return (Criteria) this;
        }

        public Criteria andAccessGroupEqualTo(Integer value) {
            addCriterion("access_group =", value, "accessGroup");
            return (Criteria) this;
        }

        public Criteria andAccessGroupNotEqualTo(Integer value) {
            addCriterion("access_group <>", value, "accessGroup");
            return (Criteria) this;
        }

        public Criteria andAccessGroupGreaterThan(Integer value) {
            addCriterion("access_group >", value, "accessGroup");
            return (Criteria) this;
        }

        public Criteria andAccessGroupGreaterThanOrEqualTo(Integer value) {
            addCriterion("access_group >=", value, "accessGroup");
            return (Criteria) this;
        }

        public Criteria andAccessGroupLessThan(Integer value) {
            addCriterion("access_group <", value, "accessGroup");
            return (Criteria) this;
        }

        public Criteria andAccessGroupLessThanOrEqualTo(Integer value) {
            addCriterion("access_group <=", value, "accessGroup");
            return (Criteria) this;
        }

        public Criteria andAccessGroupIn(List<Integer> values) {
            addCriterion("access_group in", values, "accessGroup");
            return (Criteria) this;
        }

        public Criteria andAccessGroupNotIn(List<Integer> values) {
            addCriterion("access_group not in", values, "accessGroup");
            return (Criteria) this;
        }

        public Criteria andAccessGroupBetween(Integer value1, Integer value2) {
            addCriterion("access_group between", value1, value2, "accessGroup");
            return (Criteria) this;
        }

        public Criteria andAccessGroupNotBetween(Integer value1, Integer value2) {
            addCriterion("access_group not between", value1, value2, "accessGroup");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table stock_existing
     *
     * @mbggenerated do_not_delete_during_merge Thu Nov 01 14:24:56 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table stock_existing
     *
     * @mbggenerated Thu Nov 01 14:24:56 CST 2018
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
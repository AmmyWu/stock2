package com.stock.dao.model.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.stock.dao.page.Page;

public class SysServerInfoExample {
	
	protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	protected Page page;

	public void setPage(Page page) {
		this.page = page;
	}

	public Page getPage() {
		return page;
	}

	public SysServerInfoExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

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

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andServerInfoIdIsNull() {
			addCriterion("server_info_id is null");
			return (Criteria) this;
		}

		public Criteria andServerInfoIdIsNotNull() {
			addCriterion("server_info_id is not null");
			return (Criteria) this;
		}

		public Criteria andServerInfoIdEqualTo(Integer value) {
			addCriterion("server_info_id =", value, "serverInfoId");
			return (Criteria) this;
		}

		public Criteria andServerInfoIdNotEqualTo(Integer value) {
			addCriterion("server_info_id <>", value, "serverInfoId");
			return (Criteria) this;
		}

		public Criteria andServerInfoIdGreaterThan(Integer value) {
			addCriterion("server_info_id >", value, "serverInfoId");
			return (Criteria) this;
		}

		public Criteria andServerInfoIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("server_info_id >=", value, "serverInfoId");
			return (Criteria) this;
		}

		public Criteria andServerInfoIdLessThan(Integer value) {
			addCriterion("server_info_id <", value, "serverInfoId");
			return (Criteria) this;
		}

		public Criteria andServerInfoIdLessThanOrEqualTo(Integer value) {
			addCriterion("server_info_id <=", value, "serverInfoId");
			return (Criteria) this;
		}

		public Criteria andServerInfoIdIn(List<Integer> values) {
			addCriterion("server_info_id in", values, "serverInfoId");
			return (Criteria) this;
		}

		public Criteria andServerInfoIdNotIn(List<Integer> values) {
			addCriterion("server_info_id not in", values, "serverInfoId");
			return (Criteria) this;
		}

		public Criteria andServerInfoIdBetween(Integer value1, Integer value2) {
			addCriterion("server_info_id between", value1, value2,
					"serverInfoId");
			return (Criteria) this;
		}

		public Criteria andServerInfoIdNotBetween(Integer value1, Integer value2) {
			addCriterion("server_info_id not between", value1, value2,
					"serverInfoId");
			return (Criteria) this;
		}

		public Criteria andLogDirectoryIsNull() {
			addCriterion("log_directory is null");
			return (Criteria) this;
		}

		public Criteria andLogDirectoryIsNotNull() {
			addCriterion("log_directory is not null");
			return (Criteria) this;
		}

		public Criteria andLogDirectoryEqualTo(String value) {
			addCriterion("log_directory =", value, "logDirectory");
			return (Criteria) this;
		}

		public Criteria andLogDirectoryNotEqualTo(String value) {
			addCriterion("log_directory <>", value, "logDirectory");
			return (Criteria) this;
		}

		public Criteria andLogDirectoryGreaterThan(String value) {
			addCriterion("log_directory >", value, "logDirectory");
			return (Criteria) this;
		}

		public Criteria andLogDirectoryGreaterThanOrEqualTo(String value) {
			addCriterion("log_directory >=", value, "logDirectory");
			return (Criteria) this;
		}

		public Criteria andLogDirectoryLessThan(String value) {
			addCriterion("log_directory <", value, "logDirectory");
			return (Criteria) this;
		}

		public Criteria andLogDirectoryLessThanOrEqualTo(String value) {
			addCriterion("log_directory <=", value, "logDirectory");
			return (Criteria) this;
		}

		public Criteria andLogDirectoryLike(String value) {
			addCriterion("log_directory like", value, "logDirectory");
			return (Criteria) this;
		}

		public Criteria andLogDirectoryNotLike(String value) {
			addCriterion("log_directory not like", value, "logDirectory");
			return (Criteria) this;
		}

		public Criteria andLogDirectoryIn(List<String> values) {
			addCriterion("log_directory in", values, "logDirectory");
			return (Criteria) this;
		}

		public Criteria andLogDirectoryNotIn(List<String> values) {
			addCriterion("log_directory not in", values, "logDirectory");
			return (Criteria) this;
		}

		public Criteria andLogDirectoryBetween(String value1, String value2) {
			addCriterion("log_directory between", value1, value2,
					"logDirectory");
			return (Criteria) this;
		}

		public Criteria andLogDirectoryNotBetween(String value1, String value2) {
			addCriterion("log_directory not between", value1, value2,
					"logDirectory");
			return (Criteria) this;
		}

		public Criteria andServerIpIsNull() {
			addCriterion("server_ip is null");
			return (Criteria) this;
		}

		public Criteria andServerIpIsNotNull() {
			addCriterion("server_ip is not null");
			return (Criteria) this;
		}

		public Criteria andServerIpEqualTo(String value) {
			addCriterion("server_ip =", value, "serverIp");
			return (Criteria) this;
		}

		public Criteria andServerIpNotEqualTo(String value) {
			addCriterion("server_ip <>", value, "serverIp");
			return (Criteria) this;
		}

		public Criteria andServerIpGreaterThan(String value) {
			addCriterion("server_ip >", value, "serverIp");
			return (Criteria) this;
		}

		public Criteria andServerIpGreaterThanOrEqualTo(String value) {
			addCriterion("server_ip >=", value, "serverIp");
			return (Criteria) this;
		}

		public Criteria andServerIpLessThan(String value) {
			addCriterion("server_ip <", value, "serverIp");
			return (Criteria) this;
		}

		public Criteria andServerIpLessThanOrEqualTo(String value) {
			addCriterion("server_ip <=", value, "serverIp");
			return (Criteria) this;
		}

		public Criteria andServerIpLike(String value) {
			addCriterion("server_ip like", value, "serverIp");
			return (Criteria) this;
		}

		public Criteria andServerIpNotLike(String value) {
			addCriterion("server_ip not like", value, "serverIp");
			return (Criteria) this;
		}

		public Criteria andServerIpIn(List<String> values) {
			addCriterion("server_ip in", values, "serverIp");
			return (Criteria) this;
		}

		public Criteria andServerIpNotIn(List<String> values) {
			addCriterion("server_ip not in", values, "serverIp");
			return (Criteria) this;
		}

		public Criteria andServerIpBetween(String value1, String value2) {
			addCriterion("server_ip between", value1, value2, "serverIp");
			return (Criteria) this;
		}

		public Criteria andServerIpNotBetween(String value1, String value2) {
			addCriterion("server_ip not between", value1, value2, "serverIp");
			return (Criteria) this;
		}

		public Criteria andPortIsNull() {
			addCriterion("port is null");
			return (Criteria) this;
		}

		public Criteria andPortIsNotNull() {
			addCriterion("port is not null");
			return (Criteria) this;
		}

		public Criteria andPortEqualTo(String value) {
			addCriterion("port =", value, "port");
			return (Criteria) this;
		}

		public Criteria andPortNotEqualTo(String value) {
			addCriterion("port <>", value, "port");
			return (Criteria) this;
		}

		public Criteria andPortGreaterThan(String value) {
			addCriterion("port >", value, "port");
			return (Criteria) this;
		}

		public Criteria andPortGreaterThanOrEqualTo(String value) {
			addCriterion("port >=", value, "port");
			return (Criteria) this;
		}

		public Criteria andPortLessThan(String value) {
			addCriterion("port <", value, "port");
			return (Criteria) this;
		}

		public Criteria andPortLessThanOrEqualTo(String value) {
			addCriterion("port <=", value, "port");
			return (Criteria) this;
		}

		public Criteria andPortLike(String value) {
			addCriterion("port like", value, "port");
			return (Criteria) this;
		}

		public Criteria andPortNotLike(String value) {
			addCriterion("port not like", value, "port");
			return (Criteria) this;
		}

		public Criteria andPortIn(List<String> values) {
			addCriterion("port in", values, "port");
			return (Criteria) this;
		}

		public Criteria andPortNotIn(List<String> values) {
			addCriterion("port not in", values, "port");
			return (Criteria) this;
		}

		public Criteria andPortBetween(String value1, String value2) {
			addCriterion("port between", value1, value2, "port");
			return (Criteria) this;
		}

		public Criteria andPortNotBetween(String value1, String value2) {
			addCriterion("port not between", value1, value2, "port");
			return (Criteria) this;
		}

		public Criteria andServerNameIsNull() {
			addCriterion("server_name is null");
			return (Criteria) this;
		}

		public Criteria andServerNameIsNotNull() {
			addCriterion("server_name is not null");
			return (Criteria) this;
		}

		public Criteria andServerNameEqualTo(String value) {
			addCriterion("server_name =", value, "serverName");
			return (Criteria) this;
		}

		public Criteria andServerNameNotEqualTo(String value) {
			addCriterion("server_name <>", value, "serverName");
			return (Criteria) this;
		}

		public Criteria andServerNameGreaterThan(String value) {
			addCriterion("server_name >", value, "serverName");
			return (Criteria) this;
		}

		public Criteria andServerNameGreaterThanOrEqualTo(String value) {
			addCriterion("server_name >=", value, "serverName");
			return (Criteria) this;
		}

		public Criteria andServerNameLessThan(String value) {
			addCriterion("server_name <", value, "serverName");
			return (Criteria) this;
		}

		public Criteria andServerNameLessThanOrEqualTo(String value) {
			addCriterion("server_name <=", value, "serverName");
			return (Criteria) this;
		}

		public Criteria andServerNameLike(String value) {
			addCriterion("server_name like", value, "serverName");
			return (Criteria) this;
		}

		public Criteria andServerNameNotLike(String value) {
			addCriterion("server_name not like", value, "serverName");
			return (Criteria) this;
		}

		public Criteria andServerNameIn(List<String> values) {
			addCriterion("server_name in", values, "serverName");
			return (Criteria) this;
		}

		public Criteria andServerNameNotIn(List<String> values) {
			addCriterion("server_name not in", values, "serverName");
			return (Criteria) this;
		}

		public Criteria andServerNameBetween(String value1, String value2) {
			addCriterion("server_name between", value1, value2, "serverName");
			return (Criteria) this;
		}

		public Criteria andServerNameNotBetween(String value1, String value2) {
			addCriterion("server_name not between", value1, value2,
					"serverName");
			return (Criteria) this;
		}

		public Criteria andDescriptionIsNull() {
			addCriterion("description is null");
			return (Criteria) this;
		}

		public Criteria andDescriptionIsNotNull() {
			addCriterion("description is not null");
			return (Criteria) this;
		}

		public Criteria andDescriptionEqualTo(String value) {
			addCriterion("description =", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotEqualTo(String value) {
			addCriterion("description <>", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionGreaterThan(String value) {
			addCriterion("description >", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
			addCriterion("description >=", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionLessThan(String value) {
			addCriterion("description <", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionLessThanOrEqualTo(String value) {
			addCriterion("description <=", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionLike(String value) {
			addCriterion("description like", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotLike(String value) {
			addCriterion("description not like", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionIn(List<String> values) {
			addCriterion("description in", values, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotIn(List<String> values) {
			addCriterion("description not in", values, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionBetween(String value1, String value2) {
			addCriterion("description between", value1, value2, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotBetween(String value1, String value2) {
			addCriterion("description not between", value1, value2,
					"description");
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
			addCriterion("create_time not between", value1, value2,
					"createTime");
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
			addCriterion("access_group not between", value1, value2,
					"accessGroup");
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

		public Criteria andRecordVersionIsNull() {
			addCriterion("record_version is null");
			return (Criteria) this;
		}

		public Criteria andRecordVersionIsNotNull() {
			addCriterion("record_version is not null");
			return (Criteria) this;
		}

		public Criteria andRecordVersionEqualTo(Integer value) {
			addCriterion("record_version =", value, "recordVersion");
			return (Criteria) this;
		}

		public Criteria andRecordVersionNotEqualTo(Integer value) {
			addCriterion("record_version <>", value, "recordVersion");
			return (Criteria) this;
		}

		public Criteria andRecordVersionGreaterThan(Integer value) {
			addCriterion("record_version >", value, "recordVersion");
			return (Criteria) this;
		}

		public Criteria andRecordVersionGreaterThanOrEqualTo(Integer value) {
			addCriterion("record_version >=", value, "recordVersion");
			return (Criteria) this;
		}

		public Criteria andRecordVersionLessThan(Integer value) {
			addCriterion("record_version <", value, "recordVersion");
			return (Criteria) this;
		}

		public Criteria andRecordVersionLessThanOrEqualTo(Integer value) {
			addCriterion("record_version <=", value, "recordVersion");
			return (Criteria) this;
		}

		public Criteria andRecordVersionIn(List<Integer> values) {
			addCriterion("record_version in", values, "recordVersion");
			return (Criteria) this;
		}

		public Criteria andRecordVersionNotIn(List<Integer> values) {
			addCriterion("record_version not in", values, "recordVersion");
			return (Criteria) this;
		}

		public Criteria andRecordVersionBetween(Integer value1, Integer value2) {
			addCriterion("record_version between", value1, value2,
					"recordVersion");
			return (Criteria) this;
		}

		public Criteria andRecordVersionNotBetween(Integer value1,
				Integer value2) {
			addCriterion("record_version not between", value1, value2,
					"recordVersion");
			return (Criteria) this;
		}

		public Criteria andIsDeletedIsNull() {
			addCriterion("is_deleted is null");
			return (Criteria) this;
		}

		public Criteria andIsDeletedIsNotNull() {
			addCriterion("is_deleted is not null");
			return (Criteria) this;
		}

		public Criteria andIsDeletedEqualTo(Integer value) {
			addCriterion("is_deleted =", value, "isDeleted");
			return (Criteria) this;
		}

		public Criteria andIsDeletedNotEqualTo(Integer value) {
			addCriterion("is_deleted <>", value, "isDeleted");
			return (Criteria) this;
		}

		public Criteria andIsDeletedGreaterThan(Integer value) {
			addCriterion("is_deleted >", value, "isDeleted");
			return (Criteria) this;
		}

		public Criteria andIsDeletedGreaterThanOrEqualTo(Integer value) {
			addCriterion("is_deleted >=", value, "isDeleted");
			return (Criteria) this;
		}

		public Criteria andIsDeletedLessThan(Integer value) {
			addCriterion("is_deleted <", value, "isDeleted");
			return (Criteria) this;
		}

		public Criteria andIsDeletedLessThanOrEqualTo(Integer value) {
			addCriterion("is_deleted <=", value, "isDeleted");
			return (Criteria) this;
		}

		public Criteria andIsDeletedIn(List<Integer> values) {
			addCriterion("is_deleted in", values, "isDeleted");
			return (Criteria) this;
		}

		public Criteria andIsDeletedNotIn(List<Integer> values) {
			addCriterion("is_deleted not in", values, "isDeleted");
			return (Criteria) this;
		}

		public Criteria andIsDeletedBetween(Integer value1, Integer value2) {
			addCriterion("is_deleted between", value1, value2, "isDeleted");
			return (Criteria) this;
		}

		public Criteria andIsDeletedNotBetween(Integer value1, Integer value2) {
			addCriterion("is_deleted not between", value1, value2, "isDeleted");
			return (Criteria) this;
		}
	}

	public static class Criteria extends GeneratedCriteria {

		protected Criteria() {
			super();
		}
	}

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

		protected Criterion(String condition, Object value, Object secondValue,
				String typeHandler) {
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
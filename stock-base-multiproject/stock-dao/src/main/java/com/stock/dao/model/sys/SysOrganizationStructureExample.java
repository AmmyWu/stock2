package com.stock.dao.model.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.stock.dao.page.Page;

public class SysOrganizationStructureExample {
	
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

	public SysOrganizationStructureExample() {
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

		public Criteria andOrganizationStructureIdIsNull() {
			addCriterion("organization_structure_id is null");
			return (Criteria) this;
		}

		public Criteria andOrganizationStructureIdIsNotNull() {
			addCriterion("organization_structure_id is not null");
			return (Criteria) this;
		}

		public Criteria andOrganizationStructureIdEqualTo(Integer value) {
			addCriterion("organization_structure_id =", value,
					"organizationStructureId");
			return (Criteria) this;
		}

		public Criteria andOrganizationStructureIdNotEqualTo(Integer value) {
			addCriterion("organization_structure_id <>", value,
					"organizationStructureId");
			return (Criteria) this;
		}

		public Criteria andOrganizationStructureIdGreaterThan(Integer value) {
			addCriterion("organization_structure_id >", value,
					"organizationStructureId");
			return (Criteria) this;
		}

		public Criteria andOrganizationStructureIdGreaterThanOrEqualTo(
				Integer value) {
			addCriterion("organization_structure_id >=", value,
					"organizationStructureId");
			return (Criteria) this;
		}

		public Criteria andOrganizationStructureIdLessThan(Integer value) {
			addCriterion("organization_structure_id <", value,
					"organizationStructureId");
			return (Criteria) this;
		}

		public Criteria andOrganizationStructureIdLessThanOrEqualTo(
				Integer value) {
			addCriterion("organization_structure_id <=", value,
					"organizationStructureId");
			return (Criteria) this;
		}

		public Criteria andOrganizationStructureIdIn(List<Integer> values) {
			addCriterion("organization_structure_id in", values,
					"organizationStructureId");
			return (Criteria) this;
		}

		public Criteria andOrganizationStructureIdNotIn(List<Integer> values) {
			addCriterion("organization_structure_id not in", values,
					"organizationStructureId");
			return (Criteria) this;
		}

		public Criteria andOrganizationStructureIdBetween(Integer value1,
				Integer value2) {
			addCriterion("organization_structure_id between", value1, value2,
					"organizationStructureId");
			return (Criteria) this;
		}

		public Criteria andOrganizationStructureIdNotBetween(Integer value1,
				Integer value2) {
			addCriterion("organization_structure_id not between", value1,
					value2, "organizationStructureId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdIsNull() {
			addCriterion("superior_id is null");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdIsNotNull() {
			addCriterion("superior_id is not null");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdEqualTo(Integer value) {
			addCriterion("superior_id =", value, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdNotEqualTo(Integer value) {
			addCriterion("superior_id <>", value, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdGreaterThan(Integer value) {
			addCriterion("superior_id >", value, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("superior_id >=", value, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdLessThan(Integer value) {
			addCriterion("superior_id <", value, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdLessThanOrEqualTo(Integer value) {
			addCriterion("superior_id <=", value, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdIn(List<Integer> values) {
			addCriterion("superior_id in", values, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdNotIn(List<Integer> values) {
			addCriterion("superior_id not in", values, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdBetween(Integer value1, Integer value2) {
			addCriterion("superior_id between", value1, value2, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdNotBetween(Integer value1, Integer value2) {
			addCriterion("superior_id not between", value1, value2,
					"superiorId");
			return (Criteria) this;
		}

		public Criteria andCodeCommonSetIdIsNull() {
			addCriterion("code_common_set_id is null");
			return (Criteria) this;
		}

		public Criteria andCodeCommonSetIdIsNotNull() {
			addCriterion("code_common_set_id is not null");
			return (Criteria) this;
		}

		public Criteria andCodeCommonSetIdEqualTo(Integer value) {
			addCriterion("code_common_set_id =", value, "codeCommonSetId");
			return (Criteria) this;
		}

		public Criteria andCodeCommonSetIdNotEqualTo(Integer value) {
			addCriterion("code_common_set_id <>", value, "codeCommonSetId");
			return (Criteria) this;
		}

		public Criteria andCodeCommonSetIdGreaterThan(Integer value) {
			addCriterion("code_common_set_id >", value, "codeCommonSetId");
			return (Criteria) this;
		}

		public Criteria andCodeCommonSetIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("code_common_set_id >=", value, "codeCommonSetId");
			return (Criteria) this;
		}

		public Criteria andCodeCommonSetIdLessThan(Integer value) {
			addCriterion("code_common_set_id <", value, "codeCommonSetId");
			return (Criteria) this;
		}

		public Criteria andCodeCommonSetIdLessThanOrEqualTo(Integer value) {
			addCriterion("code_common_set_id <=", value, "codeCommonSetId");
			return (Criteria) this;
		}

		public Criteria andCodeCommonSetIdIn(List<Integer> values) {
			addCriterion("code_common_set_id in", values, "codeCommonSetId");
			return (Criteria) this;
		}

		public Criteria andCodeCommonSetIdNotIn(List<Integer> values) {
			addCriterion("code_common_set_id not in", values, "codeCommonSetId");
			return (Criteria) this;
		}

		public Criteria andCodeCommonSetIdBetween(Integer value1, Integer value2) {
			addCriterion("code_common_set_id between", value1, value2,
					"codeCommonSetId");
			return (Criteria) this;
		}

		public Criteria andCodeCommonSetIdNotBetween(Integer value1,
				Integer value2) {
			addCriterion("code_common_set_id not between", value1, value2,
					"codeCommonSetId");
			return (Criteria) this;
		}

		public Criteria andCodeNameIsNull() {
			addCriterion("code_name is null");
			return (Criteria) this;
		}

		public Criteria andCodeNameIsNotNull() {
			addCriterion("code_name is not null");
			return (Criteria) this;
		}

		public Criteria andCodeNameEqualTo(String value) {
			addCriterion("code_name =", value, "codeName");
			return (Criteria) this;
		}

		public Criteria andCodeNameNotEqualTo(String value) {
			addCriterion("code_name <>", value, "codeName");
			return (Criteria) this;
		}

		public Criteria andCodeNameGreaterThan(String value) {
			addCriterion("code_name >", value, "codeName");
			return (Criteria) this;
		}

		public Criteria andCodeNameGreaterThanOrEqualTo(String value) {
			addCriterion("code_name >=", value, "codeName");
			return (Criteria) this;
		}

		public Criteria andCodeNameLessThan(String value) {
			addCriterion("code_name <", value, "codeName");
			return (Criteria) this;
		}

		public Criteria andCodeNameLessThanOrEqualTo(String value) {
			addCriterion("code_name <=", value, "codeName");
			return (Criteria) this;
		}

		public Criteria andCodeNameLike(String value) {
			addCriterion("code_name like", value, "codeName");
			return (Criteria) this;
		}

		public Criteria andCodeNameNotLike(String value) {
			addCriterion("code_name not like", value, "codeName");
			return (Criteria) this;
		}

		public Criteria andCodeNameIn(List<String> values) {
			addCriterion("code_name in", values, "codeName");
			return (Criteria) this;
		}

		public Criteria andCodeNameNotIn(List<String> values) {
			addCriterion("code_name not in", values, "codeName");
			return (Criteria) this;
		}

		public Criteria andCodeNameBetween(String value1, String value2) {
			addCriterion("code_name between", value1, value2, "codeName");
			return (Criteria) this;
		}

		public Criteria andCodeNameNotBetween(String value1, String value2) {
			addCriterion("code_name not between", value1, value2, "codeName");
			return (Criteria) this;
		}

		public Criteria andTypeIsNull() {
			addCriterion("type is null");
			return (Criteria) this;
		}

		public Criteria andTypeIsNotNull() {
			addCriterion("type is not null");
			return (Criteria) this;
		}

		public Criteria andTypeEqualTo(Integer value) {
			addCriterion("type =", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotEqualTo(Integer value) {
			addCriterion("type <>", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeGreaterThan(Integer value) {
			addCriterion("type >", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
			addCriterion("type >=", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeLessThan(Integer value) {
			addCriterion("type <", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeLessThanOrEqualTo(Integer value) {
			addCriterion("type <=", value, "type");
			return (Criteria) this;
		}

		public Criteria andTypeIn(List<Integer> values) {
			addCriterion("type in", values, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotIn(List<Integer> values) {
			addCriterion("type not in", values, "type");
			return (Criteria) this;
		}

		public Criteria andTypeBetween(Integer value1, Integer value2) {
			addCriterion("type between", value1, value2, "type");
			return (Criteria) this;
		}

		public Criteria andTypeNotBetween(Integer value1, Integer value2) {
			addCriterion("type not between", value1, value2, "type");
			return (Criteria) this;
		}

		public Criteria andNameIsNull() {
			addCriterion("name is null");
			return (Criteria) this;
		}

		public Criteria andNameIsNotNull() {
			addCriterion("name is not null");
			return (Criteria) this;
		}

		public Criteria andNameEqualTo(String value) {
			addCriterion("name =", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameNotEqualTo(String value) {
			addCriterion("name <>", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameGreaterThan(String value) {
			addCriterion("name >", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameGreaterThanOrEqualTo(String value) {
			addCriterion("name >=", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameLessThan(String value) {
			addCriterion("name <", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameLessThanOrEqualTo(String value) {
			addCriterion("name <=", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameLike(String value) {
			addCriterion("name like", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameNotLike(String value) {
			addCriterion("name not like", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameIn(List<String> values) {
			addCriterion("name in", values, "name");
			return (Criteria) this;
		}

		public Criteria andNameNotIn(List<String> values) {
			addCriterion("name not in", values, "name");
			return (Criteria) this;
		}

		public Criteria andNameBetween(String value1, String value2) {
			addCriterion("name between", value1, value2, "name");
			return (Criteria) this;
		}

		public Criteria andNameNotBetween(String value1, String value2) {
			addCriterion("name not between", value1, value2, "name");
			return (Criteria) this;
		}

		public Criteria andOfficeNativeNameIsNull() {
			addCriterion("office_native_name is null");
			return (Criteria) this;
		}

		public Criteria andOfficeNativeNameIsNotNull() {
			addCriterion("office_native_name is not null");
			return (Criteria) this;
		}

		public Criteria andOfficeNativeNameEqualTo(String value) {
			addCriterion("office_native_name =", value, "officeNativeName");
			return (Criteria) this;
		}

		public Criteria andOfficeNativeNameNotEqualTo(String value) {
			addCriterion("office_native_name <>", value, "officeNativeName");
			return (Criteria) this;
		}

		public Criteria andOfficeNativeNameGreaterThan(String value) {
			addCriterion("office_native_name >", value, "officeNativeName");
			return (Criteria) this;
		}

		public Criteria andOfficeNativeNameGreaterThanOrEqualTo(String value) {
			addCriterion("office_native_name >=", value, "officeNativeName");
			return (Criteria) this;
		}

		public Criteria andOfficeNativeNameLessThan(String value) {
			addCriterion("office_native_name <", value, "officeNativeName");
			return (Criteria) this;
		}

		public Criteria andOfficeNativeNameLessThanOrEqualTo(String value) {
			addCriterion("office_native_name <=", value, "officeNativeName");
			return (Criteria) this;
		}

		public Criteria andOfficeNativeNameLike(String value) {
			addCriterion("office_native_name like", value, "officeNativeName");
			return (Criteria) this;
		}

		public Criteria andOfficeNativeNameNotLike(String value) {
			addCriterion("office_native_name not like", value,
					"officeNativeName");
			return (Criteria) this;
		}

		public Criteria andOfficeNativeNameIn(List<String> values) {
			addCriterion("office_native_name in", values, "officeNativeName");
			return (Criteria) this;
		}

		public Criteria andOfficeNativeNameNotIn(List<String> values) {
			addCriterion("office_native_name not in", values,
					"officeNativeName");
			return (Criteria) this;
		}

		public Criteria andOfficeNativeNameBetween(String value1, String value2) {
			addCriterion("office_native_name between", value1, value2,
					"officeNativeName");
			return (Criteria) this;
		}

		public Criteria andOfficeNativeNameNotBetween(String value1,
				String value2) {
			addCriterion("office_native_name not between", value1, value2,
					"officeNativeName");
			return (Criteria) this;
		}

		public Criteria andAbbrevIsNull() {
			addCriterion("abbrev is null");
			return (Criteria) this;
		}

		public Criteria andAbbrevIsNotNull() {
			addCriterion("abbrev is not null");
			return (Criteria) this;
		}

		public Criteria andAbbrevEqualTo(String value) {
			addCriterion("abbrev =", value, "abbrev");
			return (Criteria) this;
		}

		public Criteria andAbbrevNotEqualTo(String value) {
			addCriterion("abbrev <>", value, "abbrev");
			return (Criteria) this;
		}

		public Criteria andAbbrevGreaterThan(String value) {
			addCriterion("abbrev >", value, "abbrev");
			return (Criteria) this;
		}

		public Criteria andAbbrevGreaterThanOrEqualTo(String value) {
			addCriterion("abbrev >=", value, "abbrev");
			return (Criteria) this;
		}

		public Criteria andAbbrevLessThan(String value) {
			addCriterion("abbrev <", value, "abbrev");
			return (Criteria) this;
		}

		public Criteria andAbbrevLessThanOrEqualTo(String value) {
			addCriterion("abbrev <=", value, "abbrev");
			return (Criteria) this;
		}

		public Criteria andAbbrevLike(String value) {
			addCriterion("abbrev like", value, "abbrev");
			return (Criteria) this;
		}

		public Criteria andAbbrevNotLike(String value) {
			addCriterion("abbrev not like", value, "abbrev");
			return (Criteria) this;
		}

		public Criteria andAbbrevIn(List<String> values) {
			addCriterion("abbrev in", values, "abbrev");
			return (Criteria) this;
		}

		public Criteria andAbbrevNotIn(List<String> values) {
			addCriterion("abbrev not in", values, "abbrev");
			return (Criteria) this;
		}

		public Criteria andAbbrevBetween(String value1, String value2) {
			addCriterion("abbrev between", value1, value2, "abbrev");
			return (Criteria) this;
		}

		public Criteria andAbbrevNotBetween(String value1, String value2) {
			addCriterion("abbrev not between", value1, value2, "abbrev");
			return (Criteria) this;
		}

		public Criteria andBusinessRegisterNoIsNull() {
			addCriterion("business_register_no is null");
			return (Criteria) this;
		}

		public Criteria andBusinessRegisterNoIsNotNull() {
			addCriterion("business_register_no is not null");
			return (Criteria) this;
		}

		public Criteria andBusinessRegisterNoEqualTo(String value) {
			addCriterion("business_register_no =", value, "businessRegisterNo");
			return (Criteria) this;
		}

		public Criteria andBusinessRegisterNoNotEqualTo(String value) {
			addCriterion("business_register_no <>", value, "businessRegisterNo");
			return (Criteria) this;
		}

		public Criteria andBusinessRegisterNoGreaterThan(String value) {
			addCriterion("business_register_no >", value, "businessRegisterNo");
			return (Criteria) this;
		}

		public Criteria andBusinessRegisterNoGreaterThanOrEqualTo(String value) {
			addCriterion("business_register_no >=", value, "businessRegisterNo");
			return (Criteria) this;
		}

		public Criteria andBusinessRegisterNoLessThan(String value) {
			addCriterion("business_register_no <", value, "businessRegisterNo");
			return (Criteria) this;
		}

		public Criteria andBusinessRegisterNoLessThanOrEqualTo(String value) {
			addCriterion("business_register_no <=", value, "businessRegisterNo");
			return (Criteria) this;
		}

		public Criteria andBusinessRegisterNoLike(String value) {
			addCriterion("business_register_no like", value,
					"businessRegisterNo");
			return (Criteria) this;
		}

		public Criteria andBusinessRegisterNoNotLike(String value) {
			addCriterion("business_register_no not like", value,
					"businessRegisterNo");
			return (Criteria) this;
		}

		public Criteria andBusinessRegisterNoIn(List<String> values) {
			addCriterion("business_register_no in", values,
					"businessRegisterNo");
			return (Criteria) this;
		}

		public Criteria andBusinessRegisterNoNotIn(List<String> values) {
			addCriterion("business_register_no not in", values,
					"businessRegisterNo");
			return (Criteria) this;
		}

		public Criteria andBusinessRegisterNoBetween(String value1,
				String value2) {
			addCriterion("business_register_no between", value1, value2,
					"businessRegisterNo");
			return (Criteria) this;
		}

		public Criteria andBusinessRegisterNoNotBetween(String value1,
				String value2) {
			addCriterion("business_register_no not between", value1, value2,
					"businessRegisterNo");
			return (Criteria) this;
		}

		public Criteria andTaxRegisterNoIsNull() {
			addCriterion("tax_register_no is null");
			return (Criteria) this;
		}

		public Criteria andTaxRegisterNoIsNotNull() {
			addCriterion("tax_register_no is not null");
			return (Criteria) this;
		}

		public Criteria andTaxRegisterNoEqualTo(String value) {
			addCriterion("tax_register_no =", value, "taxRegisterNo");
			return (Criteria) this;
		}

		public Criteria andTaxRegisterNoNotEqualTo(String value) {
			addCriterion("tax_register_no <>", value, "taxRegisterNo");
			return (Criteria) this;
		}

		public Criteria andTaxRegisterNoGreaterThan(String value) {
			addCriterion("tax_register_no >", value, "taxRegisterNo");
			return (Criteria) this;
		}

		public Criteria andTaxRegisterNoGreaterThanOrEqualTo(String value) {
			addCriterion("tax_register_no >=", value, "taxRegisterNo");
			return (Criteria) this;
		}

		public Criteria andTaxRegisterNoLessThan(String value) {
			addCriterion("tax_register_no <", value, "taxRegisterNo");
			return (Criteria) this;
		}

		public Criteria andTaxRegisterNoLessThanOrEqualTo(String value) {
			addCriterion("tax_register_no <=", value, "taxRegisterNo");
			return (Criteria) this;
		}

		public Criteria andTaxRegisterNoLike(String value) {
			addCriterion("tax_register_no like", value, "taxRegisterNo");
			return (Criteria) this;
		}

		public Criteria andTaxRegisterNoNotLike(String value) {
			addCriterion("tax_register_no not like", value, "taxRegisterNo");
			return (Criteria) this;
		}

		public Criteria andTaxRegisterNoIn(List<String> values) {
			addCriterion("tax_register_no in", values, "taxRegisterNo");
			return (Criteria) this;
		}

		public Criteria andTaxRegisterNoNotIn(List<String> values) {
			addCriterion("tax_register_no not in", values, "taxRegisterNo");
			return (Criteria) this;
		}

		public Criteria andTaxRegisterNoBetween(String value1, String value2) {
			addCriterion("tax_register_no between", value1, value2,
					"taxRegisterNo");
			return (Criteria) this;
		}

		public Criteria andTaxRegisterNoNotBetween(String value1, String value2) {
			addCriterion("tax_register_no not between", value1, value2,
					"taxRegisterNo");
			return (Criteria) this;
		}

		public Criteria andPortCodeIsNull() {
			addCriterion("port_code is null");
			return (Criteria) this;
		}

		public Criteria andPortCodeIsNotNull() {
			addCriterion("port_code is not null");
			return (Criteria) this;
		}

		public Criteria andPortCodeEqualTo(String value) {
			addCriterion("port_code =", value, "portCode");
			return (Criteria) this;
		}

		public Criteria andPortCodeNotEqualTo(String value) {
			addCriterion("port_code <>", value, "portCode");
			return (Criteria) this;
		}

		public Criteria andPortCodeGreaterThan(String value) {
			addCriterion("port_code >", value, "portCode");
			return (Criteria) this;
		}

		public Criteria andPortCodeGreaterThanOrEqualTo(String value) {
			addCriterion("port_code >=", value, "portCode");
			return (Criteria) this;
		}

		public Criteria andPortCodeLessThan(String value) {
			addCriterion("port_code <", value, "portCode");
			return (Criteria) this;
		}

		public Criteria andPortCodeLessThanOrEqualTo(String value) {
			addCriterion("port_code <=", value, "portCode");
			return (Criteria) this;
		}

		public Criteria andPortCodeLike(String value) {
			addCriterion("port_code like", value, "portCode");
			return (Criteria) this;
		}

		public Criteria andPortCodeNotLike(String value) {
			addCriterion("port_code not like", value, "portCode");
			return (Criteria) this;
		}

		public Criteria andPortCodeIn(List<String> values) {
			addCriterion("port_code in", values, "portCode");
			return (Criteria) this;
		}

		public Criteria andPortCodeNotIn(List<String> values) {
			addCriterion("port_code not in", values, "portCode");
			return (Criteria) this;
		}

		public Criteria andPortCodeBetween(String value1, String value2) {
			addCriterion("port_code between", value1, value2, "portCode");
			return (Criteria) this;
		}

		public Criteria andPortCodeNotBetween(String value1, String value2) {
			addCriterion("port_code not between", value1, value2, "portCode");
			return (Criteria) this;
		}

		public Criteria andContactIsNull() {
			addCriterion("contact is null");
			return (Criteria) this;
		}

		public Criteria andContactIsNotNull() {
			addCriterion("contact is not null");
			return (Criteria) this;
		}

		public Criteria andContactEqualTo(String value) {
			addCriterion("contact =", value, "contact");
			return (Criteria) this;
		}

		public Criteria andContactNotEqualTo(String value) {
			addCriterion("contact <>", value, "contact");
			return (Criteria) this;
		}

		public Criteria andContactGreaterThan(String value) {
			addCriterion("contact >", value, "contact");
			return (Criteria) this;
		}

		public Criteria andContactGreaterThanOrEqualTo(String value) {
			addCriterion("contact >=", value, "contact");
			return (Criteria) this;
		}

		public Criteria andContactLessThan(String value) {
			addCriterion("contact <", value, "contact");
			return (Criteria) this;
		}

		public Criteria andContactLessThanOrEqualTo(String value) {
			addCriterion("contact <=", value, "contact");
			return (Criteria) this;
		}

		public Criteria andContactLike(String value) {
			addCriterion("contact like", value, "contact");
			return (Criteria) this;
		}

		public Criteria andContactNotLike(String value) {
			addCriterion("contact not like", value, "contact");
			return (Criteria) this;
		}

		public Criteria andContactIn(List<String> values) {
			addCriterion("contact in", values, "contact");
			return (Criteria) this;
		}

		public Criteria andContactNotIn(List<String> values) {
			addCriterion("contact not in", values, "contact");
			return (Criteria) this;
		}

		public Criteria andContactBetween(String value1, String value2) {
			addCriterion("contact between", value1, value2, "contact");
			return (Criteria) this;
		}

		public Criteria andContactNotBetween(String value1, String value2) {
			addCriterion("contact not between", value1, value2, "contact");
			return (Criteria) this;
		}

		public Criteria andEmailIsNull() {
			addCriterion("email is null");
			return (Criteria) this;
		}

		public Criteria andEmailIsNotNull() {
			addCriterion("email is not null");
			return (Criteria) this;
		}

		public Criteria andEmailEqualTo(String value) {
			addCriterion("email =", value, "email");
			return (Criteria) this;
		}

		public Criteria andEmailNotEqualTo(String value) {
			addCriterion("email <>", value, "email");
			return (Criteria) this;
		}

		public Criteria andEmailGreaterThan(String value) {
			addCriterion("email >", value, "email");
			return (Criteria) this;
		}

		public Criteria andEmailGreaterThanOrEqualTo(String value) {
			addCriterion("email >=", value, "email");
			return (Criteria) this;
		}

		public Criteria andEmailLessThan(String value) {
			addCriterion("email <", value, "email");
			return (Criteria) this;
		}

		public Criteria andEmailLessThanOrEqualTo(String value) {
			addCriterion("email <=", value, "email");
			return (Criteria) this;
		}

		public Criteria andEmailLike(String value) {
			addCriterion("email like", value, "email");
			return (Criteria) this;
		}

		public Criteria andEmailNotLike(String value) {
			addCriterion("email not like", value, "email");
			return (Criteria) this;
		}

		public Criteria andEmailIn(List<String> values) {
			addCriterion("email in", values, "email");
			return (Criteria) this;
		}

		public Criteria andEmailNotIn(List<String> values) {
			addCriterion("email not in", values, "email");
			return (Criteria) this;
		}

		public Criteria andEmailBetween(String value1, String value2) {
			addCriterion("email between", value1, value2, "email");
			return (Criteria) this;
		}

		public Criteria andEmailNotBetween(String value1, String value2) {
			addCriterion("email not between", value1, value2, "email");
			return (Criteria) this;
		}

		public Criteria andTelIsNull() {
			addCriterion("tel is null");
			return (Criteria) this;
		}

		public Criteria andTelIsNotNull() {
			addCriterion("tel is not null");
			return (Criteria) this;
		}

		public Criteria andTelEqualTo(String value) {
			addCriterion("tel =", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelNotEqualTo(String value) {
			addCriterion("tel <>", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelGreaterThan(String value) {
			addCriterion("tel >", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelGreaterThanOrEqualTo(String value) {
			addCriterion("tel >=", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelLessThan(String value) {
			addCriterion("tel <", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelLessThanOrEqualTo(String value) {
			addCriterion("tel <=", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelLike(String value) {
			addCriterion("tel like", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelNotLike(String value) {
			addCriterion("tel not like", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelIn(List<String> values) {
			addCriterion("tel in", values, "tel");
			return (Criteria) this;
		}

		public Criteria andTelNotIn(List<String> values) {
			addCriterion("tel not in", values, "tel");
			return (Criteria) this;
		}

		public Criteria andTelBetween(String value1, String value2) {
			addCriterion("tel between", value1, value2, "tel");
			return (Criteria) this;
		}

		public Criteria andTelNotBetween(String value1, String value2) {
			addCriterion("tel not between", value1, value2, "tel");
			return (Criteria) this;
		}

		public Criteria andSupplierTypeIsNull() {
			addCriterion("supplier_type is null");
			return (Criteria) this;
		}

		public Criteria andSupplierTypeIsNotNull() {
			addCriterion("supplier_type is not null");
			return (Criteria) this;
		}

		public Criteria andSupplierTypeEqualTo(String value) {
			addCriterion("supplier_type =", value, "supplierType");
			return (Criteria) this;
		}

		public Criteria andSupplierTypeNotEqualTo(String value) {
			addCriterion("supplier_type <>", value, "supplierType");
			return (Criteria) this;
		}

		public Criteria andSupplierTypeGreaterThan(String value) {
			addCriterion("supplier_type >", value, "supplierType");
			return (Criteria) this;
		}

		public Criteria andSupplierTypeGreaterThanOrEqualTo(String value) {
			addCriterion("supplier_type >=", value, "supplierType");
			return (Criteria) this;
		}

		public Criteria andSupplierTypeLessThan(String value) {
			addCriterion("supplier_type <", value, "supplierType");
			return (Criteria) this;
		}

		public Criteria andSupplierTypeLessThanOrEqualTo(String value) {
			addCriterion("supplier_type <=", value, "supplierType");
			return (Criteria) this;
		}

		public Criteria andSupplierTypeLike(String value) {
			addCriterion("supplier_type like", value, "supplierType");
			return (Criteria) this;
		}

		public Criteria andSupplierTypeNotLike(String value) {
			addCriterion("supplier_type not like", value, "supplierType");
			return (Criteria) this;
		}

		public Criteria andSupplierTypeIn(List<String> values) {
			addCriterion("supplier_type in", values, "supplierType");
			return (Criteria) this;
		}

		public Criteria andSupplierTypeNotIn(List<String> values) {
			addCriterion("supplier_type not in", values, "supplierType");
			return (Criteria) this;
		}

		public Criteria andSupplierTypeBetween(String value1, String value2) {
			addCriterion("supplier_type between", value1, value2,
					"supplierType");
			return (Criteria) this;
		}

		public Criteria andSupplierTypeNotBetween(String value1, String value2) {
			addCriterion("supplier_type not between", value1, value2,
					"supplierType");
			return (Criteria) this;
		}

		public Criteria andCustTypeIsNull() {
			addCriterion("cust_type is null");
			return (Criteria) this;
		}

		public Criteria andCustTypeIsNotNull() {
			addCriterion("cust_type is not null");
			return (Criteria) this;
		}

		public Criteria andCustTypeEqualTo(String value) {
			addCriterion("cust_type =", value, "custType");
			return (Criteria) this;
		}

		public Criteria andCustTypeNotEqualTo(String value) {
			addCriterion("cust_type <>", value, "custType");
			return (Criteria) this;
		}

		public Criteria andCustTypeGreaterThan(String value) {
			addCriterion("cust_type >", value, "custType");
			return (Criteria) this;
		}

		public Criteria andCustTypeGreaterThanOrEqualTo(String value) {
			addCriterion("cust_type >=", value, "custType");
			return (Criteria) this;
		}

		public Criteria andCustTypeLessThan(String value) {
			addCriterion("cust_type <", value, "custType");
			return (Criteria) this;
		}

		public Criteria andCustTypeLessThanOrEqualTo(String value) {
			addCriterion("cust_type <=", value, "custType");
			return (Criteria) this;
		}

		public Criteria andCustTypeLike(String value) {
			addCriterion("cust_type like", value, "custType");
			return (Criteria) this;
		}

		public Criteria andCustTypeNotLike(String value) {
			addCriterion("cust_type not like", value, "custType");
			return (Criteria) this;
		}

		public Criteria andCustTypeIn(List<String> values) {
			addCriterion("cust_type in", values, "custType");
			return (Criteria) this;
		}

		public Criteria andCustTypeNotIn(List<String> values) {
			addCriterion("cust_type not in", values, "custType");
			return (Criteria) this;
		}

		public Criteria andCustTypeBetween(String value1, String value2) {
			addCriterion("cust_type between", value1, value2, "custType");
			return (Criteria) this;
		}

		public Criteria andCustTypeNotBetween(String value1, String value2) {
			addCriterion("cust_type not between", value1, value2, "custType");
			return (Criteria) this;
		}

		public Criteria andFaxIsNull() {
			addCriterion("fax is null");
			return (Criteria) this;
		}

		public Criteria andFaxIsNotNull() {
			addCriterion("fax is not null");
			return (Criteria) this;
		}

		public Criteria andFaxEqualTo(String value) {
			addCriterion("fax =", value, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxNotEqualTo(String value) {
			addCriterion("fax <>", value, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxGreaterThan(String value) {
			addCriterion("fax >", value, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxGreaterThanOrEqualTo(String value) {
			addCriterion("fax >=", value, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxLessThan(String value) {
			addCriterion("fax <", value, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxLessThanOrEqualTo(String value) {
			addCriterion("fax <=", value, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxLike(String value) {
			addCriterion("fax like", value, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxNotLike(String value) {
			addCriterion("fax not like", value, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxIn(List<String> values) {
			addCriterion("fax in", values, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxNotIn(List<String> values) {
			addCriterion("fax not in", values, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxBetween(String value1, String value2) {
			addCriterion("fax between", value1, value2, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxNotBetween(String value1, String value2) {
			addCriterion("fax not between", value1, value2, "fax");
			return (Criteria) this;
		}

		public Criteria andAddressIsNull() {
			addCriterion("address is null");
			return (Criteria) this;
		}

		public Criteria andAddressIsNotNull() {
			addCriterion("address is not null");
			return (Criteria) this;
		}

		public Criteria andAddressEqualTo(String value) {
			addCriterion("address =", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressNotEqualTo(String value) {
			addCriterion("address <>", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressGreaterThan(String value) {
			addCriterion("address >", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressGreaterThanOrEqualTo(String value) {
			addCriterion("address >=", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressLessThan(String value) {
			addCriterion("address <", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressLessThanOrEqualTo(String value) {
			addCriterion("address <=", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressLike(String value) {
			addCriterion("address like", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressNotLike(String value) {
			addCriterion("address not like", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressIn(List<String> values) {
			addCriterion("address in", values, "address");
			return (Criteria) this;
		}

		public Criteria andAddressNotIn(List<String> values) {
			addCriterion("address not in", values, "address");
			return (Criteria) this;
		}

		public Criteria andAddressBetween(String value1, String value2) {
			addCriterion("address between", value1, value2, "address");
			return (Criteria) this;
		}

		public Criteria andAddressNotBetween(String value1, String value2) {
			addCriterion("address not between", value1, value2, "address");
			return (Criteria) this;
		}

		public Criteria andIsInternalIsNull() {
			addCriterion("is_internal is null");
			return (Criteria) this;
		}

		public Criteria andIsInternalIsNotNull() {
			addCriterion("is_internal is not null");
			return (Criteria) this;
		}

		public Criteria andIsInternalEqualTo(Integer value) {
			addCriterion("is_internal =", value, "isInternal");
			return (Criteria) this;
		}

		public Criteria andIsInternalNotEqualTo(Integer value) {
			addCriterion("is_internal <>", value, "isInternal");
			return (Criteria) this;
		}

		public Criteria andIsInternalGreaterThan(Integer value) {
			addCriterion("is_internal >", value, "isInternal");
			return (Criteria) this;
		}

		public Criteria andIsInternalGreaterThanOrEqualTo(Integer value) {
			addCriterion("is_internal >=", value, "isInternal");
			return (Criteria) this;
		}

		public Criteria andIsInternalLessThan(Integer value) {
			addCriterion("is_internal <", value, "isInternal");
			return (Criteria) this;
		}

		public Criteria andIsInternalLessThanOrEqualTo(Integer value) {
			addCriterion("is_internal <=", value, "isInternal");
			return (Criteria) this;
		}

		public Criteria andIsInternalIn(List<Integer> values) {
			addCriterion("is_internal in", values, "isInternal");
			return (Criteria) this;
		}

		public Criteria andIsInternalNotIn(List<Integer> values) {
			addCriterion("is_internal not in", values, "isInternal");
			return (Criteria) this;
		}

		public Criteria andIsInternalBetween(Integer value1, Integer value2) {
			addCriterion("is_internal between", value1, value2, "isInternal");
			return (Criteria) this;
		}

		public Criteria andIsInternalNotBetween(Integer value1, Integer value2) {
			addCriterion("is_internal not between", value1, value2,
					"isInternal");
			return (Criteria) this;
		}

		public Criteria andIsCustomerIsNull() {
			addCriterion("is_customer is null");
			return (Criteria) this;
		}

		public Criteria andIsCustomerIsNotNull() {
			addCriterion("is_customer is not null");
			return (Criteria) this;
		}

		public Criteria andIsCustomerEqualTo(Integer value) {
			addCriterion("is_customer =", value, "isCustomer");
			return (Criteria) this;
		}

		public Criteria andIsCustomerNotEqualTo(Integer value) {
			addCriterion("is_customer <>", value, "isCustomer");
			return (Criteria) this;
		}

		public Criteria andIsCustomerGreaterThan(Integer value) {
			addCriterion("is_customer >", value, "isCustomer");
			return (Criteria) this;
		}

		public Criteria andIsCustomerGreaterThanOrEqualTo(Integer value) {
			addCriterion("is_customer >=", value, "isCustomer");
			return (Criteria) this;
		}

		public Criteria andIsCustomerLessThan(Integer value) {
			addCriterion("is_customer <", value, "isCustomer");
			return (Criteria) this;
		}

		public Criteria andIsCustomerLessThanOrEqualTo(Integer value) {
			addCriterion("is_customer <=", value, "isCustomer");
			return (Criteria) this;
		}

		public Criteria andIsCustomerIn(List<Integer> values) {
			addCriterion("is_customer in", values, "isCustomer");
			return (Criteria) this;
		}

		public Criteria andIsCustomerNotIn(List<Integer> values) {
			addCriterion("is_customer not in", values, "isCustomer");
			return (Criteria) this;
		}

		public Criteria andIsCustomerBetween(Integer value1, Integer value2) {
			addCriterion("is_customer between", value1, value2, "isCustomer");
			return (Criteria) this;
		}

		public Criteria andIsCustomerNotBetween(Integer value1, Integer value2) {
			addCriterion("is_customer not between", value1, value2,
					"isCustomer");
			return (Criteria) this;
		}

		public Criteria andIsSupplierIsNull() {
			addCriterion("is_supplier is null");
			return (Criteria) this;
		}

		public Criteria andIsSupplierIsNotNull() {
			addCriterion("is_supplier is not null");
			return (Criteria) this;
		}

		public Criteria andIsSupplierEqualTo(Integer value) {
			addCriterion("is_supplier =", value, "isSupplier");
			return (Criteria) this;
		}

		public Criteria andIsSupplierNotEqualTo(Integer value) {
			addCriterion("is_supplier <>", value, "isSupplier");
			return (Criteria) this;
		}

		public Criteria andIsSupplierGreaterThan(Integer value) {
			addCriterion("is_supplier >", value, "isSupplier");
			return (Criteria) this;
		}

		public Criteria andIsSupplierGreaterThanOrEqualTo(Integer value) {
			addCriterion("is_supplier >=", value, "isSupplier");
			return (Criteria) this;
		}

		public Criteria andIsSupplierLessThan(Integer value) {
			addCriterion("is_supplier <", value, "isSupplier");
			return (Criteria) this;
		}

		public Criteria andIsSupplierLessThanOrEqualTo(Integer value) {
			addCriterion("is_supplier <=", value, "isSupplier");
			return (Criteria) this;
		}

		public Criteria andIsSupplierIn(List<Integer> values) {
			addCriterion("is_supplier in", values, "isSupplier");
			return (Criteria) this;
		}

		public Criteria andIsSupplierNotIn(List<Integer> values) {
			addCriterion("is_supplier not in", values, "isSupplier");
			return (Criteria) this;
		}

		public Criteria andIsSupplierBetween(Integer value1, Integer value2) {
			addCriterion("is_supplier between", value1, value2, "isSupplier");
			return (Criteria) this;
		}

		public Criteria andIsSupplierNotBetween(Integer value1, Integer value2) {
			addCriterion("is_supplier not between", value1, value2,
					"isSupplier");
			return (Criteria) this;
		}

		public Criteria andActiveIsNull() {
			addCriterion("active is null");
			return (Criteria) this;
		}

		public Criteria andActiveIsNotNull() {
			addCriterion("active is not null");
			return (Criteria) this;
		}

		public Criteria andActiveEqualTo(Integer value) {
			addCriterion("active =", value, "active");
			return (Criteria) this;
		}

		public Criteria andActiveNotEqualTo(Integer value) {
			addCriterion("active <>", value, "active");
			return (Criteria) this;
		}

		public Criteria andActiveGreaterThan(Integer value) {
			addCriterion("active >", value, "active");
			return (Criteria) this;
		}

		public Criteria andActiveGreaterThanOrEqualTo(Integer value) {
			addCriterion("active >=", value, "active");
			return (Criteria) this;
		}

		public Criteria andActiveLessThan(Integer value) {
			addCriterion("active <", value, "active");
			return (Criteria) this;
		}

		public Criteria andActiveLessThanOrEqualTo(Integer value) {
			addCriterion("active <=", value, "active");
			return (Criteria) this;
		}

		public Criteria andActiveIn(List<Integer> values) {
			addCriterion("active in", values, "active");
			return (Criteria) this;
		}

		public Criteria andActiveNotIn(List<Integer> values) {
			addCriterion("active not in", values, "active");
			return (Criteria) this;
		}

		public Criteria andActiveBetween(Integer value1, Integer value2) {
			addCriterion("active between", value1, value2, "active");
			return (Criteria) this;
		}

		public Criteria andActiveNotBetween(Integer value1, Integer value2) {
			addCriterion("active not between", value1, value2, "active");
			return (Criteria) this;
		}

		public Criteria andXchgrNameIsNull() {
			addCriterion("xchgr_name is null");
			return (Criteria) this;
		}

		public Criteria andXchgrNameIsNotNull() {
			addCriterion("xchgr_name is not null");
			return (Criteria) this;
		}

		public Criteria andXchgrNameEqualTo(String value) {
			addCriterion("xchgr_name =", value, "xchgrName");
			return (Criteria) this;
		}

		public Criteria andXchgrNameNotEqualTo(String value) {
			addCriterion("xchgr_name <>", value, "xchgrName");
			return (Criteria) this;
		}

		public Criteria andXchgrNameGreaterThan(String value) {
			addCriterion("xchgr_name >", value, "xchgrName");
			return (Criteria) this;
		}

		public Criteria andXchgrNameGreaterThanOrEqualTo(String value) {
			addCriterion("xchgr_name >=", value, "xchgrName");
			return (Criteria) this;
		}

		public Criteria andXchgrNameLessThan(String value) {
			addCriterion("xchgr_name <", value, "xchgrName");
			return (Criteria) this;
		}

		public Criteria andXchgrNameLessThanOrEqualTo(String value) {
			addCriterion("xchgr_name <=", value, "xchgrName");
			return (Criteria) this;
		}

		public Criteria andXchgrNameLike(String value) {
			addCriterion("xchgr_name like", value, "xchgrName");
			return (Criteria) this;
		}

		public Criteria andXchgrNameNotLike(String value) {
			addCriterion("xchgr_name not like", value, "xchgrName");
			return (Criteria) this;
		}

		public Criteria andXchgrNameIn(List<String> values) {
			addCriterion("xchgr_name in", values, "xchgrName");
			return (Criteria) this;
		}

		public Criteria andXchgrNameNotIn(List<String> values) {
			addCriterion("xchgr_name not in", values, "xchgrName");
			return (Criteria) this;
		}

		public Criteria andXchgrNameBetween(String value1, String value2) {
			addCriterion("xchgr_name between", value1, value2, "xchgrName");
			return (Criteria) this;
		}

		public Criteria andXchgrNameNotBetween(String value1, String value2) {
			addCriterion("xchgr_name not between", value1, value2, "xchgrName");
			return (Criteria) this;
		}

		public Criteria andCompanyCodeIsNull() {
			addCriterion("company_code is null");
			return (Criteria) this;
		}

		public Criteria andCompanyCodeIsNotNull() {
			addCriterion("company_code is not null");
			return (Criteria) this;
		}

		public Criteria andCompanyCodeEqualTo(String value) {
			addCriterion("company_code =", value, "companyCode");
			return (Criteria) this;
		}

		public Criteria andCompanyCodeNotEqualTo(String value) {
			addCriterion("company_code <>", value, "companyCode");
			return (Criteria) this;
		}

		public Criteria andCompanyCodeGreaterThan(String value) {
			addCriterion("company_code >", value, "companyCode");
			return (Criteria) this;
		}

		public Criteria andCompanyCodeGreaterThanOrEqualTo(String value) {
			addCriterion("company_code >=", value, "companyCode");
			return (Criteria) this;
		}

		public Criteria andCompanyCodeLessThan(String value) {
			addCriterion("company_code <", value, "companyCode");
			return (Criteria) this;
		}

		public Criteria andCompanyCodeLessThanOrEqualTo(String value) {
			addCriterion("company_code <=", value, "companyCode");
			return (Criteria) this;
		}

		public Criteria andCompanyCodeLike(String value) {
			addCriterion("company_code like", value, "companyCode");
			return (Criteria) this;
		}

		public Criteria andCompanyCodeNotLike(String value) {
			addCriterion("company_code not like", value, "companyCode");
			return (Criteria) this;
		}

		public Criteria andCompanyCodeIn(List<String> values) {
			addCriterion("company_code in", values, "companyCode");
			return (Criteria) this;
		}

		public Criteria andCompanyCodeNotIn(List<String> values) {
			addCriterion("company_code not in", values, "companyCode");
			return (Criteria) this;
		}

		public Criteria andCompanyCodeBetween(String value1, String value2) {
			addCriterion("company_code between", value1, value2, "companyCode");
			return (Criteria) this;
		}

		public Criteria andCompanyCodeNotBetween(String value1, String value2) {
			addCriterion("company_code not between", value1, value2,
					"companyCode");
			return (Criteria) this;
		}

		public Criteria andHomeCurrencyIsNull() {
			addCriterion("home_currency is null");
			return (Criteria) this;
		}

		public Criteria andHomeCurrencyIsNotNull() {
			addCriterion("home_currency is not null");
			return (Criteria) this;
		}

		public Criteria andHomeCurrencyEqualTo(String value) {
			addCriterion("home_currency =", value, "homeCurrency");
			return (Criteria) this;
		}

		public Criteria andHomeCurrencyNotEqualTo(String value) {
			addCriterion("home_currency <>", value, "homeCurrency");
			return (Criteria) this;
		}

		public Criteria andHomeCurrencyGreaterThan(String value) {
			addCriterion("home_currency >", value, "homeCurrency");
			return (Criteria) this;
		}

		public Criteria andHomeCurrencyGreaterThanOrEqualTo(String value) {
			addCriterion("home_currency >=", value, "homeCurrency");
			return (Criteria) this;
		}

		public Criteria andHomeCurrencyLessThan(String value) {
			addCriterion("home_currency <", value, "homeCurrency");
			return (Criteria) this;
		}

		public Criteria andHomeCurrencyLessThanOrEqualTo(String value) {
			addCriterion("home_currency <=", value, "homeCurrency");
			return (Criteria) this;
		}

		public Criteria andHomeCurrencyLike(String value) {
			addCriterion("home_currency like", value, "homeCurrency");
			return (Criteria) this;
		}

		public Criteria andHomeCurrencyNotLike(String value) {
			addCriterion("home_currency not like", value, "homeCurrency");
			return (Criteria) this;
		}

		public Criteria andHomeCurrencyIn(List<String> values) {
			addCriterion("home_currency in", values, "homeCurrency");
			return (Criteria) this;
		}

		public Criteria andHomeCurrencyNotIn(List<String> values) {
			addCriterion("home_currency not in", values, "homeCurrency");
			return (Criteria) this;
		}

		public Criteria andHomeCurrencyBetween(String value1, String value2) {
			addCriterion("home_currency between", value1, value2,
					"homeCurrency");
			return (Criteria) this;
		}

		public Criteria andHomeCurrencyNotBetween(String value1, String value2) {
			addCriterion("home_currency not between", value1, value2,
					"homeCurrency");
			return (Criteria) this;
		}

		public Criteria andSettleOfficeIsNull() {
			addCriterion("settle_office is null");
			return (Criteria) this;
		}

		public Criteria andSettleOfficeIsNotNull() {
			addCriterion("settle_office is not null");
			return (Criteria) this;
		}

		public Criteria andSettleOfficeEqualTo(String value) {
			addCriterion("settle_office =", value, "settleOffice");
			return (Criteria) this;
		}

		public Criteria andSettleOfficeNotEqualTo(String value) {
			addCriterion("settle_office <>", value, "settleOffice");
			return (Criteria) this;
		}

		public Criteria andSettleOfficeGreaterThan(String value) {
			addCriterion("settle_office >", value, "settleOffice");
			return (Criteria) this;
		}

		public Criteria andSettleOfficeGreaterThanOrEqualTo(String value) {
			addCriterion("settle_office >=", value, "settleOffice");
			return (Criteria) this;
		}

		public Criteria andSettleOfficeLessThan(String value) {
			addCriterion("settle_office <", value, "settleOffice");
			return (Criteria) this;
		}

		public Criteria andSettleOfficeLessThanOrEqualTo(String value) {
			addCriterion("settle_office <=", value, "settleOffice");
			return (Criteria) this;
		}

		public Criteria andSettleOfficeLike(String value) {
			addCriterion("settle_office like", value, "settleOffice");
			return (Criteria) this;
		}

		public Criteria andSettleOfficeNotLike(String value) {
			addCriterion("settle_office not like", value, "settleOffice");
			return (Criteria) this;
		}

		public Criteria andSettleOfficeIn(List<String> values) {
			addCriterion("settle_office in", values, "settleOffice");
			return (Criteria) this;
		}

		public Criteria andSettleOfficeNotIn(List<String> values) {
			addCriterion("settle_office not in", values, "settleOffice");
			return (Criteria) this;
		}

		public Criteria andSettleOfficeBetween(String value1, String value2) {
			addCriterion("settle_office between", value1, value2,
					"settleOffice");
			return (Criteria) this;
		}

		public Criteria andSettleOfficeNotBetween(String value1, String value2) {
			addCriterion("settle_office not between", value1, value2,
					"settleOffice");
			return (Criteria) this;
		}

		public Criteria andBlContentIsNull() {
			addCriterion("bl_content is null");
			return (Criteria) this;
		}

		public Criteria andBlContentIsNotNull() {
			addCriterion("bl_content is not null");
			return (Criteria) this;
		}

		public Criteria andBlContentEqualTo(String value) {
			addCriterion("bl_content =", value, "blContent");
			return (Criteria) this;
		}

		public Criteria andBlContentNotEqualTo(String value) {
			addCriterion("bl_content <>", value, "blContent");
			return (Criteria) this;
		}

		public Criteria andBlContentGreaterThan(String value) {
			addCriterion("bl_content >", value, "blContent");
			return (Criteria) this;
		}

		public Criteria andBlContentGreaterThanOrEqualTo(String value) {
			addCriterion("bl_content >=", value, "blContent");
			return (Criteria) this;
		}

		public Criteria andBlContentLessThan(String value) {
			addCriterion("bl_content <", value, "blContent");
			return (Criteria) this;
		}

		public Criteria andBlContentLessThanOrEqualTo(String value) {
			addCriterion("bl_content <=", value, "blContent");
			return (Criteria) this;
		}

		public Criteria andBlContentLike(String value) {
			addCriterion("bl_content like", value, "blContent");
			return (Criteria) this;
		}

		public Criteria andBlContentNotLike(String value) {
			addCriterion("bl_content not like", value, "blContent");
			return (Criteria) this;
		}

		public Criteria andBlContentIn(List<String> values) {
			addCriterion("bl_content in", values, "blContent");
			return (Criteria) this;
		}

		public Criteria andBlContentNotIn(List<String> values) {
			addCriterion("bl_content not in", values, "blContent");
			return (Criteria) this;
		}

		public Criteria andBlContentBetween(String value1, String value2) {
			addCriterion("bl_content between", value1, value2, "blContent");
			return (Criteria) this;
		}

		public Criteria andBlContentNotBetween(String value1, String value2) {
			addCriterion("bl_content not between", value1, value2, "blContent");
			return (Criteria) this;
		}

		public Criteria andCompanyIdIsNull() {
			addCriterion("company_id is null");
			return (Criteria) this;
		}

		public Criteria andCompanyIdIsNotNull() {
			addCriterion("company_id is not null");
			return (Criteria) this;
		}

		public Criteria andCompanyIdEqualTo(Integer value) {
			addCriterion("company_id =", value, "companyId");
			return (Criteria) this;
		}

		public Criteria andCompanyIdNotEqualTo(Integer value) {
			addCriterion("company_id <>", value, "companyId");
			return (Criteria) this;
		}

		public Criteria andCompanyIdGreaterThan(Integer value) {
			addCriterion("company_id >", value, "companyId");
			return (Criteria) this;
		}

		public Criteria andCompanyIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("company_id >=", value, "companyId");
			return (Criteria) this;
		}

		public Criteria andCompanyIdLessThan(Integer value) {
			addCriterion("company_id <", value, "companyId");
			return (Criteria) this;
		}

		public Criteria andCompanyIdLessThanOrEqualTo(Integer value) {
			addCriterion("company_id <=", value, "companyId");
			return (Criteria) this;
		}

		public Criteria andCompanyIdIn(List<Integer> values) {
			addCriterion("company_id in", values, "companyId");
			return (Criteria) this;
		}

		public Criteria andCompanyIdNotIn(List<Integer> values) {
			addCriterion("company_id not in", values, "companyId");
			return (Criteria) this;
		}

		public Criteria andCompanyIdBetween(Integer value1, Integer value2) {
			addCriterion("company_id between", value1, value2, "companyId");
			return (Criteria) this;
		}

		public Criteria andCompanyIdNotBetween(Integer value1, Integer value2) {
			addCriterion("company_id not between", value1, value2, "companyId");
			return (Criteria) this;
		}

		public Criteria andCustIdIsNull() {
			addCriterion("cust_id is null");
			return (Criteria) this;
		}

		public Criteria andCustIdIsNotNull() {
			addCriterion("cust_id is not null");
			return (Criteria) this;
		}

		public Criteria andCustIdEqualTo(String value) {
			addCriterion("cust_id =", value, "custId");
			return (Criteria) this;
		}

		public Criteria andCustIdNotEqualTo(String value) {
			addCriterion("cust_id <>", value, "custId");
			return (Criteria) this;
		}

		public Criteria andCustIdGreaterThan(String value) {
			addCriterion("cust_id >", value, "custId");
			return (Criteria) this;
		}

		public Criteria andCustIdGreaterThanOrEqualTo(String value) {
			addCriterion("cust_id >=", value, "custId");
			return (Criteria) this;
		}

		public Criteria andCustIdLessThan(String value) {
			addCriterion("cust_id <", value, "custId");
			return (Criteria) this;
		}

		public Criteria andCustIdLessThanOrEqualTo(String value) {
			addCriterion("cust_id <=", value, "custId");
			return (Criteria) this;
		}

		public Criteria andCustIdLike(String value) {
			addCriterion("cust_id like", value, "custId");
			return (Criteria) this;
		}

		public Criteria andCustIdNotLike(String value) {
			addCriterion("cust_id not like", value, "custId");
			return (Criteria) this;
		}

		public Criteria andCustIdIn(List<String> values) {
			addCriterion("cust_id in", values, "custId");
			return (Criteria) this;
		}

		public Criteria andCustIdNotIn(List<String> values) {
			addCriterion("cust_id not in", values, "custId");
			return (Criteria) this;
		}

		public Criteria andCustIdBetween(String value1, String value2) {
			addCriterion("cust_id between", value1, value2, "custId");
			return (Criteria) this;
		}

		public Criteria andCustIdNotBetween(String value1, String value2) {
			addCriterion("cust_id not between", value1, value2, "custId");
			return (Criteria) this;
		}

		public Criteria andCustCodeIsNull() {
			addCriterion("cust_code is null");
			return (Criteria) this;
		}

		public Criteria andCustCodeIsNotNull() {
			addCriterion("cust_code is not null");
			return (Criteria) this;
		}

		public Criteria andCustCodeEqualTo(String value) {
			addCriterion("cust_code =", value, "custCode");
			return (Criteria) this;
		}

		public Criteria andCustCodeNotEqualTo(String value) {
			addCriterion("cust_code <>", value, "custCode");
			return (Criteria) this;
		}

		public Criteria andCustCodeGreaterThan(String value) {
			addCriterion("cust_code >", value, "custCode");
			return (Criteria) this;
		}

		public Criteria andCustCodeGreaterThanOrEqualTo(String value) {
			addCriterion("cust_code >=", value, "custCode");
			return (Criteria) this;
		}

		public Criteria andCustCodeLessThan(String value) {
			addCriterion("cust_code <", value, "custCode");
			return (Criteria) this;
		}

		public Criteria andCustCodeLessThanOrEqualTo(String value) {
			addCriterion("cust_code <=", value, "custCode");
			return (Criteria) this;
		}

		public Criteria andCustCodeLike(String value) {
			addCriterion("cust_code like", value, "custCode");
			return (Criteria) this;
		}

		public Criteria andCustCodeNotLike(String value) {
			addCriterion("cust_code not like", value, "custCode");
			return (Criteria) this;
		}

		public Criteria andCustCodeIn(List<String> values) {
			addCriterion("cust_code in", values, "custCode");
			return (Criteria) this;
		}

		public Criteria andCustCodeNotIn(List<String> values) {
			addCriterion("cust_code not in", values, "custCode");
			return (Criteria) this;
		}

		public Criteria andCustCodeBetween(String value1, String value2) {
			addCriterion("cust_code between", value1, value2, "custCode");
			return (Criteria) this;
		}

		public Criteria andCustCodeNotBetween(String value1, String value2) {
			addCriterion("cust_code not between", value1, value2, "custCode");
			return (Criteria) this;
		}

		public Criteria andFunctionTypeIsNull() {
			addCriterion("function_type is null");
			return (Criteria) this;
		}

		public Criteria andFunctionTypeIsNotNull() {
			addCriterion("function_type is not null");
			return (Criteria) this;
		}

		public Criteria andFunctionTypeEqualTo(String value) {
			addCriterion("function_type =", value, "functionType");
			return (Criteria) this;
		}

		public Criteria andFunctionTypeNotEqualTo(String value) {
			addCriterion("function_type <>", value, "functionType");
			return (Criteria) this;
		}

		public Criteria andFunctionTypeGreaterThan(String value) {
			addCriterion("function_type >", value, "functionType");
			return (Criteria) this;
		}

		public Criteria andFunctionTypeGreaterThanOrEqualTo(String value) {
			addCriterion("function_type >=", value, "functionType");
			return (Criteria) this;
		}

		public Criteria andFunctionTypeLessThan(String value) {
			addCriterion("function_type <", value, "functionType");
			return (Criteria) this;
		}

		public Criteria andFunctionTypeLessThanOrEqualTo(String value) {
			addCriterion("function_type <=", value, "functionType");
			return (Criteria) this;
		}

		public Criteria andFunctionTypeLike(String value) {
			addCriterion("function_type like", value, "functionType");
			return (Criteria) this;
		}

		public Criteria andFunctionTypeNotLike(String value) {
			addCriterion("function_type not like", value, "functionType");
			return (Criteria) this;
		}

		public Criteria andFunctionTypeIn(List<String> values) {
			addCriterion("function_type in", values, "functionType");
			return (Criteria) this;
		}

		public Criteria andFunctionTypeNotIn(List<String> values) {
			addCriterion("function_type not in", values, "functionType");
			return (Criteria) this;
		}

		public Criteria andFunctionTypeBetween(String value1, String value2) {
			addCriterion("function_type between", value1, value2,
					"functionType");
			return (Criteria) this;
		}

		public Criteria andFunctionTypeNotBetween(String value1, String value2) {
			addCriterion("function_type not between", value1, value2,
					"functionType");
			return (Criteria) this;
		}

		public Criteria andRegionIdIsNull() {
			addCriterion("region_id is null");
			return (Criteria) this;
		}

		public Criteria andRegionIdIsNotNull() {
			addCriterion("region_id is not null");
			return (Criteria) this;
		}

		public Criteria andRegionIdEqualTo(Integer value) {
			addCriterion("region_id =", value, "regionId");
			return (Criteria) this;
		}

		public Criteria andRegionIdNotEqualTo(Integer value) {
			addCriterion("region_id <>", value, "regionId");
			return (Criteria) this;
		}

		public Criteria andRegionIdGreaterThan(Integer value) {
			addCriterion("region_id >", value, "regionId");
			return (Criteria) this;
		}

		public Criteria andRegionIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("region_id >=", value, "regionId");
			return (Criteria) this;
		}

		public Criteria andRegionIdLessThan(Integer value) {
			addCriterion("region_id <", value, "regionId");
			return (Criteria) this;
		}

		public Criteria andRegionIdLessThanOrEqualTo(Integer value) {
			addCriterion("region_id <=", value, "regionId");
			return (Criteria) this;
		}

		public Criteria andRegionIdIn(List<Integer> values) {
			addCriterion("region_id in", values, "regionId");
			return (Criteria) this;
		}

		public Criteria andRegionIdNotIn(List<Integer> values) {
			addCriterion("region_id not in", values, "regionId");
			return (Criteria) this;
		}

		public Criteria andRegionIdBetween(Integer value1, Integer value2) {
			addCriterion("region_id between", value1, value2, "regionId");
			return (Criteria) this;
		}

		public Criteria andRegionIdNotBetween(Integer value1, Integer value2) {
			addCriterion("region_id not between", value1, value2, "regionId");
			return (Criteria) this;
		}

		public Criteria andRegionCodeIsNull() {
			addCriterion("region_code is null");
			return (Criteria) this;
		}

		public Criteria andRegionCodeIsNotNull() {
			addCriterion("region_code is not null");
			return (Criteria) this;
		}

		public Criteria andRegionCodeEqualTo(String value) {
			addCriterion("region_code =", value, "regionCode");
			return (Criteria) this;
		}

		public Criteria andRegionCodeNotEqualTo(String value) {
			addCriterion("region_code <>", value, "regionCode");
			return (Criteria) this;
		}

		public Criteria andRegionCodeGreaterThan(String value) {
			addCriterion("region_code >", value, "regionCode");
			return (Criteria) this;
		}

		public Criteria andRegionCodeGreaterThanOrEqualTo(String value) {
			addCriterion("region_code >=", value, "regionCode");
			return (Criteria) this;
		}

		public Criteria andRegionCodeLessThan(String value) {
			addCriterion("region_code <", value, "regionCode");
			return (Criteria) this;
		}

		public Criteria andRegionCodeLessThanOrEqualTo(String value) {
			addCriterion("region_code <=", value, "regionCode");
			return (Criteria) this;
		}

		public Criteria andRegionCodeLike(String value) {
			addCriterion("region_code like", value, "regionCode");
			return (Criteria) this;
		}

		public Criteria andRegionCodeNotLike(String value) {
			addCriterion("region_code not like", value, "regionCode");
			return (Criteria) this;
		}

		public Criteria andRegionCodeIn(List<String> values) {
			addCriterion("region_code in", values, "regionCode");
			return (Criteria) this;
		}

		public Criteria andRegionCodeNotIn(List<String> values) {
			addCriterion("region_code not in", values, "regionCode");
			return (Criteria) this;
		}

		public Criteria andRegionCodeBetween(String value1, String value2) {
			addCriterion("region_code between", value1, value2, "regionCode");
			return (Criteria) this;
		}

		public Criteria andRegionCodeNotBetween(String value1, String value2) {
			addCriterion("region_code not between", value1, value2,
					"regionCode");
			return (Criteria) this;
		}

		public Criteria andPortIdIsNull() {
			addCriterion("port_id is null");
			return (Criteria) this;
		}

		public Criteria andPortIdIsNotNull() {
			addCriterion("port_id is not null");
			return (Criteria) this;
		}

		public Criteria andPortIdEqualTo(Integer value) {
			addCriterion("port_id =", value, "portId");
			return (Criteria) this;
		}

		public Criteria andPortIdNotEqualTo(Integer value) {
			addCriterion("port_id <>", value, "portId");
			return (Criteria) this;
		}

		public Criteria andPortIdGreaterThan(Integer value) {
			addCriterion("port_id >", value, "portId");
			return (Criteria) this;
		}

		public Criteria andPortIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("port_id >=", value, "portId");
			return (Criteria) this;
		}

		public Criteria andPortIdLessThan(Integer value) {
			addCriterion("port_id <", value, "portId");
			return (Criteria) this;
		}

		public Criteria andPortIdLessThanOrEqualTo(Integer value) {
			addCriterion("port_id <=", value, "portId");
			return (Criteria) this;
		}

		public Criteria andPortIdIn(List<Integer> values) {
			addCriterion("port_id in", values, "portId");
			return (Criteria) this;
		}

		public Criteria andPortIdNotIn(List<Integer> values) {
			addCriterion("port_id not in", values, "portId");
			return (Criteria) this;
		}

		public Criteria andPortIdBetween(Integer value1, Integer value2) {
			addCriterion("port_id between", value1, value2, "portId");
			return (Criteria) this;
		}

		public Criteria andPortIdNotBetween(Integer value1, Integer value2) {
			addCriterion("port_id not between", value1, value2, "portId");
			return (Criteria) this;
		}

		public Criteria andLanguageIsNull() {
			addCriterion("language is null");
			return (Criteria) this;
		}

		public Criteria andLanguageIsNotNull() {
			addCriterion("language is not null");
			return (Criteria) this;
		}

		public Criteria andLanguageEqualTo(String value) {
			addCriterion("language =", value, "language");
			return (Criteria) this;
		}

		public Criteria andLanguageNotEqualTo(String value) {
			addCriterion("language <>", value, "language");
			return (Criteria) this;
		}

		public Criteria andLanguageGreaterThan(String value) {
			addCriterion("language >", value, "language");
			return (Criteria) this;
		}

		public Criteria andLanguageGreaterThanOrEqualTo(String value) {
			addCriterion("language >=", value, "language");
			return (Criteria) this;
		}

		public Criteria andLanguageLessThan(String value) {
			addCriterion("language <", value, "language");
			return (Criteria) this;
		}

		public Criteria andLanguageLessThanOrEqualTo(String value) {
			addCriterion("language <=", value, "language");
			return (Criteria) this;
		}

		public Criteria andLanguageLike(String value) {
			addCriterion("language like", value, "language");
			return (Criteria) this;
		}

		public Criteria andLanguageNotLike(String value) {
			addCriterion("language not like", value, "language");
			return (Criteria) this;
		}

		public Criteria andLanguageIn(List<String> values) {
			addCriterion("language in", values, "language");
			return (Criteria) this;
		}

		public Criteria andLanguageNotIn(List<String> values) {
			addCriterion("language not in", values, "language");
			return (Criteria) this;
		}

		public Criteria andLanguageBetween(String value1, String value2) {
			addCriterion("language between", value1, value2, "language");
			return (Criteria) this;
		}

		public Criteria andLanguageNotBetween(String value1, String value2) {
			addCriterion("language not between", value1, value2, "language");
			return (Criteria) this;
		}

		public Criteria andSuperiorOfficeIdIsNull() {
			addCriterion("superior_office_id is null");
			return (Criteria) this;
		}

		public Criteria andSuperiorOfficeIdIsNotNull() {
			addCriterion("superior_office_id is not null");
			return (Criteria) this;
		}

		public Criteria andSuperiorOfficeIdEqualTo(String value) {
			addCriterion("superior_office_id =", value, "superiorOfficeId");
			return (Criteria) this;
		}

		public Criteria andSuperiorOfficeIdNotEqualTo(String value) {
			addCriterion("superior_office_id <>", value, "superiorOfficeId");
			return (Criteria) this;
		}

		public Criteria andSuperiorOfficeIdGreaterThan(String value) {
			addCriterion("superior_office_id >", value, "superiorOfficeId");
			return (Criteria) this;
		}

		public Criteria andSuperiorOfficeIdGreaterThanOrEqualTo(String value) {
			addCriterion("superior_office_id >=", value, "superiorOfficeId");
			return (Criteria) this;
		}

		public Criteria andSuperiorOfficeIdLessThan(String value) {
			addCriterion("superior_office_id <", value, "superiorOfficeId");
			return (Criteria) this;
		}

		public Criteria andSuperiorOfficeIdLessThanOrEqualTo(String value) {
			addCriterion("superior_office_id <=", value, "superiorOfficeId");
			return (Criteria) this;
		}

		public Criteria andSuperiorOfficeIdLike(String value) {
			addCriterion("superior_office_id like", value, "superiorOfficeId");
			return (Criteria) this;
		}

		public Criteria andSuperiorOfficeIdNotLike(String value) {
			addCriterion("superior_office_id not like", value,
					"superiorOfficeId");
			return (Criteria) this;
		}

		public Criteria andSuperiorOfficeIdIn(List<String> values) {
			addCriterion("superior_office_id in", values, "superiorOfficeId");
			return (Criteria) this;
		}

		public Criteria andSuperiorOfficeIdNotIn(List<String> values) {
			addCriterion("superior_office_id not in", values,
					"superiorOfficeId");
			return (Criteria) this;
		}

		public Criteria andSuperiorOfficeIdBetween(String value1, String value2) {
			addCriterion("superior_office_id between", value1, value2,
					"superiorOfficeId");
			return (Criteria) this;
		}

		public Criteria andSuperiorOfficeIdNotBetween(String value1,
				String value2) {
			addCriterion("superior_office_id not between", value1, value2,
					"superiorOfficeId");
			return (Criteria) this;
		}

		public Criteria andAutoInternalIsNull() {
			addCriterion("auto_internal is null");
			return (Criteria) this;
		}

		public Criteria andAutoInternalIsNotNull() {
			addCriterion("auto_internal is not null");
			return (Criteria) this;
		}

		public Criteria andAutoInternalEqualTo(Integer value) {
			addCriterion("auto_internal =", value, "autoInternal");
			return (Criteria) this;
		}

		public Criteria andAutoInternalNotEqualTo(Integer value) {
			addCriterion("auto_internal <>", value, "autoInternal");
			return (Criteria) this;
		}

		public Criteria andAutoInternalGreaterThan(Integer value) {
			addCriterion("auto_internal >", value, "autoInternal");
			return (Criteria) this;
		}

		public Criteria andAutoInternalGreaterThanOrEqualTo(Integer value) {
			addCriterion("auto_internal >=", value, "autoInternal");
			return (Criteria) this;
		}

		public Criteria andAutoInternalLessThan(Integer value) {
			addCriterion("auto_internal <", value, "autoInternal");
			return (Criteria) this;
		}

		public Criteria andAutoInternalLessThanOrEqualTo(Integer value) {
			addCriterion("auto_internal <=", value, "autoInternal");
			return (Criteria) this;
		}

		public Criteria andAutoInternalIn(List<Integer> values) {
			addCriterion("auto_internal in", values, "autoInternal");
			return (Criteria) this;
		}

		public Criteria andAutoInternalNotIn(List<Integer> values) {
			addCriterion("auto_internal not in", values, "autoInternal");
			return (Criteria) this;
		}

		public Criteria andAutoInternalBetween(Integer value1, Integer value2) {
			addCriterion("auto_internal between", value1, value2,
					"autoInternal");
			return (Criteria) this;
		}

		public Criteria andAutoInternalNotBetween(Integer value1, Integer value2) {
			addCriterion("auto_internal not between", value1, value2,
					"autoInternal");
			return (Criteria) this;
		}

		public Criteria andDeletedIsNull() {
			addCriterion("deleted is null");
			return (Criteria) this;
		}

		public Criteria andDeletedIsNotNull() {
			addCriterion("deleted is not null");
			return (Criteria) this;
		}

		public Criteria andDeletedEqualTo(Integer value) {
			addCriterion("deleted =", value, "deleted");
			return (Criteria) this;
		}

		public Criteria andDeletedNotEqualTo(Integer value) {
			addCriterion("deleted <>", value, "deleted");
			return (Criteria) this;
		}

		public Criteria andDeletedGreaterThan(Integer value) {
			addCriterion("deleted >", value, "deleted");
			return (Criteria) this;
		}

		public Criteria andDeletedGreaterThanOrEqualTo(Integer value) {
			addCriterion("deleted >=", value, "deleted");
			return (Criteria) this;
		}

		public Criteria andDeletedLessThan(Integer value) {
			addCriterion("deleted <", value, "deleted");
			return (Criteria) this;
		}

		public Criteria andDeletedLessThanOrEqualTo(Integer value) {
			addCriterion("deleted <=", value, "deleted");
			return (Criteria) this;
		}

		public Criteria andDeletedIn(List<Integer> values) {
			addCriterion("deleted in", values, "deleted");
			return (Criteria) this;
		}

		public Criteria andDeletedNotIn(List<Integer> values) {
			addCriterion("deleted not in", values, "deleted");
			return (Criteria) this;
		}

		public Criteria andDeletedBetween(Integer value1, Integer value2) {
			addCriterion("deleted between", value1, value2, "deleted");
			return (Criteria) this;
		}

		public Criteria andDeletedNotBetween(Integer value1, Integer value2) {
			addCriterion("deleted not between", value1, value2, "deleted");
			return (Criteria) this;
		}

		public Criteria andInputUserIsNull() {
			addCriterion("input_user is null");
			return (Criteria) this;
		}

		public Criteria andInputUserIsNotNull() {
			addCriterion("input_user is not null");
			return (Criteria) this;
		}

		public Criteria andInputUserEqualTo(String value) {
			addCriterion("input_user =", value, "inputUser");
			return (Criteria) this;
		}

		public Criteria andInputUserNotEqualTo(String value) {
			addCriterion("input_user <>", value, "inputUser");
			return (Criteria) this;
		}

		public Criteria andInputUserGreaterThan(String value) {
			addCriterion("input_user >", value, "inputUser");
			return (Criteria) this;
		}

		public Criteria andInputUserGreaterThanOrEqualTo(String value) {
			addCriterion("input_user >=", value, "inputUser");
			return (Criteria) this;
		}

		public Criteria andInputUserLessThan(String value) {
			addCriterion("input_user <", value, "inputUser");
			return (Criteria) this;
		}

		public Criteria andInputUserLessThanOrEqualTo(String value) {
			addCriterion("input_user <=", value, "inputUser");
			return (Criteria) this;
		}

		public Criteria andInputUserLike(String value) {
			addCriterion("input_user like", value, "inputUser");
			return (Criteria) this;
		}

		public Criteria andInputUserNotLike(String value) {
			addCriterion("input_user not like", value, "inputUser");
			return (Criteria) this;
		}

		public Criteria andInputUserIn(List<String> values) {
			addCriterion("input_user in", values, "inputUser");
			return (Criteria) this;
		}

		public Criteria andInputUserNotIn(List<String> values) {
			addCriterion("input_user not in", values, "inputUser");
			return (Criteria) this;
		}

		public Criteria andInputUserBetween(String value1, String value2) {
			addCriterion("input_user between", value1, value2, "inputUser");
			return (Criteria) this;
		}

		public Criteria andInputUserNotBetween(String value1, String value2) {
			addCriterion("input_user not between", value1, value2, "inputUser");
			return (Criteria) this;
		}

		public Criteria andInputUserNameIsNull() {
			addCriterion("input_user_name is null");
			return (Criteria) this;
		}

		public Criteria andInputUserNameIsNotNull() {
			addCriterion("input_user_name is not null");
			return (Criteria) this;
		}

		public Criteria andInputUserNameEqualTo(String value) {
			addCriterion("input_user_name =", value, "inputUserName");
			return (Criteria) this;
		}

		public Criteria andInputUserNameNotEqualTo(String value) {
			addCriterion("input_user_name <>", value, "inputUserName");
			return (Criteria) this;
		}

		public Criteria andInputUserNameGreaterThan(String value) {
			addCriterion("input_user_name >", value, "inputUserName");
			return (Criteria) this;
		}

		public Criteria andInputUserNameGreaterThanOrEqualTo(String value) {
			addCriterion("input_user_name >=", value, "inputUserName");
			return (Criteria) this;
		}

		public Criteria andInputUserNameLessThan(String value) {
			addCriterion("input_user_name <", value, "inputUserName");
			return (Criteria) this;
		}

		public Criteria andInputUserNameLessThanOrEqualTo(String value) {
			addCriterion("input_user_name <=", value, "inputUserName");
			return (Criteria) this;
		}

		public Criteria andInputUserNameLike(String value) {
			addCriterion("input_user_name like", value, "inputUserName");
			return (Criteria) this;
		}

		public Criteria andInputUserNameNotLike(String value) {
			addCriterion("input_user_name not like", value, "inputUserName");
			return (Criteria) this;
		}

		public Criteria andInputUserNameIn(List<String> values) {
			addCriterion("input_user_name in", values, "inputUserName");
			return (Criteria) this;
		}

		public Criteria andInputUserNameNotIn(List<String> values) {
			addCriterion("input_user_name not in", values, "inputUserName");
			return (Criteria) this;
		}

		public Criteria andInputUserNameBetween(String value1, String value2) {
			addCriterion("input_user_name between", value1, value2,
					"inputUserName");
			return (Criteria) this;
		}

		public Criteria andInputUserNameNotBetween(String value1, String value2) {
			addCriterion("input_user_name not between", value1, value2,
					"inputUserName");
			return (Criteria) this;
		}

		public Criteria andInputOfficeIsNull() {
			addCriterion("input_office is null");
			return (Criteria) this;
		}

		public Criteria andInputOfficeIsNotNull() {
			addCriterion("input_office is not null");
			return (Criteria) this;
		}

		public Criteria andInputOfficeEqualTo(String value) {
			addCriterion("input_office =", value, "inputOffice");
			return (Criteria) this;
		}

		public Criteria andInputOfficeNotEqualTo(String value) {
			addCriterion("input_office <>", value, "inputOffice");
			return (Criteria) this;
		}

		public Criteria andInputOfficeGreaterThan(String value) {
			addCriterion("input_office >", value, "inputOffice");
			return (Criteria) this;
		}

		public Criteria andInputOfficeGreaterThanOrEqualTo(String value) {
			addCriterion("input_office >=", value, "inputOffice");
			return (Criteria) this;
		}

		public Criteria andInputOfficeLessThan(String value) {
			addCriterion("input_office <", value, "inputOffice");
			return (Criteria) this;
		}

		public Criteria andInputOfficeLessThanOrEqualTo(String value) {
			addCriterion("input_office <=", value, "inputOffice");
			return (Criteria) this;
		}

		public Criteria andInputOfficeLike(String value) {
			addCriterion("input_office like", value, "inputOffice");
			return (Criteria) this;
		}

		public Criteria andInputOfficeNotLike(String value) {
			addCriterion("input_office not like", value, "inputOffice");
			return (Criteria) this;
		}

		public Criteria andInputOfficeIn(List<String> values) {
			addCriterion("input_office in", values, "inputOffice");
			return (Criteria) this;
		}

		public Criteria andInputOfficeNotIn(List<String> values) {
			addCriterion("input_office not in", values, "inputOffice");
			return (Criteria) this;
		}

		public Criteria andInputOfficeBetween(String value1, String value2) {
			addCriterion("input_office between", value1, value2, "inputOffice");
			return (Criteria) this;
		}

		public Criteria andInputOfficeNotBetween(String value1, String value2) {
			addCriterion("input_office not between", value1, value2,
					"inputOffice");
			return (Criteria) this;
		}

		public Criteria andUseSystemIsNull() {
			addCriterion("use_system is null");
			return (Criteria) this;
		}

		public Criteria andUseSystemIsNotNull() {
			addCriterion("use_system is not null");
			return (Criteria) this;
		}

		public Criteria andUseSystemEqualTo(Integer value) {
			addCriterion("use_system =", value, "useSystem");
			return (Criteria) this;
		}

		public Criteria andUseSystemNotEqualTo(Integer value) {
			addCriterion("use_system <>", value, "useSystem");
			return (Criteria) this;
		}

		public Criteria andUseSystemGreaterThan(Integer value) {
			addCriterion("use_system >", value, "useSystem");
			return (Criteria) this;
		}

		public Criteria andUseSystemGreaterThanOrEqualTo(Integer value) {
			addCriterion("use_system >=", value, "useSystem");
			return (Criteria) this;
		}

		public Criteria andUseSystemLessThan(Integer value) {
			addCriterion("use_system <", value, "useSystem");
			return (Criteria) this;
		}

		public Criteria andUseSystemLessThanOrEqualTo(Integer value) {
			addCriterion("use_system <=", value, "useSystem");
			return (Criteria) this;
		}

		public Criteria andUseSystemIn(List<Integer> values) {
			addCriterion("use_system in", values, "useSystem");
			return (Criteria) this;
		}

		public Criteria andUseSystemNotIn(List<Integer> values) {
			addCriterion("use_system not in", values, "useSystem");
			return (Criteria) this;
		}

		public Criteria andUseSystemBetween(Integer value1, Integer value2) {
			addCriterion("use_system between", value1, value2, "useSystem");
			return (Criteria) this;
		}

		public Criteria andUseSystemNotBetween(Integer value1, Integer value2) {
			addCriterion("use_system not between", value1, value2, "useSystem");
			return (Criteria) this;
		}

		public Criteria andCountryIdIsNull() {
			addCriterion("country_id is null");
			return (Criteria) this;
		}

		public Criteria andCountryIdIsNotNull() {
			addCriterion("country_id is not null");
			return (Criteria) this;
		}

		public Criteria andCountryIdEqualTo(Integer value) {
			addCriterion("country_id =", value, "countryId");
			return (Criteria) this;
		}

		public Criteria andCountryIdNotEqualTo(Integer value) {
			addCriterion("country_id <>", value, "countryId");
			return (Criteria) this;
		}

		public Criteria andCountryIdGreaterThan(Integer value) {
			addCriterion("country_id >", value, "countryId");
			return (Criteria) this;
		}

		public Criteria andCountryIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("country_id >=", value, "countryId");
			return (Criteria) this;
		}

		public Criteria andCountryIdLessThan(Integer value) {
			addCriterion("country_id <", value, "countryId");
			return (Criteria) this;
		}

		public Criteria andCountryIdLessThanOrEqualTo(Integer value) {
			addCriterion("country_id <=", value, "countryId");
			return (Criteria) this;
		}

		public Criteria andCountryIdIn(List<Integer> values) {
			addCriterion("country_id in", values, "countryId");
			return (Criteria) this;
		}

		public Criteria andCountryIdNotIn(List<Integer> values) {
			addCriterion("country_id not in", values, "countryId");
			return (Criteria) this;
		}

		public Criteria andCountryIdBetween(Integer value1, Integer value2) {
			addCriterion("country_id between", value1, value2, "countryId");
			return (Criteria) this;
		}

		public Criteria andCountryIdNotBetween(Integer value1, Integer value2) {
			addCriterion("country_id not between", value1, value2, "countryId");
			return (Criteria) this;
		}

		public Criteria andCountryCodeIsNull() {
			addCriterion("country_code is null");
			return (Criteria) this;
		}

		public Criteria andCountryCodeIsNotNull() {
			addCriterion("country_code is not null");
			return (Criteria) this;
		}

		public Criteria andCountryCodeEqualTo(String value) {
			addCriterion("country_code =", value, "countryCode");
			return (Criteria) this;
		}

		public Criteria andCountryCodeNotEqualTo(String value) {
			addCriterion("country_code <>", value, "countryCode");
			return (Criteria) this;
		}

		public Criteria andCountryCodeGreaterThan(String value) {
			addCriterion("country_code >", value, "countryCode");
			return (Criteria) this;
		}

		public Criteria andCountryCodeGreaterThanOrEqualTo(String value) {
			addCriterion("country_code >=", value, "countryCode");
			return (Criteria) this;
		}

		public Criteria andCountryCodeLessThan(String value) {
			addCriterion("country_code <", value, "countryCode");
			return (Criteria) this;
		}

		public Criteria andCountryCodeLessThanOrEqualTo(String value) {
			addCriterion("country_code <=", value, "countryCode");
			return (Criteria) this;
		}

		public Criteria andCountryCodeLike(String value) {
			addCriterion("country_code like", value, "countryCode");
			return (Criteria) this;
		}

		public Criteria andCountryCodeNotLike(String value) {
			addCriterion("country_code not like", value, "countryCode");
			return (Criteria) this;
		}

		public Criteria andCountryCodeIn(List<String> values) {
			addCriterion("country_code in", values, "countryCode");
			return (Criteria) this;
		}

		public Criteria andCountryCodeNotIn(List<String> values) {
			addCriterion("country_code not in", values, "countryCode");
			return (Criteria) this;
		}

		public Criteria andCountryCodeBetween(String value1, String value2) {
			addCriterion("country_code between", value1, value2, "countryCode");
			return (Criteria) this;
		}

		public Criteria andCountryCodeNotBetween(String value1, String value2) {
			addCriterion("country_code not between", value1, value2,
					"countryCode");
			return (Criteria) this;
		}

		public Criteria andCityIdIsNull() {
			addCriterion("city_id is null");
			return (Criteria) this;
		}

		public Criteria andCityIdIsNotNull() {
			addCriterion("city_id is not null");
			return (Criteria) this;
		}

		public Criteria andCityIdEqualTo(Integer value) {
			addCriterion("city_id =", value, "cityId");
			return (Criteria) this;
		}

		public Criteria andCityIdNotEqualTo(Integer value) {
			addCriterion("city_id <>", value, "cityId");
			return (Criteria) this;
		}

		public Criteria andCityIdGreaterThan(Integer value) {
			addCriterion("city_id >", value, "cityId");
			return (Criteria) this;
		}

		public Criteria andCityIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("city_id >=", value, "cityId");
			return (Criteria) this;
		}

		public Criteria andCityIdLessThan(Integer value) {
			addCriterion("city_id <", value, "cityId");
			return (Criteria) this;
		}

		public Criteria andCityIdLessThanOrEqualTo(Integer value) {
			addCriterion("city_id <=", value, "cityId");
			return (Criteria) this;
		}

		public Criteria andCityIdIn(List<Integer> values) {
			addCriterion("city_id in", values, "cityId");
			return (Criteria) this;
		}

		public Criteria andCityIdNotIn(List<Integer> values) {
			addCriterion("city_id not in", values, "cityId");
			return (Criteria) this;
		}

		public Criteria andCityIdBetween(Integer value1, Integer value2) {
			addCriterion("city_id between", value1, value2, "cityId");
			return (Criteria) this;
		}

		public Criteria andCityIdNotBetween(Integer value1, Integer value2) {
			addCriterion("city_id not between", value1, value2, "cityId");
			return (Criteria) this;
		}

		public Criteria andCityCodeIsNull() {
			addCriterion("city_code is null");
			return (Criteria) this;
		}

		public Criteria andCityCodeIsNotNull() {
			addCriterion("city_code is not null");
			return (Criteria) this;
		}

		public Criteria andCityCodeEqualTo(String value) {
			addCriterion("city_code =", value, "cityCode");
			return (Criteria) this;
		}

		public Criteria andCityCodeNotEqualTo(String value) {
			addCriterion("city_code <>", value, "cityCode");
			return (Criteria) this;
		}

		public Criteria andCityCodeGreaterThan(String value) {
			addCriterion("city_code >", value, "cityCode");
			return (Criteria) this;
		}

		public Criteria andCityCodeGreaterThanOrEqualTo(String value) {
			addCriterion("city_code >=", value, "cityCode");
			return (Criteria) this;
		}

		public Criteria andCityCodeLessThan(String value) {
			addCriterion("city_code <", value, "cityCode");
			return (Criteria) this;
		}

		public Criteria andCityCodeLessThanOrEqualTo(String value) {
			addCriterion("city_code <=", value, "cityCode");
			return (Criteria) this;
		}

		public Criteria andCityCodeLike(String value) {
			addCriterion("city_code like", value, "cityCode");
			return (Criteria) this;
		}

		public Criteria andCityCodeNotLike(String value) {
			addCriterion("city_code not like", value, "cityCode");
			return (Criteria) this;
		}

		public Criteria andCityCodeIn(List<String> values) {
			addCriterion("city_code in", values, "cityCode");
			return (Criteria) this;
		}

		public Criteria andCityCodeNotIn(List<String> values) {
			addCriterion("city_code not in", values, "cityCode");
			return (Criteria) this;
		}

		public Criteria andCityCodeBetween(String value1, String value2) {
			addCriterion("city_code between", value1, value2, "cityCode");
			return (Criteria) this;
		}

		public Criteria andCityCodeNotBetween(String value1, String value2) {
			addCriterion("city_code not between", value1, value2, "cityCode");
			return (Criteria) this;
		}

		public Criteria andIsDeptIsNull() {
			addCriterion("is_dept is null");
			return (Criteria) this;
		}

		public Criteria andIsDeptIsNotNull() {
			addCriterion("is_dept is not null");
			return (Criteria) this;
		}

		public Criteria andIsDeptEqualTo(Integer value) {
			addCriterion("is_dept =", value, "isDept");
			return (Criteria) this;
		}

		public Criteria andIsDeptNotEqualTo(Integer value) {
			addCriterion("is_dept <>", value, "isDept");
			return (Criteria) this;
		}

		public Criteria andIsDeptGreaterThan(Integer value) {
			addCriterion("is_dept >", value, "isDept");
			return (Criteria) this;
		}

		public Criteria andIsDeptGreaterThanOrEqualTo(Integer value) {
			addCriterion("is_dept >=", value, "isDept");
			return (Criteria) this;
		}

		public Criteria andIsDeptLessThan(Integer value) {
			addCriterion("is_dept <", value, "isDept");
			return (Criteria) this;
		}

		public Criteria andIsDeptLessThanOrEqualTo(Integer value) {
			addCriterion("is_dept <=", value, "isDept");
			return (Criteria) this;
		}

		public Criteria andIsDeptIn(List<Integer> values) {
			addCriterion("is_dept in", values, "isDept");
			return (Criteria) this;
		}

		public Criteria andIsDeptNotIn(List<Integer> values) {
			addCriterion("is_dept not in", values, "isDept");
			return (Criteria) this;
		}

		public Criteria andIsDeptBetween(Integer value1, Integer value2) {
			addCriterion("is_dept between", value1, value2, "isDept");
			return (Criteria) this;
		}

		public Criteria andIsDeptNotBetween(Integer value1, Integer value2) {
			addCriterion("is_dept not between", value1, value2, "isDept");
			return (Criteria) this;
		}

		public Criteria andRelactionTagIsNull() {
			addCriterion("relaction_tag is null");
			return (Criteria) this;
		}

		public Criteria andRelactionTagIsNotNull() {
			addCriterion("relaction_tag is not null");
			return (Criteria) this;
		}

		public Criteria andRelactionTagEqualTo(String value) {
			addCriterion("relaction_tag =", value, "relactionTag");
			return (Criteria) this;
		}

		public Criteria andRelactionTagNotEqualTo(String value) {
			addCriterion("relaction_tag <>", value, "relactionTag");
			return (Criteria) this;
		}

		public Criteria andRelactionTagGreaterThan(String value) {
			addCriterion("relaction_tag >", value, "relactionTag");
			return (Criteria) this;
		}

		public Criteria andRelactionTagGreaterThanOrEqualTo(String value) {
			addCriterion("relaction_tag >=", value, "relactionTag");
			return (Criteria) this;
		}

		public Criteria andRelactionTagLessThan(String value) {
			addCriterion("relaction_tag <", value, "relactionTag");
			return (Criteria) this;
		}

		public Criteria andRelactionTagLessThanOrEqualTo(String value) {
			addCriterion("relaction_tag <=", value, "relactionTag");
			return (Criteria) this;
		}

		public Criteria andRelactionTagLike(String value) {
			addCriterion("relaction_tag like", value, "relactionTag");
			return (Criteria) this;
		}

		public Criteria andRelactionTagNotLike(String value) {
			addCriterion("relaction_tag not like", value, "relactionTag");
			return (Criteria) this;
		}

		public Criteria andRelactionTagIn(List<String> values) {
			addCriterion("relaction_tag in", values, "relactionTag");
			return (Criteria) this;
		}

		public Criteria andRelactionTagNotIn(List<String> values) {
			addCriterion("relaction_tag not in", values, "relactionTag");
			return (Criteria) this;
		}

		public Criteria andRelactionTagBetween(String value1, String value2) {
			addCriterion("relaction_tag between", value1, value2,
					"relactionTag");
			return (Criteria) this;
		}

		public Criteria andRelactionTagNotBetween(String value1, String value2) {
			addCriterion("relaction_tag not between", value1, value2,
					"relactionTag");
			return (Criteria) this;
		}

		public Criteria andBookingProcessTypeIsNull() {
			addCriterion("booking_process_type is null");
			return (Criteria) this;
		}

		public Criteria andBookingProcessTypeIsNotNull() {
			addCriterion("booking_process_type is not null");
			return (Criteria) this;
		}

		public Criteria andBookingProcessTypeEqualTo(Integer value) {
			addCriterion("booking_process_type =", value, "bookingProcessType");
			return (Criteria) this;
		}

		public Criteria andBookingProcessTypeNotEqualTo(Integer value) {
			addCriterion("booking_process_type <>", value, "bookingProcessType");
			return (Criteria) this;
		}

		public Criteria andBookingProcessTypeGreaterThan(Integer value) {
			addCriterion("booking_process_type >", value, "bookingProcessType");
			return (Criteria) this;
		}

		public Criteria andBookingProcessTypeGreaterThanOrEqualTo(Integer value) {
			addCriterion("booking_process_type >=", value, "bookingProcessType");
			return (Criteria) this;
		}

		public Criteria andBookingProcessTypeLessThan(Integer value) {
			addCriterion("booking_process_type <", value, "bookingProcessType");
			return (Criteria) this;
		}

		public Criteria andBookingProcessTypeLessThanOrEqualTo(Integer value) {
			addCriterion("booking_process_type <=", value, "bookingProcessType");
			return (Criteria) this;
		}

		public Criteria andBookingProcessTypeIn(List<Integer> values) {
			addCriterion("booking_process_type in", values,
					"bookingProcessType");
			return (Criteria) this;
		}

		public Criteria andBookingProcessTypeNotIn(List<Integer> values) {
			addCriterion("booking_process_type not in", values,
					"bookingProcessType");
			return (Criteria) this;
		}

		public Criteria andBookingProcessTypeBetween(Integer value1,
				Integer value2) {
			addCriterion("booking_process_type between", value1, value2,
					"bookingProcessType");
			return (Criteria) this;
		}

		public Criteria andBookingProcessTypeNotBetween(Integer value1,
				Integer value2) {
			addCriterion("booking_process_type not between", value1, value2,
					"bookingProcessType");
			return (Criteria) this;
		}

		public Criteria andNotStatisticFlagIsNull() {
			addCriterion("not_statistic_flag is null");
			return (Criteria) this;
		}

		public Criteria andNotStatisticFlagIsNotNull() {
			addCriterion("not_statistic_flag is not null");
			return (Criteria) this;
		}

		public Criteria andNotStatisticFlagEqualTo(Integer value) {
			addCriterion("not_statistic_flag =", value, "notStatisticFlag");
			return (Criteria) this;
		}

		public Criteria andNotStatisticFlagNotEqualTo(Integer value) {
			addCriterion("not_statistic_flag <>", value, "notStatisticFlag");
			return (Criteria) this;
		}

		public Criteria andNotStatisticFlagGreaterThan(Integer value) {
			addCriterion("not_statistic_flag >", value, "notStatisticFlag");
			return (Criteria) this;
		}

		public Criteria andNotStatisticFlagGreaterThanOrEqualTo(Integer value) {
			addCriterion("not_statistic_flag >=", value, "notStatisticFlag");
			return (Criteria) this;
		}

		public Criteria andNotStatisticFlagLessThan(Integer value) {
			addCriterion("not_statistic_flag <", value, "notStatisticFlag");
			return (Criteria) this;
		}

		public Criteria andNotStatisticFlagLessThanOrEqualTo(Integer value) {
			addCriterion("not_statistic_flag <=", value, "notStatisticFlag");
			return (Criteria) this;
		}

		public Criteria andNotStatisticFlagIn(List<Integer> values) {
			addCriterion("not_statistic_flag in", values, "notStatisticFlag");
			return (Criteria) this;
		}

		public Criteria andNotStatisticFlagNotIn(List<Integer> values) {
			addCriterion("not_statistic_flag not in", values,
					"notStatisticFlag");
			return (Criteria) this;
		}

		public Criteria andNotStatisticFlagBetween(Integer value1,
				Integer value2) {
			addCriterion("not_statistic_flag between", value1, value2,
					"notStatisticFlag");
			return (Criteria) this;
		}

		public Criteria andNotStatisticFlagNotBetween(Integer value1,
				Integer value2) {
			addCriterion("not_statistic_flag not between", value1, value2,
					"notStatisticFlag");
			return (Criteria) this;
		}

		public Criteria andWebsiteAddressIsNull() {
			addCriterion("website_address is null");
			return (Criteria) this;
		}

		public Criteria andWebsiteAddressIsNotNull() {
			addCriterion("website_address is not null");
			return (Criteria) this;
		}

		public Criteria andWebsiteAddressEqualTo(String value) {
			addCriterion("website_address =", value, "websiteAddress");
			return (Criteria) this;
		}

		public Criteria andWebsiteAddressNotEqualTo(String value) {
			addCriterion("website_address <>", value, "websiteAddress");
			return (Criteria) this;
		}

		public Criteria andWebsiteAddressGreaterThan(String value) {
			addCriterion("website_address >", value, "websiteAddress");
			return (Criteria) this;
		}

		public Criteria andWebsiteAddressGreaterThanOrEqualTo(String value) {
			addCriterion("website_address >=", value, "websiteAddress");
			return (Criteria) this;
		}

		public Criteria andWebsiteAddressLessThan(String value) {
			addCriterion("website_address <", value, "websiteAddress");
			return (Criteria) this;
		}

		public Criteria andWebsiteAddressLessThanOrEqualTo(String value) {
			addCriterion("website_address <=", value, "websiteAddress");
			return (Criteria) this;
		}

		public Criteria andWebsiteAddressLike(String value) {
			addCriterion("website_address like", value, "websiteAddress");
			return (Criteria) this;
		}

		public Criteria andWebsiteAddressNotLike(String value) {
			addCriterion("website_address not like", value, "websiteAddress");
			return (Criteria) this;
		}

		public Criteria andWebsiteAddressIn(List<String> values) {
			addCriterion("website_address in", values, "websiteAddress");
			return (Criteria) this;
		}

		public Criteria andWebsiteAddressNotIn(List<String> values) {
			addCriterion("website_address not in", values, "websiteAddress");
			return (Criteria) this;
		}

		public Criteria andWebsiteAddressBetween(String value1, String value2) {
			addCriterion("website_address between", value1, value2,
					"websiteAddress");
			return (Criteria) this;
		}

		public Criteria andWebsiteAddressNotBetween(String value1, String value2) {
			addCriterion("website_address not between", value1, value2,
					"websiteAddress");
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
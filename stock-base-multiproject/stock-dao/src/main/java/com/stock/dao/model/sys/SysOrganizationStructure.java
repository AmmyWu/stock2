package com.stock.dao.model.sys;

import java.util.Date;

public class SysOrganizationStructure {
    
	private Integer organizationStructureId;

    private Integer superiorId;

    private Integer codeCommonSetId;

    private String codeName;

    private Integer type;

    private String name;

    private String officeNativeName;

    private String abbrev;

    private String businessRegisterNo;

    private String taxRegisterNo;

    private String portCode;

    private String contact;

    private String email;

    private String tel;

    private String supplierType;

    private String custType;

    private String fax;

    private String address;

    private Integer isInternal;

    private Integer isCustomer;

    private Integer isSupplier;

    private Integer active;

    private String xchgrName;

    private String companyCode;

    private String homeCurrency;

    private String settleOffice;

    private String blContent;

    private Integer companyId;

    private String custId;

    private String custCode;

    private String functionType;

    private Integer regionId;

    private String regionCode;

    private Integer portId;

    private String language;

    private String superiorOfficeId;

    private Integer autoInternal;

    private Integer deleted;

    private String inputUser;

    private String inputUserName;

    private String inputOffice;

    private Integer useSystem;

    private Integer countryId;

    private String countryCode;

    private Integer cityId;

    private String cityCode;

    private Integer isDept;

    private String relactionTag;

    private Integer bookingProcessType;

    private Integer notStatisticFlag;

    private String websiteAddress;

    private String description;

    private Integer creator;

    private Date createTime;

    private Integer accessGroup;

    private Integer amender;

    private Date amendTime;

    private Integer recordVersion;

    private Integer isDeleted;

    public Integer getOrganizationStructureId() {
        return organizationStructureId;
    }

    public void setOrganizationStructureId(Integer organizationStructureId) {
        this.organizationStructureId = organizationStructureId;
    }

    public Integer getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(Integer superiorId) {
        this.superiorId = superiorId;
    }

    public Integer getCodeCommonSetId() {
        return codeCommonSetId;
    }

    public void setCodeCommonSetId(Integer codeCommonSetId) {
        this.codeCommonSetId = codeCommonSetId;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName == null ? null : codeName.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOfficeNativeName() {
        return officeNativeName;
    }

    public void setOfficeNativeName(String officeNativeName) {
        this.officeNativeName = officeNativeName == null ? null : officeNativeName.trim();
    }

    public String getAbbrev() {
        return abbrev;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev == null ? null : abbrev.trim();
    }

    public String getBusinessRegisterNo() {
        return businessRegisterNo;
    }

    public void setBusinessRegisterNo(String businessRegisterNo) {
        this.businessRegisterNo = businessRegisterNo == null ? null : businessRegisterNo.trim();
    }

    public String getTaxRegisterNo() {
        return taxRegisterNo;
    }

    public void setTaxRegisterNo(String taxRegisterNo) {
        this.taxRegisterNo = taxRegisterNo == null ? null : taxRegisterNo.trim();
    }

    public String getPortCode() {
        return portCode;
    }

    public void setPortCode(String portCode) {
        this.portCode = portCode == null ? null : portCode.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType == null ? null : supplierType.trim();
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType == null ? null : custType.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getIsInternal() {
        return isInternal;
    }

    public void setIsInternal(Integer isInternal) {
        this.isInternal = isInternal;
    }

    public Integer getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(Integer isCustomer) {
        this.isCustomer = isCustomer;
    }

    public Integer getIsSupplier() {
        return isSupplier;
    }

    public void setIsSupplier(Integer isSupplier) {
        this.isSupplier = isSupplier;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getXchgrName() {
        return xchgrName;
    }

    public void setXchgrName(String xchgrName) {
        this.xchgrName = xchgrName == null ? null : xchgrName.trim();
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getHomeCurrency() {
        return homeCurrency;
    }

    public void setHomeCurrency(String homeCurrency) {
        this.homeCurrency = homeCurrency == null ? null : homeCurrency.trim();
    }

    public String getSettleOffice() {
        return settleOffice;
    }

    public void setSettleOffice(String settleOffice) {
        this.settleOffice = settleOffice == null ? null : settleOffice.trim();
    }

    public String getBlContent() {
        return blContent;
    }

    public void setBlContent(String blContent) {
        this.blContent = blContent == null ? null : blContent.trim();
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode == null ? null : custCode.trim();
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType == null ? null : functionType.trim();
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode == null ? null : regionCode.trim();
    }

    public Integer getPortId() {
        return portId;
    }

    public void setPortId(Integer portId) {
        this.portId = portId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getSuperiorOfficeId() {
        return superiorOfficeId;
    }

    public void setSuperiorOfficeId(String superiorOfficeId) {
        this.superiorOfficeId = superiorOfficeId == null ? null : superiorOfficeId.trim();
    }

    public Integer getAutoInternal() {
        return autoInternal;
    }

    public void setAutoInternal(Integer autoInternal) {
        this.autoInternal = autoInternal;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getInputUser() {
        return inputUser;
    }

    public void setInputUser(String inputUser) {
        this.inputUser = inputUser == null ? null : inputUser.trim();
    }

    public String getInputUserName() {
        return inputUserName;
    }

    public void setInputUserName(String inputUserName) {
        this.inputUserName = inputUserName == null ? null : inputUserName.trim();
    }

    public String getInputOffice() {
        return inputOffice;
    }

    public void setInputOffice(String inputOffice) {
        this.inputOffice = inputOffice == null ? null : inputOffice.trim();
    }

    public Integer getUseSystem() {
        return useSystem;
    }

    public void setUseSystem(Integer useSystem) {
        this.useSystem = useSystem;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode == null ? null : countryCode.trim();
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Integer getIsDept() {
        return isDept;
    }

    public void setIsDept(Integer isDept) {
        this.isDept = isDept;
    }

    public String getRelactionTag() {
        return relactionTag;
    }

    public void setRelactionTag(String relactionTag) {
        this.relactionTag = relactionTag == null ? null : relactionTag.trim();
    }

    public Integer getBookingProcessType() {
        return bookingProcessType;
    }

    public void setBookingProcessType(Integer bookingProcessType) {
        this.bookingProcessType = bookingProcessType;
    }

    public Integer getNotStatisticFlag() {
        return notStatisticFlag;
    }

    public void setNotStatisticFlag(Integer notStatisticFlag) {
        this.notStatisticFlag = notStatisticFlag;
    }

    public String getWebsiteAddress() {
        return websiteAddress;
    }

    public void setWebsiteAddress(String websiteAddress) {
        this.websiteAddress = websiteAddress == null ? null : websiteAddress.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getAccessGroup() {
        return accessGroup;
    }

    public void setAccessGroup(Integer accessGroup) {
        this.accessGroup = accessGroup;
    }

    public Integer getAmender() {
        return amender;
    }

    public void setAmender(Integer amender) {
        this.amender = amender;
    }

    public Date getAmendTime() {
        return amendTime;
    }

    public void setAmendTime(Date amendTime) {
        this.amendTime = amendTime;
    }

    public Integer getRecordVersion() {
        return recordVersion;
    }

    public void setRecordVersion(Integer recordVersion) {
        this.recordVersion = recordVersion;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
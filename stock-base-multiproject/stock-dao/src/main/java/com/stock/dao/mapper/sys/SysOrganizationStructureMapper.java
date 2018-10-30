package com.stock.dao.mapper.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.stock.dao.model.sys.SysOrganizationStructure;
import com.stock.dao.model.sys.SysOrganizationStructureExample;

public interface SysOrganizationStructureMapper {
    int countByExample(SysOrganizationStructureExample example);

    int deleteByExample(SysOrganizationStructureExample example);

    int deleteByPrimaryKey(Integer organizationStructureId);

    int insert(SysOrganizationStructure record);

    int insertSelective(SysOrganizationStructure record);

    List<SysOrganizationStructure> selectByExample(SysOrganizationStructureExample example);

    SysOrganizationStructure selectByPrimaryKey(Integer organizationStructureId);

    int updateByExampleSelective(@Param("record") SysOrganizationStructure record, @Param("example") SysOrganizationStructureExample example);

    int updateByExample(@Param("record") SysOrganizationStructure record, @Param("example") SysOrganizationStructureExample example);

    int updateByPrimaryKeySelective(SysOrganizationStructure record);

    int updateByPrimaryKey(SysOrganizationStructure record);
}
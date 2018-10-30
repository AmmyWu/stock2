package com.stock.dao.mapper.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.stock.dao.model.sys.SysSetting;
import com.stock.dao.model.sys.SysSettingExample;

public interface SysSettingMapper {
    int countByExample(SysSettingExample example);

    int deleteByExample(SysSettingExample example);

    int deleteByPrimaryKey(Integer stKey);

    int insert(SysSetting record);

    int insertSelective(SysSetting record);

    List<SysSetting> selectByExample(SysSettingExample example);

    SysSetting selectByPrimaryKey(Integer stKey);

    int updateByExampleSelective(@Param("record") SysSetting record, @Param("example") SysSettingExample example);

    int updateByExample(@Param("record") SysSetting record, @Param("example") SysSettingExample example);

    int updateByPrimaryKeySelective(SysSetting record);

    int updateByPrimaryKey(SysSetting record);
}
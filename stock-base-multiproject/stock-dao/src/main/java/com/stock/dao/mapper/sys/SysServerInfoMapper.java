package com.stock.dao.mapper.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.stock.dao.model.sys.SysServerInfo;
import com.stock.dao.model.sys.SysServerInfoExample;

public interface SysServerInfoMapper {
    int countByExample(SysServerInfoExample example);

    int deleteByExample(SysServerInfoExample example);

    int deleteByPrimaryKey(Integer serverInfoId);

    int insert(SysServerInfo record);

    int insertSelective(SysServerInfo record);

    List<SysServerInfo> selectByExample(SysServerInfoExample example);

    SysServerInfo selectByPrimaryKey(Integer serverInfoId);

    int updateByExampleSelective(@Param("record") SysServerInfo record, @Param("example") SysServerInfoExample example);

    int updateByExample(@Param("record") SysServerInfo record, @Param("example") SysServerInfoExample example);

    int updateByPrimaryKeySelective(SysServerInfo record);

    int updateByPrimaryKey(SysServerInfo record);
}
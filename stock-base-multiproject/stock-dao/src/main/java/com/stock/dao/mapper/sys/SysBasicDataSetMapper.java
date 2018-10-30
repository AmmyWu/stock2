package com.stock.dao.mapper.sys;

import com.stock.dao.model.sys.SysBasicDataSet;
import com.stock.dao.model.sys.SysBasicDataSetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysBasicDataSetMapper {
    int countByExample(SysBasicDataSetExample example);

    int deleteByExample(SysBasicDataSetExample example);

    int deleteByPrimaryKey(Integer basicDataSetId);

    int insert(SysBasicDataSet record);

    int insertSelective(SysBasicDataSet record);

    List<SysBasicDataSet> selectByExample(SysBasicDataSetExample example);

    SysBasicDataSet selectByPrimaryKey(Integer basicDataSetId);

    int updateByExampleSelective(@Param("record") SysBasicDataSet record, @Param("example") SysBasicDataSetExample example);

    int updateByExample(@Param("record") SysBasicDataSet record, @Param("example") SysBasicDataSetExample example);

    int updateByPrimaryKeySelective(SysBasicDataSet record);

    int updateByPrimaryKey(SysBasicDataSet record);
}
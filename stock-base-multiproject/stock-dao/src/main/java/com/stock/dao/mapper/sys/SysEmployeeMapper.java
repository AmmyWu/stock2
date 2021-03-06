package com.stock.dao.mapper.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.stock.dao.model.sys.SysEmployee;
import com.stock.dao.model.sys.SysEmployeeExample;

public interface SysEmployeeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_employee
     *
     * @mbggenerated Thu May 25 21:45:43 CST 2017
     */
    int countByExample(SysEmployeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_employee
     *
     * @mbggenerated Thu May 25 21:45:43 CST 2017
     */
    int deleteByExample(SysEmployeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_employee
     *
     * @mbggenerated Thu May 25 21:45:43 CST 2017
     */
    int deleteByPrimaryKey(Integer employeeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_employee
     *
     * @mbggenerated Thu May 25 21:45:43 CST 2017
     */
    int insert(SysEmployee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_employee
     *
     * @mbggenerated Thu May 25 21:45:43 CST 2017
     */
    int insertSelective(SysEmployee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_employee
     *
     * @mbggenerated Thu May 25 21:45:43 CST 2017
     */
    List<SysEmployee> selectByExample(SysEmployeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_employee
     *
     * @mbggenerated Thu May 25 21:45:43 CST 2017
     */
    SysEmployee selectByPrimaryKey(Integer employeeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_employee
     *
     * @mbggenerated Thu May 25 21:45:43 CST 2017
     */
    int updateByExampleSelective(@Param("record") SysEmployee record, @Param("example") SysEmployeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_employee
     *
     * @mbggenerated Thu May 25 21:45:43 CST 2017
     */
    int updateByExample(@Param("record") SysEmployee record, @Param("example") SysEmployeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_employee
     *
     * @mbggenerated Thu May 25 21:45:43 CST 2017
     */
    int updateByPrimaryKeySelective(SysEmployee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_employee
     *
     * @mbggenerated Thu May 25 21:45:43 CST 2017
     */
    int updateByPrimaryKey(SysEmployee record);
}
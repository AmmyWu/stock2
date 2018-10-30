package com.stock.service.sys.impl;


import com.stock.dao.mapper.sys.SysAuthenticationMapper;
import com.stock.dao.mapper.sys.SysEmployeeMapper;
import com.stock.dao.mapper.sys.SysUserMapper;
import com.stock.dao.model.sys.*;
import com.stock.service.sys.ssoClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ssoClientServiceImpl implements ssoClientService {

    @Autowired
    private SysEmployeeMapper sysEmployeeMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysAuthenticationMapper sysAuthenticationMapper;

    @Override
    public SysAuthentication getAuthenticationByEmployeeCode(String employeeCode) {

        SysEmployeeExample sysEmployeeExample = new SysEmployeeExample();
        SysUserExample sysUserExample = new SysUserExample();
        SysAuthenticationExample sysAuthenticationExample = new SysAuthenticationExample();

        sysEmployeeExample.createCriteria().andEmployeeCodeEqualTo(employeeCode);
        List<SysEmployee> sysEmployees = sysEmployeeMapper.selectByExample(sysEmployeeExample);
        if(sysEmployees == null) return null;

        sysUserExample.createCriteria().andUserDetailIdEqualTo(sysEmployees.get(0).getEmployeeId());
        List<SysUser> sysUsers = sysUserMapper.selectByExample(sysUserExample);
        if (sysUsers == null) return null;

        sysAuthenticationExample.createCriteria().andUserIdEqualTo(sysUsers.get(0).getUserId());
        List<SysAuthentication> sysAuthentications = sysAuthenticationMapper.selectByExample(sysAuthenticationExample);
        if (sysAuthentications == null) return null;

        return sysAuthentications.get(0);
    }

}

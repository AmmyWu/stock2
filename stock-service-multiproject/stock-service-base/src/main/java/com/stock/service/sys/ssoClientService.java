package com.stock.service.sys;

import com.stock.dao.model.sys.SysAuthentication;

public interface ssoClientService {

    SysAuthentication getAuthenticationByEmployeeCode(String employeeCode);

}

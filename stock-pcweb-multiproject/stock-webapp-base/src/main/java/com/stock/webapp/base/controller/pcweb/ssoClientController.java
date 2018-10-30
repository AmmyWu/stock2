package com.stock.webapp.base.controller.pcweb;

import com.stock.common.constants.HttpResponseConstants;
import com.stock.common.exceptions.BizException;
import com.stock.common.tool.db.AesDBUtil;
import com.stock.common.tool.db.AesEx;
import com.stock.dao.model.sys.SysAuthentication;
import com.stock.dao.model.sys.SysEmployee;
import com.stock.service.sys.SysEmployeeService;
import com.stock.service.sys.ssoClientService;
import com.stock.webapp.base.security.AuthencationResult;
import com.stock.webapp.base.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping(value = "/ssoClient")
public class ssoClientController {

    @Autowired
    private ssoClientService ssoClientService;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private SysEmployeeService sysEmployeeService;

    private static final String PASSWORD = "sbHC2Mf40_LOwMn60MXPor72st-31s72xr3MqDIwMTgtMTAtMjMgMTc6MzU6NTgxMw";
//    private static final String PASSWORD = "5YyX5LuR5Y2r55Sf55uR552j57u85ZCI5omn5rOV57O757ufMjAxOC0xMC0yNSAxMDowOTo0OTI3";

    @RequestMapping(value = "/loginBySSO.do")
    public void loginBySSO(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //解密单点登录系统发送的加密消息
        String code = new String(AesEx.decryptFromBase64(request.getParameter("encryptCode"),PASSWORD),"utf-8");
        System.out.println(">>>>>>>>>>");
        System.out.println(code);
        System.out.println(">>>>>>>>>>");
        //code = clientId+" "+employeeCode+" "+time
        String[] infos = code.split(" ");
        String employeeCode = infos[1];
        if(employeeCode == null){
            throw new BizException(HttpResponseConstants.Public.ERROR_700);
        }
        SysAuthentication sysAuthentication = null;
        try{
             sysAuthentication = ssoClientService.getAuthenticationByEmployeeCode(employeeCode);
        } catch(Exception e){
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            AuthencationResult authencationResult = new AuthencationResult();
            authencationResult.setMsg(employeeCode);
            out.print(authencationResult);
            out.flush();
            out.close();
        }


        /*request.setAttribute("account", sysAuthentication.getAccount());
        request.setAttribute("password", sysAuthentication.getPassword());
        MyAuthenticationFilterEx myAuthenticationFilterex = new MyAuthenticationFilterEx();
        myAuthenticationFilterex.attemptAuthentication(request, response);*/

        securityUtils.login(sysAuthentication.getAccount());
		//return  securityUtils.login(sysAuthentication.getAccount());
    }

    @RequestMapping(value = "/getEncryptClientId.do")
    public @ResponseBody String getEncryptClientId() throws Exception {
        return AesDBUtil.encryptToBase64(HttpResponseConstants.ssoClient.CLIENT_ID.getBytes(StandardCharsets.UTF_8));
    }

    @RequestMapping(value = "/registerEmployee")
    public @ResponseBody Object registerEmployee(SysEmployee sysEmployee){
        sysEmployee.setOrganizationStructureId(101);
        return sysEmployeeService.insert(sysEmployee);
    }
}

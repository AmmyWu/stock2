package com.stock.webapp.base.security;


import com.stock.dao.model.sys.*;
import com.stock.dao.util.DataAuthorityRegister;
import com.stock.service.sys.*;
import com.stock.service.sys.utils.SysConst;
import net.sf.json.JSONObject;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import javax.servlet.http.HttpSession;
import java.util.*;

public class SsoLoginServiceImpl{
    @Autowired
    private HttpSession session;

    @Autowired
    private SysAuthenticationService sysAuthenticationService;

    @Autowired
    private SysRoleResourceService rolesResourcesService;

    @Autowired
    private SysUserRoleService usersRolesService;

    @Autowired
    private SysEmployeeService employeeService;

    @Autowired
    private SysCustomerService customerService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysLoginLogService sysLoginLogService;

    @Autowired
    private SysAuthenticationService authenticationService;


    /**
     *
     * 设置登陆结果
     *
     * @param authentication
     * @param authResult
     */
    public void setAuthencationResult(Authentication authentication, AuthencationResult authResult) {



        SysAuthentication sysAuthentication = authenticationService
                .getAuthenticationByAccount(authentication.getName());

        SysUser user = sysUserService.getUsers(sysAuthentication.getUserId());

        authResult.setUser(user);
        authResult.setLoginingAccount(authentication.getName());

        authResult.setStatus(true);

        authResult.setMsg("登陆验证成功！");

        /*// 客户登陆
        if ("客户".equals(user.getType())) {

//			 this.doLoginForCustomer(session, user);

        }*/
        // 员工登陆
        if ("员工".equals(user.getType())) {


            this.doLoginForEmployee(authentication, authResult, user);
        }


        //记录日志
        MDC.put("UserId", user.getUserId());

        this.addLoginLog(authentication,sysAuthentication,user);
    }



    /**
     *
     * 处理员工登陆
     *
     * @param authentication
     * @param authResult
     * @param user
     */
    private void doLoginForEmployee(Authentication authentication,AuthencationResult authResult,SysUser user){

        SysEmployee employee = employeeService.getEmployeeById(user.getUserDetailId());
        SysCustomer customer = customerService.getCustomerById(user.getUserDetailId());
 //       HttpSession session = request.getSession();
        if(employee != null){
            authResult.setEmployee(employee);
            MDC.put("loginingAccessgroup",  employee.getOrganizationStructureId());
            session.setAttribute("employeeId", employee.getEmployeeId());
        }
        else{
            authResult.setCustomer(customer);
            session.setAttribute("employeeId", customer.getCustomerId());
        }
        //根据用户的角色，获取对应角色所赋予的资源id list
        List<Integer> rrsList = rolesResourcesService.getResourceIdList(this.getRoleList(authentication));

        // 未分配角色
        if (rrsList == null) {
            authResult.setMenuJSON(null);
            authResult.setMsg("您还未分配角色，请与管理员联系！");
        }else {
            // todo 设置主菜单
            JSONObject menuJSON = SysConst.RESOURCESTREE
                    .bulidMenuResourceTree(rrsList);

            // 设置button资源id
            authResult.setBtnResourceIds(rolesResourcesService
                    .getButtonResourceIds(rrsList));

            authResult.setMenuJSON(menuJSON);

        }



        session.setAttribute("roleList", rrsList);

        //当前登陆用户的组织架构id，新增记录需加入该值，做数据权限控制

//		session.setAttribute("loginingAccessgroup", employee.getOrganizationStructureId());

        // 添加数据授权登记
        DataAuthorityRegister dataAuthorityRegister = new DataAuthorityRegister();

        session.setAttribute("dataAuthorityRegister", dataAuthorityRegister);

        session.setAttribute("loginingUser", user);

    }

    /**
     *
     *根据授权信息，得到用户的role list
     * @param authentication
     * @return
     */
    private List<Integer> getRoleList(Authentication authentication){

        List<Integer> roleList = new ArrayList<Integer>();


        @SuppressWarnings("unchecked")
        Collection<GrantedAuthority> authoritySet =  (Collection<GrantedAuthority>) authentication.getAuthorities();

        Iterator<GrantedAuthority> it  = authoritySet.iterator();


        while(it.hasNext()){
            roleList.add(Integer.parseInt(it.next().getAuthority()));
        }

        return roleList;
    }

    /**
     *
     * 登陆时，分布在授权表（sys_authentication_info） 和 登陆日志表 sys_login_log 添加日志信息
     * @param authentication
     * @param authInfo
     * @param user
     */
    private void addLoginLog(Authentication authentication,SysAuthentication authInfo,SysUser user){


        WebAuthenticationDetails detail = authentication.getDetails() == null? null:(WebAuthenticationDetails) authentication.getDetails();

        String remoteAddress  = detail == null?"":detail.getRemoteAddress();

        authInfo.setLastIp(remoteAddress);
        authInfo.setLastTime(new Date());
        authInfo.setRecordVersion(0);

        user.setCreator(user.getUserId() );
        user.setCreateTime(new Date());
        user.setUserId(user.getUserId());
        user.setAmendTime(new Date());

        authenticationService.update(authInfo);

        SysLoginLog loginLog = new SysLoginLog();
        loginLog.setAuthenticationAccount(authInfo.getAccount());
        loginLog.setType("登陆");
        loginLog.setLoginIp(remoteAddress);
        loginLog.setLoginTime(new Date());
        loginLog.setCreator(user.getUserId() );
        loginLog.setCreateTime(new Date());
        loginLog.setUserId(user.getUserId());
        loginLog.setAmendTime(new Date());
        loginLog.setUsername(user.getUsername());
        loginLog.setAccessGroup(user.getAccessGroup());

        sysLoginLogService.insert(loginLog);
        //
    }

}

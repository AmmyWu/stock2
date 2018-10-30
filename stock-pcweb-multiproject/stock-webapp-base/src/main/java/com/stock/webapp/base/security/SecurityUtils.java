package com.stock.webapp.base.security;


import java.io.PrintWriter;

import com.stock.dao.model.sys.SysAuthentication;
import com.stock.service.sys.SysAuthenticationService;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自动登录权限工具类
 * @author sliansoft1
 *
 */
public class SecurityUtils {
	@Autowired
	private AuthenticationManager myAuthenticationManager;

    
    @Autowired
	private SysAuthenticationService sysAuthenticationService;

    @Autowired
    private SsoLoginServiceImpl ssoLoginService;
    /**
     * 判断当前用户是否已经登陆
     * @return 登陆状态返回 true, 否则返回 false
     */
    public static boolean isLogin() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return !"anonymousUser".equals(username);
    }

    /**
     * 登陆
     * @param username
     * @param userType
     * @return 
     */
    public void login(String username) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setCharacterEncoding("UTF-8");
        try {
        	SysAuthentication authenticationByAccount = sysAuthenticationService.getAuthenticationByAccount(username);
        	UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, authenticationByAccount.getPassword());
            token.setDetails(new WebAuthenticationDetails(request));
            Authentication authenticatedUser = myAuthenticationManager.authenticate(token); // 登陆
            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
            request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
            //设置session
 //           classLoginService.setLoginSession(username);
            AuthencationResult authResult = new AuthencationResult();
            ssoLoginService.setAuthencationResult(authenticatedUser, authResult);
            PrintWriter out = response.getWriter();
            out.print(JSONObject.fromObject(authResult));

    		out.flush();
    		out.close();
            // 重定向到登陆前的页面
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        //重定向
      /*  if (userType.equals("reserve")){
        	return Constants.ZJU_REDIRECT_URI_RESERVE;
        }else if (userType.equals("teacher")) {
        	return Constants.ZJU_REDIRECT_URI_TEACHER;
		}else if (userType.equals("mbreserve")) {
			return Constants.ZJU_REDIRECT_URI_RESERVE_MOBILE;
		}else {
			return Constants.ZJU_REDIRECT_URI_RESERVE;
		}*/
		//return username;
		
		
		
		
		
    }
}

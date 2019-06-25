package com.yn.web.configuration;

import com.yn.common.entity.ErrorCode;
import com.yn.common.entity.GlobalResult;
import com.yn.common.utils.ServletUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author dhc
 * 2019-03-05 16:09
 */
public class AuthenticationHandler implements AuthenticationEntryPoint, AccessDeniedHandler, AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler {
    private static final int ONE_MONTH = 0x278d00;

    // 需要登录
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        GlobalResult result = new GlobalResult();
        result.setCode(ErrorCode.USER_NOT_LOGIN.code());
        result.setMsg(ErrorCode.USER_NOT_LOGIN.message());
        ServletUtils.responseJson(response, result);
    }

    // 没有权限
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        GlobalResult result = new GlobalResult();
        result.setCode(ErrorCode.USER_NOT_AUTHORITY.code());
        result.setMsg(ErrorCode.USER_NOT_AUTHORITY.message());
        ServletUtils.responseJson(response, result);
    }

    // 登录失败
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        GlobalResult result = new GlobalResult();
        result.setCode(ErrorCode.USER_AUTH_FAIL.code());
        result.setMsg(ErrorCode.USER_AUTH_FAIL.message());
        ServletUtils.responseJson(response, result);
    }

    // 登录成功
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        // 设置Session时效为30天
        session.setMaxInactiveInterval(ONE_MONTH);
        response.setHeader("X-Auth-Token", session.getId());

        Object object = authentication.getPrincipal();
        GlobalResult result = new GlobalResult();
        result.setData(object);
        result.setSuccess(true);
        ServletUtils.responseJson(response, result);
    }

    // 登出成功
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        GlobalResult result = new GlobalResult();
        result.setSuccess(true);
        ServletUtils.responseJson(response, result);
    }
}

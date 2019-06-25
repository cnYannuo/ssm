package com.yn.common.filter;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static final String url_404 = "/page_404";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*request.getParameterMap().forEach((s, strings) -> {
            System.err.println(s+"---"+strings);
        });
        // Principal principal = SessionUtil.getCurrentPrincipal();
       *//* if(null!=principal){
            return true;
        }else{
            response.sendRedirect(request.getContextPath()+url_404);
        }*/
        return true;
    }
}
package com.yn.web.configuration.filter;

import com.alibaba.druid.util.StringUtils;
import com.yn.common.utils.ServletUtils;
import com.yn.domain.model.dto.SystemLoginDTO;
import com.yn.web.configuration.param.MPSignInParam;
import com.yn.web.configuration.param.OffAccSignInParam;
import com.yn.web.configuration.token.MPAuthenticationToken;
import com.yn.web.configuration.token.OffAccAuthenticationToken;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    //拦截请求路径
    private static final String WEB_LOGIN_URL = "/web/**/login";

    private static final String WEB = "web";
    private static final String OFFACC = "off_acc";
    private static final String MP = "mp";
    public AuthenticationFilter() {
        super(new AntPathRequestMatcher(WEB_LOGIN_URL, HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String requestURI = request.getRequestURI().split("/")[2];//获得**
        AbstractAuthenticationToken token = null;
        if(StringUtils.equals(requestURI, WEB)){
            SystemLoginDTO info = ServletUtils.getRequestBody(request, SystemLoginDTO.class);
            if (info == null) {
                throw new AuthenticationServiceException("Username and password are required!");
            }
            token = new UsernamePasswordAuthenticationToken(info, info.getUserPwd());

        }else if(StringUtils.equals(requestURI, OFFACC)){

            OffAccSignInParam param = ServletUtils.getRequestBody(request, OffAccSignInParam.class);
            if (param == null) {
                throw new AuthenticationServiceException("code are required!");
            }
            token = new OffAccAuthenticationToken(param);
        }else if(StringUtils.equals(requestURI, MP)){

            MPSignInParam param = ServletUtils.getRequestBody(request, MPSignInParam.class);
            if (param == null) {
                throw new AuthenticationServiceException("mpLoginCode are required!");
            }
            token = new MPAuthenticationToken(param);
        }

        token.setDetails(super.authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(token);
    }
}

package com.yn.web.configuration.provider;

import com.yn.common.entity.GlobalResult;
import com.yn.domain.model.bo.Principal;
import com.yn.domain.model.dto.SystemLoginDTO;
import com.yn.service.ISystemUserService;
import com.yn.web.configuration.param.OffAccSignInParam;
import com.yn.web.configuration.token.OffAccAuthenticationToken;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

public class OffAccAuthenticationProvider implements AuthenticationProvider {

    private ISystemUserService iSystemUserService;

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public OffAccAuthenticationProvider(ISystemUserService iSystemUserService) {
        this.iSystemUserService = iSystemUserService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(OffAccAuthenticationToken.class, authentication,
                messages.getMessage("WebUserAuthenticationProvider.onlySupports", "Only WebUserAuthenticationToken is supported"));

        OffAccAuthenticationToken token = (OffAccAuthenticationToken) authentication;
        OffAccSignInParam param = (OffAccSignInParam) token.getPrincipal();

        SystemLoginDTO dto = new SystemLoginDTO();

        GlobalResult result = iSystemUserService.login(dto, new GlobalResult());

        Principal principal = (Principal) result.getData();

//        List<String> list = (List<String>)map.get("auth");

        // 添加用户角色权限
        /*List<GrantedAuthority> authorities = list.stream()
                .map(string -> {
                    return new SimpleGrantedAuthority(string);
                }).collect(Collectors.toList());*/
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(principal.getRoleName()));
//        authorities.add(new SimpleGrantedAuthority(RoleConstans.USER));
        return new OffAccAuthenticationToken(principal, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OffAccAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

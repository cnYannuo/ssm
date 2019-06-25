package com.yn.web.configuration.provider;

import com.yn.service.ISystemUserService;
import com.yn.web.configuration.param.MPSignInParam;
import com.yn.web.configuration.token.MPAuthenticationToken;
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

public class MPAuthenticationProvider implements AuthenticationProvider {
    private ISystemUserService iSystemUserService;
    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public MPAuthenticationProvider(ISystemUserService iSystemUserService) {
        this.iSystemUserService = iSystemUserService;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(MPAuthenticationToken.class, authentication,
                messages.getMessage("WebUserAuthenticationProvider.onlySupports", "Only WebUserAuthenticationToken is supported"));

        MPAuthenticationToken token = (MPAuthenticationToken) authentication;
        MPSignInParam param = (MPSignInParam) token.getPrincipal();


//        SignedInfo info = thirdUserService.signIn(param);
//        UnifyUser user = userService.loadUserByUsername(username);
        /*if (user == null) {
            throw new UsernameNotFoundException(username);
        }*/

        // 添加用户角色权限
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(""));

//        return new UsernamePasswordAuthenticationToken(user, password, authorities);
        return new MPAuthenticationToken(null, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MPAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

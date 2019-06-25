package com.yn.web.configuration.provider;

import com.yn.common.entity.GlobalResult;
import com.yn.domain.model.bo.Principal;
import com.yn.domain.model.dto.SystemLoginDTO;
import com.yn.service.ISystemUserService;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

public class UserAuthenticationProvider implements AuthenticationProvider {
    private ISystemUserService iSystemUserService;
    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public UserAuthenticationProvider(ISystemUserService iSystemUserService) {
        this.iSystemUserService = iSystemUserService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
                messages.getMessage("WebUserAuthenticationProvider.onlySupports", "Only WebUserAuthenticationToken is supported"));

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        SystemLoginDTO dto = (SystemLoginDTO) token.getPrincipal();

//        userQO.addOperatorFilter(new OperatorFilter("account", SQLOperator.EQUAL, username));
        GlobalResult result = iSystemUserService.login(dto, new GlobalResult());
        Principal principal = (Principal) result.getData();
//        UnifyUser user = userService.loadUserByUsername(username);
        if (principal == null) {
            throw new UsernameNotFoundException(dto.getUserName());
        }
        // 检查密码是否匹配
//        checkPassword(user, password);

        /*List<RoleBean> roleBeans = roleService.getRoleByUserId(user.getId());*/

        // 添加用户角色权限
        if(principal.getRoleName() == null){
            return new UsernamePasswordAuthenticationToken(principal, dto.getUserName(), newArrayList());
        }
        String[] strings = principal.getRoleName().split(",");
        List<GrantedAuthority> authorities = Arrays.asList(strings)
                .stream()
                .map(str ->{
                    return new SimpleGrantedAuthority(str);
                })
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(principal, dto.getUserName(), authorities);
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}

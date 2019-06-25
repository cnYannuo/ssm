package com.yn.web.configuration;

import com.yn.common.entity.ErrorCode;
import com.yn.common.entity.GlobalResult;
import com.yn.common.utils.ServletUtils;
import com.yn.service.ISystemUserService;
import com.yn.web.configuration.filter.AuthenticationFilter;
import com.yn.web.configuration.provider.MPAuthenticationProvider;
import com.yn.web.configuration.provider.OffAccAuthenticationProvider;
import com.yn.web.configuration.provider.UserAuthenticationProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configurable
@EnableWebSecurity
//@EnableJdbcHttpSession // 如果是Redis 就改为EnableRedisHttpSession
@EnableRedisHttpSession
@EnableGlobalMethodSecurity(prePostEnabled=true)  // 支持类和方法的注解权限验证
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationHandler authenticationHandler = new AuthenticationHandler();

    private final AuthenticationFilter authenticationFilter;

    private final UserAuthenticationProvider userAuthenticationProvider;
    private final OffAccAuthenticationProvider offAccAuthenticationProvider;
    private final MPAuthenticationProvider mpAuthenticationProvider;

    @Value("${com.yn.security.ignoring}")
    private String ignoring;

    @Autowired
    public WebSecurityConfig(ISystemUserService iSystemUserService) {
        this.authenticationFilter = new AuthenticationFilter();

        this.userAuthenticationProvider = new UserAuthenticationProvider(iSystemUserService);
        this.offAccAuthenticationProvider = new OffAccAuthenticationProvider(iSystemUserService);
        this.mpAuthenticationProvider = new MPAuthenticationProvider(iSystemUserService);
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        authenticationFilter.setAuthenticationManager(super.authenticationManager());
        authenticationFilter.setAuthenticationSuccessHandler(authenticationHandler);
        authenticationFilter.setAuthenticationFailureHandler(authenticationHandler);

        GlobalResult result = new GlobalResult();
        result.setCode(ErrorCode.SESSION_EXPIRED.code());
        result.setMsg(ErrorCode.SESSION_EXPIRED.message());

        http.cors()//支持跨域
                .and().csrf().disable()//禁止表单提交
                .sessionManagement().maximumSessions(1).expiredSessionStrategy(event -> ServletUtils.responseJson(event.getResponse(), result))//设置session
                .and().sessionCreationPolicy(SessionCreationPolicy.NEVER)//设置session
                .and().exceptionHandling().authenticationEntryPoint(authenticationHandler).accessDeniedHandler(authenticationHandler)//设置异常处理器
                .and().authorizeRequests().anyRequest().authenticated()//要求所有进入应用的 HTTP请求都要进行认证
//                .antMatchers("/web/v1/admin").hasRole("ADMIN")
//                .and().formLogin().loginProcessingUrl(WEB_LOGIN_URL).successHandler(authenticationHandler).failureHandler(authenticationHandler)//设置login请求路径，成功时的控制器以及失败时的控制器
                .and().addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class)//设置过滤器
        ;
//                .logout().logoutUrl(WEB_LOGOUT_URL).logoutSuccessHandler(authenticationHandler);//设置登出过滤器和路径
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(userAuthenticationProvider)
            .authenticationProvider(offAccAuthenticationProvider)
            .authenticationProvider(mpAuthenticationProvider)
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(//设置忽略认证的请求路径
                "/",
                "/error",
                "favicon.ico",
                "/v2/api-docs",
                "/swagger-**/**",
                "/webjars/**");
        if(StringUtils.isNotBlank(ignoring)) {
            String[] ignorings = ignoring.trim().split(",");
            for (String ignoring : ignorings) {
                web.ignoring().antMatchers(ignoring);
            }
        }
    }

}

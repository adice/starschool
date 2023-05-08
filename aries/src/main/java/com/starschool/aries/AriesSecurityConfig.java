package com.starschool.aries;

import com.starschool.aries.security.*;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.DelegatingReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.LinkedList;

/**
 * @description: 网关安全配置
 * @author: adi
 * @since 2023/4/25 8:27
 */
@Configuration
@EnableWebFluxSecurity
public class AriesSecurityConfig {
    @Resource
    private AriesAuthenticationManager ariesAuthenticationManager;
    @Resource
    private AriesServerSecurityContextRepository ariesServerSecurityContextRepository;
    @Resource
    private AriesAuthorizationManager ariesAuthorizationManager;
//    @Resource
//    private AriesAuthenticationConverter ariesAuthenticationConverter;
    @Resource
    private AriesUserDetailsService ariesUserDetailsService;
    @Resource
    private AriesAuthenticationSuccessHandler ariesAuthenticationSuccessHandler;

    private static Mono<Authentication> authenticate(Authentication authentication) {
        // 其他登陆方式 (比如手机号验证码登陆) 可在此设置不得抛出异常或者 Mono.error
        return Mono.empty();
    }

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity
                // 登录认证处理
                .authenticationManager(reactiveAuthenticationManager())
//                .securityContextRepository(ariesServerSecurityContextRepository)
                // 请求拦截处理
                .authorizeExchange()
                .pathMatchers(SecurityConstant.WHITE_PATHS).permitAll()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .anyExchange().access(ariesAuthorizationManager)
                .and()
                .formLogin()
                .loginPage("/teacher/login")
                .authenticationSuccessHandler(ariesAuthenticationSuccessHandler)
                .and()
                .logout().disable()
                .csrf(ServerHttpSecurity.CsrfSpec::disable);
        return httpSecurity.build();

//        SecurityWebFilterChain securityWebFilterChain = httpSecurity
//                .formLogin()
//                .loginPage("/teacher/login")
//                .authenticationManager(ariesTokenAuthenticationManager)
//                .authenticationSuccessHandler(ariesAuthenticationSuccessHandler)
//                .and()
//                .authorizeExchange()
//                .pathMatchers(whitePaths).permitAll()
//                .anyExchange().access(ariesAuthorizationManager)
//                .and()
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .httpBasic().disable()
//                .build();
//
//        securityWebFilterChain.getWebFilters()
//                .filter(webFilter -> webFilter instanceof AuthenticationWebFilter)
//                .subscribe(webFilter -> {
//                    AuthenticationWebFilter filter = (AuthenticationWebFilter) webFilter;
//                    filter.setServerAuthenticationConverter(ariesAuthenticationConverter);
//                });
//        return securityWebFilterChain;
    }

    /**
     * 注册用户信息验证管理器
     * @return 验证管理器
     */
    @Bean
    ReactiveAuthenticationManager reactiveAuthenticationManager() {
        LinkedList<ReactiveAuthenticationManager> managers = new LinkedList<>();
//        managers.add(authentication -> {
//            return Mono.empty();
//        });
        // 必须放最后不然会优先使用用户名密码校验但是用户名密码不对时此 AuthenticationManager 会调用 Mono.error 造成后面的 AuthenticationManager 不生效
        managers.add(new UserDetailsRepositoryReactiveAuthenticationManager(ariesUserDetailsService));
//        managers.add(ariesTokenAuthenticationManager);
        return new DelegatingReactiveAuthenticationManager(managers);
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许访问的客户端域名
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedOriginPattern("*");
        //允许服务端访问的客户端请求头
        corsConfiguration.addAllowedHeader("*");
        //允许访问的方法名,GET POST等
        corsConfiguration.addAllowedMethod("*");
        //暴露哪些头部信息 不能用*因为跨域访问默认不能获取全部头部信息
        corsConfiguration.addExposedHeader("aries-token");
        corsConfiguration.addExposedHeader("server-ip-address");
        corsConfiguration.addExposedHeader("gateway-ip-address");
        corsConfiguration.setMaxAge(3000L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(source);
    }
}

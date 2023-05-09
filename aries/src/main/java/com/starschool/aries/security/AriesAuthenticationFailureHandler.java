package com.starschool.aries.security;

import com.alibaba.fastjson2.JSONObject;
import com.starschool.common.util.ResponseResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @description: 登录验证失败处理
 * @author: adi
 * @since 2023/5/8 17:27
 */
@Component
public class AriesAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        return Mono.defer(() -> Mono.just(webFilterExchange.getExchange().getResponse()).flatMap(response -> {
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            ResponseResult<Void> responseResult = null;
            if(exception instanceof UsernameNotFoundException) {
                responseResult = ResponseResult.failure(500, "账号不存在");
            } else if (exception instanceof BadCredentialsException) {
                responseResult = ResponseResult.failure(500, "账号密码不正确");
            } else if (exception instanceof AccountExpiredException) {
                responseResult = ResponseResult.failure(500, "账号已过期");
            } else if (exception instanceof LockedException) {
                responseResult = ResponseResult.failure(500, "账号已锁定");
            } else if (exception instanceof CredentialsExpiredException) {
                responseResult = ResponseResult.failure(500, "账号凭证已失效");
            } else if (exception instanceof DisabledException) {
                responseResult = ResponseResult.failure(500, "账号已禁用");
            }
            DataBuffer dataBuffer = dataBufferFactory.wrap(JSONObject.toJSONString(responseResult).getBytes());
            return response.writeWith(Mono.just(dataBuffer));
        }));
    }
}

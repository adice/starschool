package com.starschool.aries.security;

import com.alibaba.fastjson2.JSONObject;
import com.starschool.aries.entity.Role;
import com.starschool.aries.entity.Teacher;
import com.starschool.common.util.ResponseResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @description: 用户登录成功后续处理
 * @author: adi
 * @since 2023/4/25 14:34
 */
@Component
public class AriesAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler{
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        Teacher teacher = (Teacher)authentication.getPrincipal();
        teacher.setPassword(null);
        for (Role role : teacher.getRoles()) {
            role.setResources(null);
        }
        return Mono.defer(() -> Mono.just(webFilterExchange.getExchange().getResponse()).flatMap(response -> {
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            DataBuffer dataBuffer = dataBufferFactory.wrap(JSONObject.toJSONString(ResponseResult.success(teacher)).getBytes());
            return response.writeWith(Mono.just(dataBuffer));
        }));
    }
}

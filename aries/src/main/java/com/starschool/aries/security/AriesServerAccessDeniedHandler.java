package com.starschool.aries.security;

import com.alibaba.fastjson2.JSONObject;
import com.starschool.common.util.ResponseResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * @description: 鉴权失败后处理
 * @author: adi
 * @since 2023/5/9 10:50
 */
@Component
public class AriesServerAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        return Mono.defer(() -> Mono.just(exchange.getResponse()))
                .flatMap(response -> {
                    DataBufferFactory dataBufferFactory = response.bufferFactory();
                    String result = JSONObject.toJSONString(ResponseResult.failure(500, "您无权访问"));
                    DataBuffer buffer = dataBufferFactory.wrap(result.getBytes(Charset.defaultCharset()));
                    return response.writeWith(Mono.just(buffer));
                });
    }
}

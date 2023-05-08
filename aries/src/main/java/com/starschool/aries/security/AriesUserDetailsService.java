package com.starschool.aries.security;

import com.starschool.aries.entity.Role;
import com.starschool.aries.entity.Teacher;
import com.starschool.aries.feignclient.TaurusFeignClient;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @description: 用户登录验证
 * @author: adi
 * @since 2023/4/25 14:53
 */
@Service
@Transactional(readOnly = true)
public class AriesUserDetailsService implements ReactiveUserDetailsService {

    @Resource
    @Lazy
    private TaurusFeignClient taurusFeignClient;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        if (username.startsWith("t")) {
            Future<Teacher> future = Executors.newFixedThreadPool(1)
                    .submit(() -> taurusFeignClient.get4Login(username.substring(1)));
            Teacher teacher;
            try {
                teacher = future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (teacher != null) {
                editAuthorities(teacher);
                return Mono.just(teacher);
            } else {
                return Mono.defer(() -> Mono.error(new UsernameNotFoundException("账号不存在")));
            }
        } else if (username.startsWith("s")) {
            return null;
        } else
            return Mono.defer(() -> Mono.error(new UsernameNotFoundException("输入参数不正确")));
    }

    public void editAuthorities(Teacher teacher) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (Role role : teacher.getRoles()) {
            for (com.starschool.aries.entity.Resource resource : role.getResources()) {
               if(StringUtils.hasText(resource.getUrl())){
                   authorities.add(new SimpleGrantedAuthority(resource.getUrl()));
               }
            }
        }
        teacher.setAuthorities(authorities);
    }
}

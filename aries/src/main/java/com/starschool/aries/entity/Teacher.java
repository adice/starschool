package com.starschool.aries.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @description: 教师实体类
 * @author: adi
 * @since 2023/4/23 16:26
 */
@Setter
@Getter
public class Teacher implements UserDetails {

    @Serial
    private static final long serialVersionUID = -8744678010112173760L;

    private String username;    //教工编号
    private String password;
    private String name;
    private String email;
    private String phone;
    private Date registTime;
    private int loginSuccessCount;
    private int loginFailureCount;  //1天内登录错误次数，超过5次，当天不允许登录
    private Set<Role> roles = new HashSet<>(0);

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Date lockTime;
    private Collection<? extends GrantedAuthority> authorities;
}

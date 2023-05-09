package com.starschool.aries.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @description: 权限资源实体类
 * @author: adi
 * @since 2023/4/23 16:55
 */
@Setter
@Getter
public class Permission implements Serializable {
    @Serial
    private static final long serialVersionUID = 5065363833846742417L;
    private int id;
    private String title;
    private String url;
    private String icon;
    private int priority;
    private int permissionType;       // 1-菜单，2-其它资源等(例如按钮、超链接)，后续补充
    private int state;      // 1-正常，2-禁用
    private Permission parentPermission;
    private Set<Permission> childPermissions = new HashSet<>();
}

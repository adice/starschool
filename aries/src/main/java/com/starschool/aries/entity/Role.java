package com.starschool.aries.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @description: 角色实体类
 * @author: adi
 * @since 2023/4/23 16:55
 */
@Setter
@Getter
public class Role implements Serializable {
    @Serial
    private static final long serialVersionUID = 3574175592477251497L;

    private int id;
    private String title;
    private String name;
    private int priority;
    private int state;      // 1-正常，2-禁用
    private Set<Resource> resources = new HashSet<>();
}

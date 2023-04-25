package com.starschool.taurus.entity;

import jakarta.persistence.*;
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

@Entity
@Table(name = "user_role")
@Setter
@Getter
public class Role implements Serializable {
    @Serial
    private static final long serialVersionUID = 3574175592477251497L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @Column(length = 20)
    private String name;
    private int priority;
    private int state;      // 1-正常，2-禁用
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_rel_role_resource",
            joinColumns = {@JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))},
            inverseJoinColumns = {@JoinColumn(name = "resource_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))})
    private Set<Resource> resources = new HashSet<>();

}

package com.starschool.taurus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @description: 教师实体类
 * @author: adi
 * @since 2023/4/23 16:26
 */
@Entity
@Table(name = "user_teacher")
@Setter
@Getter
public class Teacher implements Serializable {

    @Serial
    private static final long serialVersionUID = -8744678010112173760L;
    @Id
    @GenericGenerator(name = "teacher_assigned", strategy = "assigned")
    @GeneratedValue(generator = "teacher_assigned")
    @Column(length = 20)
    private String username;    //教工编号
    @Column(length = 100)
    private String password;
    @Column(unique = true, length = 50)
    private String name;
    @Column(length = 100)
    private String email;
    @Column(length = 20)
    private String phone;
    @Column(length = 50)
    private Date registTime;
    private int loginSuccessCount;
    private int loginFailureCount;  //1天内登录错误次数，超过5次，当天不允许登录
    @JsonIgnore
    @ManyToMany
    @OrderBy("priority")
    @JoinTable(name = "user_rel_role_teacher",
            joinColumns = {@JoinColumn(name = "teacher_username", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))},
            inverseJoinColumns = {@JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))})
    private Set<Role> roles = new HashSet<>(0);

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Date lockTime;

}

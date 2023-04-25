package com.starschool.taurus.entity;

import jakarta.persistence.*;

import java.util.Date;

/**
 * @description: TODO
 * @author: adi
 * @since 2023/4/23 16:56
 */
@Entity
@Table(name = "user_login_record")
public class TeacherLoginRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String loginIp;
    private Date loginTime;
    private int loginState;
    @ManyToOne
    @JoinColumn(name = "teacher_username", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Teacher teacher;
}

package com.starschool.taurus.teacher.dao;

import com.starschool.taurus.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherDao extends JpaRepository<Teacher, String> {
    public Teacher findByUsername(String username);
}

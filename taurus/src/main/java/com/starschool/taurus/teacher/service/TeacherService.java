package com.starschool.taurus.teacher.service;

import com.starschool.taurus.entity.Teacher;
import com.starschool.taurus.teacher.dao.TeacherDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: TODO
 * @author: adi
 * @since 2023/4/24 16:42
 */
@Service
@Transactional(readOnly = true)
public class TeacherService {
    @Resource
    private TeacherDao teacherDao;

    public Teacher getTeacher(String username) {
        return teacherDao.findByUsername(username);
    }
}

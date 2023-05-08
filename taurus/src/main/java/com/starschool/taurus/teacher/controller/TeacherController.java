package com.starschool.taurus.teacher.controller;

import com.starschool.taurus.entity.Teacher;
import com.starschool.taurus.teacher.service.TeacherService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 教师控制器类
 * @author: adi
 * @since 2023/4/24 16:54
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Resource
    private TeacherService teacherService;

    @GetMapping("/test/{id}")
    public String test(@PathVariable("id") int id){
        System.out.println(id);
        return "testtest";
    }
    @GetMapping("/login/{username}")
    public Teacher get4Login(@PathVariable("username") String username) {
        return teacherService.getTeacher(username);

    }
}

package com.starschool.aries.feignclient;

import com.starschool.aries.entity.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("taurus")
public interface TaurusFeignClient {
    @GetMapping("/teacher/login/{username}")
    Teacher get4Login(@PathVariable("username") String username);
}

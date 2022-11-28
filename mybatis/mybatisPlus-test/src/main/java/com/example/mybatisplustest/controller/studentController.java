package com.example.mybatisplustest.controller;

import com.example.mybatisplustest.entity.Student;
import com.example.mybatisplustest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class studentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/addStudent")
    public String addStudent(@PathVariable String name,@PathVariable String age){
        int insert = studentService.getBaseMapper().insert(new Student("zz", 13));
        return insert+"";
    }

}

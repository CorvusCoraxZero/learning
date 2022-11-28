package com.example.mybatisplustest;

import com.example.mybatisplustest.entity.Student;
import com.example.mybatisplustest.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MybatisPlusTestApplicationTests {

    @Autowired
    StudentService studentService;

    @Test
    void contextLoads() {
        studentService.save(new Student("www",18));
    }

}

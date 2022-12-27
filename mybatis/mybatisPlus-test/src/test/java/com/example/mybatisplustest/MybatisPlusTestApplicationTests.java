package com.example.mybatisplustest;

import com.example.mybatisplustest.entity.Student;
import com.example.mybatisplustest.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// @SpringBootTest
class MybatisPlusTestApplicationTests {

    @Autowired
    StudentService studentService;

    @Test
    void contextLoads() {
        studentService.save(new Student("www",18));
    }

    @Test
    void testOption() {
        System.out.println(studentService.testParam(1,24,""));
    }

    @Test
    void testOptionNoObject() {
        System.out.println(studentService.testParamWithoutObject(18,""));
    }

    @Test
    void testParamObjectWithMap() {
        System.out.println(studentService.testParamObjectWithMap(18,""));
    }

    @Test
    void testAnd() {
        System.out.println(studentService.testAnd(18,""));
    }

    @Test
    void testString() {
        String num = "0123456789";
        System.out.println(num.substring(1));
    }

}

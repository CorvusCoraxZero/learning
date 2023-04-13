package com.example.mybatisplustest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatisplustest.entity.QueryOption;
import com.example.mybatisplustest.entity.Student;
import com.example.mybatisplustest.mapper.StudentMapper;
import com.example.mybatisplustest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    StudentMapper mapper;

    public Student testParam(Integer id, Integer num, String value) {
        QueryOption option = new QueryOption(num, value);
        Student student = mapper.testParam(2, option);
        return student;
    }

    public Student testParamPage(Integer num, String value) {
        QueryOption option = new QueryOption(num, value);
        Student student = mapper.testParamPage(new Page<Student>(3,1),option);
        return student;
    }

    public Student testParamWithoutObject(Integer num, String value) {
        QueryOption option = new QueryOption(num, value);
        Student student = mapper.testParamNoObject(option);
        return student;
    }

    @Override
    public Student testParamObjectWithMap(Integer num, String value) {
        QueryOption option = new QueryOption(num, value);
        HashMap<String, Object> map = new HashMap<>();
        map.put("age",new Integer(18));
        option.setParams(map);
        Student student = mapper.testParamObjectWithMap(option,map);

        return null;
    }

    @Override
    public Student testAnd(Integer num, String value) {
        // and
        this.getOne(new LambdaQueryWrapper<Student>().eq(Student::getAge,18).eq(Student::getName,"www"));
        // and()
        this.getOne(new LambdaQueryWrapper<Student>().eq(Student::getAge,18).and(wra -> wra.eq(Student::getName,"www").eq(Student::getId,1)));
        // and(and())
        this.getOne(new LambdaQueryWrapper<Student>().eq(Student::getAge,18).and(wra -> wra.eq(Student::getName,"www").and(bb -> bb.eq(Student::getId,1))));
        // and().and
        this.getOne(new LambdaQueryWrapper<Student>().eq(Student::getAge,18).and(wra -> wra.eq(Student::getName,"www")).eq(Student::getId,1));
        // and().and()
        this.getOne(new LambdaQueryWrapper<Student>().eq(Student::getAge,18).and(wra -> wra.eq(Student::getName,"www")).and(bb -> bb.eq(Student::getId,1)));

        return null;
    }
}

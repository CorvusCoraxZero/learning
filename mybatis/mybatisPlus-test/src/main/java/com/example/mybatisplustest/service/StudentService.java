package com.example.mybatisplustest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatisplustest.entity.Student;
import org.apache.ibatis.annotations.Param;

public interface StudentService extends IService<Student> {
    public Student testParam(@Param("id") Integer id, Integer num, String value);

    public Student testParamPage(Integer num, String value);

    public Student testParamWithoutObject(Integer num, String value);

    public Student testParamObjectWithMap(Integer num, String value);

    public Student testAnd(Integer num, String value);


}

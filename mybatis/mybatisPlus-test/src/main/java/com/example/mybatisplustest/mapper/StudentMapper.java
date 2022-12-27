package com.example.mybatisplustest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplustest.entity.QueryOption;
import com.example.mybatisplustest.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface StudentMapper extends BaseMapper<Student> {
    public Student testParam(int id, QueryOption option);

    public Student testParamPage(Page<Student> page, QueryOption option);

    public Student testParamNoObject(@Param("option") QueryOption option);
    
    public Student testParamObjectWithMap(@Param("option") QueryOption option,@Param("map") Map map);
    
    
}

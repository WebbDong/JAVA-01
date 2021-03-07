package com.webbdong.dynamicdatasource.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.webbdong.dynamicdatasource.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Webb Dong
 * @date 2021-03-08 1:43 AM
 */
@Mapper
public interface StudentDao {

    @DS("db2")
    @Select("SELECT * FROM `student`")
    List<Student> selectAll();

}

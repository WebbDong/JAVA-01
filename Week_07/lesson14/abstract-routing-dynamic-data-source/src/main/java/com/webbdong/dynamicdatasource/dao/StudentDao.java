package com.webbdong.dynamicdatasource.dao;

import com.webbdong.dynamicdatasource.annotation.TargetDataSource;
import com.webbdong.dynamicdatasource.consts.DataSourceConsts;
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

    @TargetDataSource(name = DataSourceConsts.MYSQL2)
    @Select("SELECT * FROM `student`")
    List<Student> selectAll();

}

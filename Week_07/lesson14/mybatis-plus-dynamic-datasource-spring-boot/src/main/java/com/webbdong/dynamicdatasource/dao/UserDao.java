package com.webbdong.dynamicdatasource.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.webbdong.dynamicdatasource.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Webb Dong
 * @date 2021-03-08 1:44 AM
 */
@Mapper
public interface UserDao {

    @DS("db1")
    @Select("SELECT * FROM `t_user`")
    List<User> selectAll();

}

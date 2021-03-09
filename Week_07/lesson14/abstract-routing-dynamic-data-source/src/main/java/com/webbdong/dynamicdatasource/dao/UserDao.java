package com.webbdong.dynamicdatasource.dao;

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

    @Select("SELECT * FROM `t_user`")
    List<User> selectAll();

}

package com.webbdong.shardingsphere.readwritesplit.dao;

import com.webbdong.shardingsphere.readwritesplit.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Webb Dong
 * @date 2021-03-08 6:56 PM
 */
@Mapper
public interface UserDao {

    @Select("SELECT * FROM `t_user`")
    List<User> selectAll();

    @Insert("INSERT INTO `t_user`(`username`, `pwd`, `gender`, `birthday`, `state`, `created_time`, `updated_time`) " +
            "VALUES(#{username}, #{pwd}, #{gender}, #{birthday}, #{state}, #{created_time}, #{updated_time})")
    int insert(User user);

}

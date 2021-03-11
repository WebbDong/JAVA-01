package com.webbdong.readwritesplit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.webbdong.readwritesplit.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Webb Dong
 * @date 2021-03-08 1:44 AM
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM `t_user`")
    List<User> selectAll();

}

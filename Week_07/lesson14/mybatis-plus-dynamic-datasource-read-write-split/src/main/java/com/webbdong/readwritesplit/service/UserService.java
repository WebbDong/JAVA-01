package com.webbdong.readwritesplit.service;

import com.webbdong.readwritesplit.entity.User;

import java.util.List;

/**
 * @author Webb Dong
 * @date 2021-03-10 2:44 PM
 */
public interface UserService {

    List<User> selectAll();

    void insert(User user);

    int updateById(User user);

    void selectAndUpdate();

}

package com.webbdong.readwritesplit.service.impl;

import com.webbdong.readwritesplit.mapper.UserMapper;
import com.webbdong.readwritesplit.entity.User;
import com.webbdong.readwritesplit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Webb Dong
 * @date 2021-03-10 2:44 PM
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public int updateById(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public void selectAndUpdate() {
        List<User> users = userMapper.selectAll();
        System.out.println(users);
        System.out.println(users.size());

        User updateUser = new User();
        updateUser.setId(5L);
        updateUser.setUsername("Kobe");
        userMapper.updateById(updateUser);
    }

}

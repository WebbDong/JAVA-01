package com.webbdong.readwritesplit.test;

import com.webbdong.readwritesplit.Application;
import com.webbdong.readwritesplit.entity.User;
import com.webbdong.readwritesplit.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Webb Dong
 * @date 2021-03-08 2:11 AM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectAll() {
        List<User> users = userMapper.selectAll();
        System.out.println(users);
    }

}

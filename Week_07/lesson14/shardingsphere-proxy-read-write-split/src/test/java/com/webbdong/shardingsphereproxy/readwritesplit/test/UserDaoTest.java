package com.webbdong.shardingsphereproxy.readwritesplit.test;

import com.webbdong.shardingsphereproxy.readwritesplit.Application;
import com.webbdong.shardingsphereproxy.readwritesplit.dao.UserDao;
import com.webbdong.shardingsphereproxy.readwritesplit.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author Webb Dong
 * @date 2021-03-09 12:24 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testSelectAll() {
        List<User> users = userDao.selectAll();
        System.out.println(users);
    }

    @Test
    public void testInsert() {
        User user = new User();
        Date now = new Date();
        for (int i = 1; i < 11; i++) {
            user.setUsername("user" + i);
            user.setPwd("123456");
            user.setGender(1);
            user.setBirthday(now);
            user.setCreatedTime(now);
            user.setUpdatedTime(now);
            user.setState(0);
            userDao.insert(user);
        }
    }

}

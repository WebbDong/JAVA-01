package com.webbdong.readwritesplit.test;

import com.webbdong.readwritesplit.Application;
import com.webbdong.readwritesplit.entity.User;
import com.webbdong.readwritesplit.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Webb Dong
 * @date 2021-03-11 1:22 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testSelectAll() {
        List<User> users = userService.selectAll();
        System.out.println(users);
        System.out.println(users.size());
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setUsername("user104");
        user.setPwd("654321");
        user.setGender(1);
        user.setBirthday(LocalDate.now());
        user.setCreatedTime(LocalDateTime.now());
        user.setUpdatedTime(LocalDateTime.now());
        user.setState(0);
        userService.insert(user);
    }

    @Test
    public void testSelectAndUpdate() {
        userService.selectAndUpdate();
    }

}

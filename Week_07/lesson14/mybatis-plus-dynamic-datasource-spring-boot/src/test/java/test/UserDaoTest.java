package test;

import com.webbdong.dynamicdatasource.Application;
import com.webbdong.dynamicdatasource.dao.UserDao;
import com.webbdong.dynamicdatasource.entity.User;
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
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testSelectAll() {
        List<User> users = userDao.selectAll();
        System.out.println(users);
    }

}

package com.webbdong.dynamicdatasource.test;

import com.webbdong.dynamicdatasource.Application;
import com.webbdong.dynamicdatasource.dao.StudentDao;
import com.webbdong.dynamicdatasource.entity.Student;
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
public class StudentDaoTest {

    @Autowired
    private StudentDao studentDao;

    @Test
    public void testSelectAll() {
        List<Student> students = studentDao.selectAll();
        System.out.println(students);
    }

}

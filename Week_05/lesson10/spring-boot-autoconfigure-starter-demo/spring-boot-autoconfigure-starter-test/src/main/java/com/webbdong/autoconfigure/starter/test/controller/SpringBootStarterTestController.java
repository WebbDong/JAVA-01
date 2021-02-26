package com.webbdong.autoconfigure.starter.test.controller;

import com.webbdong.autoconfigure.starter.bean.Klass;
import com.webbdong.autoconfigure.starter.bean.OperatingSystem;
import com.webbdong.autoconfigure.starter.bean.School;
import com.webbdong.autoconfigure.starter.bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Webb Dong
 * @date 2021-02-26 3:22 PM
 */
@RestController
public class SpringBootStarterTestController {

    @Autowired
    private Student student;

    @Autowired
    private School school;

    @Autowired
    private Klass klass;

    @Autowired
    private OperatingSystem operatingSystem;

    @GetMapping("/getStudent")
    public Student getStudent() {
        return student;
    }

    @GetMapping("/getSchool")
    public School getSchool() {
        return school;
    }

    @GetMapping("/getKlass")
    public Klass getKlass() {
        return klass;
    }

    @GetMapping("/getCurrentSystemName")
    public String getCurrentSystemName() {
        return operatingSystem.getCurrentSystemName();
    }

}

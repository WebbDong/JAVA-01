package lesson09.beanfactory.bean;

import lombok.Data;

/**
 * @author Webb Dong
 * @description: Student
 * @date 2021-02-15 23:00
 */
@Data
public class Student {

    private String name;

    private Integer age;

    public void study() {
        System.out.println(name + " 正在努力学习...");
    }

}

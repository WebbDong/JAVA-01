package lesson09.beanfactory.xmlbeanfactory.bean;

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
        System.out.println("invoke Target Method Student.study()");
//        int i = 1 / 0;
        System.out.println(name + " 正在努力学习...");
    }

    public Integer testMethod(int x, int y, String str) {
        System.out.println("invoke Target Method Student.testMethod(int, int, String)");
//        int i = 1 / 0;
        System.out.println(str);
        return x + y;
    }

}

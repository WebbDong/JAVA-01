package com.webbdong.autoconfigure.starter.bean;

import lombok.Data;

/**
 * @author Webb Dong
 * @date 2021-02-15 23:00
 */
@Data
public class Student {

    private String name;

    private Integer age;

    public void study() {
        System.out.println(name + " 正在努力学习...");
    }

    public Integer testMethod(int x, int y, String str) {
        return x + y;
    }

}

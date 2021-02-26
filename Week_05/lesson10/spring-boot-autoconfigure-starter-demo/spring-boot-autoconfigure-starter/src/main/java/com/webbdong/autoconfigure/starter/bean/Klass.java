package com.webbdong.autoconfigure.starter.bean;

import lombok.Data;

/**
 * @author Webb Dong
 * @date 2021-02-19 22:49
 */
@Data
public class Klass {

    private String name;

    public void testMethod() {
        System.out.println(name);
    }

}

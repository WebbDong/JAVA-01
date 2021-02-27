package com.webbdong.autoconfigure.starter.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Webb Dong
 * @date 2021-02-15 23:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class School {

    private String name;

    private Integer foundingYears;

    public void introduce() {
        System.out.println(name + "，始建于" + foundingYears + "年");
    }

    public Double testMethod(double x, double y) {
        return x + y;
    }

}

package com.webbdong.shardingsphere.dynamicdatasource.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Webb Dong
 * @date 2021-03-08 12:10 AM
 */
@Data
public class Student {

    private Long id;

    private Long age;

    private String name;

    private String nickname;

    private Date createDate;

}

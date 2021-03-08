package com.webbdong.shardingsphere.dynamicdatasource.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Webb Dong
 * @date 2021-03-08 12:04 AM
 */
@Data
public class User {

    private Long id;

    private String username;

    private String pwd;

    private Integer gender;

    private Date birthday;

    private Integer state;

    private Date createdTime;

    private Date updatedTime;

}

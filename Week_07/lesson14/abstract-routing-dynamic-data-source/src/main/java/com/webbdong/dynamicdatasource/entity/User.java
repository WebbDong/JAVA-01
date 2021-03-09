package com.webbdong.dynamicdatasource.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private LocalDate birthday;

    private Integer state;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

}

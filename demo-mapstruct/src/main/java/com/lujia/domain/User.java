package com.lujia.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author :lujia
 * @date :2018/12/20  9:49
 */
@Data
public class User {

    private String name;

    private int age;

    private String password;

    private Date date;
}

package com.hyh.springmvcdemo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;

/**
 * @author : huang.yaohua
 * @date : 2022/6/8 17:51
 */
@Data
public class User {
    private int age;

    @NotEmpty
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    @NumberFormat(style = NumberFormat.Style.DEFAULT, pattern = ".##")
    private double wage;

    @Email()
    private String email;


}

package com.socialive.demo.controller.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {

    private String email;

    private String name;

    private Date lastModified;

}

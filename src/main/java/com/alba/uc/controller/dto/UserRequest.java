package com.alba.uc.controller.dto;

import lombok.Data;

@Data
public class UserRequest {

    private String name;
    private String lastName;
    private String email;
    private String password;
}

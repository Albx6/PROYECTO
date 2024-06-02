package com.alba.uc.controller.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {

    private String name;
    private String lastName;
    private String password;

}

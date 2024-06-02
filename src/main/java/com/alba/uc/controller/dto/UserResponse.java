package com.alba.uc.controller.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {

    private long id;
    private String name;
    private String lastName;
    private String email;
    private LocalDateTime creationDate;
    private String photoUrl;
    private Boolean verifiedEmail;
    private Boolean disabled;
    private LocalDateTime accesDate;
}

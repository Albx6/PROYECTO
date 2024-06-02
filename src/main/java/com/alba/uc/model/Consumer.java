package com.alba.uc.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Consumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    private List<User> users;
    private String name;
    private String email;
    private String password;
    private String apiKey;
    private LocalDateTime creationDate;

}

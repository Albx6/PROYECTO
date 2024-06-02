package com.alba.uc.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Token token;

    @ManyToOne
    private Consumer consumer;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private LocalDateTime creationDate;
    private String photoUrl;
    private Boolean verifiedEmail;
    private Boolean disabled;
    private LocalDateTime accessDate;



}

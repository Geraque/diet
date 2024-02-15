package com.example.demo.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class User {
    private Long userId;
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    private String username;
}

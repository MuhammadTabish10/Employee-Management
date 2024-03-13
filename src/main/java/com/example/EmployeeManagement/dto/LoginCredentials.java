package com.example.EmployeeManagement.dto;


import lombok.*;

import javax.validation.constraints.Email;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginCredentials {
    @Email(message = "Please provide correct email.")
    private String email;
    private String password;
}
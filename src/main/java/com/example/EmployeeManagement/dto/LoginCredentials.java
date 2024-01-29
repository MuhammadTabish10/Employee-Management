package com.example.EmployeeManagement.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginCredentials {
    @Email(message = "Please provide correct email.")
    private String email;
    private String password;
}
package com.example.EmployeeManagement.dto;

import com.example.EmployeeManagement.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(message = "Name cannot be null")
    private String name;

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "Password cannot be null")
    private String password;

    private Boolean status;

    @NotNull(message = "Role cannot be null")
    private Set<Role> roles;
}

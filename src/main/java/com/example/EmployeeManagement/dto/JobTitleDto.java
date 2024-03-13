package com.example.EmployeeManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobTitleDto {
    private Long id;

    @NotBlank(message = "Address cannot be Null")
    private String title;

    private Boolean status;
}

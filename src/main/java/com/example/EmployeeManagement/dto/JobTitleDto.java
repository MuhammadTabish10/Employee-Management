package com.example.EmployeeManagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

package com.example.EmployeeManagement.util;

import com.example.EmployeeManagement.dto.CustomUserDetail;
import com.example.EmployeeManagement.dto.EmployeeDto;
import com.example.EmployeeManagement.exception.RecordNotFoundException;
import com.example.EmployeeManagement.model.User;
import com.example.EmployeeManagement.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Helper {
    private final UserRepository userRepository;

    public Helper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void validateEmployeeDto(EmployeeDto employeeDto) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = employeeDto.getDateOfBirth();
        LocalDate hireDate = employeeDto.getHireDate();

        // Check if the hire date is not before the birth date
        if (hireDate.isBefore(birthDate)) {
            throw new IllegalArgumentException("Hire date cannot be before the birth date");
        }

        // Check if there is a difference of at least 18 years between hire date and birth date
        if (birthDate.plusYears(18).isAfter(hireDate)) {
            throw new IllegalArgumentException("Employee should be 18 years old");
        }

        // Check if the hire date is not more than 2 months in the future
        if (hireDate.isAfter(currentDate.plusMonths(2))) {
            throw new IllegalArgumentException("Hire date cannot be more than 2 months in the future");
        }
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetail) {
            String email = ((CustomUserDetail) principal).getEmail();
            return userRepository.findByEmailAndStatusIsTrue(email)
                    .orElseThrow(() -> new RecordNotFoundException("User not found"));
        } else {
            throw new RecordNotFoundException("User not Found");
        }
    }

}

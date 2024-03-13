package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto registerUser(UserDto userDto);
    List<UserDto> getAllUser(Boolean status);
    UserDto getUserById(Long id);
    UserDto update(Long id, UserDto userDto);
    void delete(Long id);
    void setToActive(Long id);
}

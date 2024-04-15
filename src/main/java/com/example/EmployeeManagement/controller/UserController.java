package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.UserDto;
import com.example.EmployeeManagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/status/{status}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<List<UserDto>> getAllUsers(@PathVariable Boolean status){
        List<UserDto> users = userService.getAllUser(status);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/current-user")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<UserDto> getCurrentUser(){
        UserDto user = userService.getCurrentUser();
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<UserDto> updateUserById(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        UserDto user = userService.update(id, userDto);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/{id}/status")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<Void> setUserStatusToActiveById(@PathVariable Long id) {
        userService.setToActive(id);
        return ResponseEntity.ok().build();
    }
}

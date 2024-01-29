package com.example.EmployeeManagement.exception;

public class UserAlreadyExistAuthenticationException extends RuntimeException{
    public UserAlreadyExistAuthenticationException(String message)
    {
        super(message);
    }
}

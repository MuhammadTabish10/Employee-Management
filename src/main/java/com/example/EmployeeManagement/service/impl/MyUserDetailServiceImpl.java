package com.example.EmployeeManagement.service.impl;

import com.example.EmployeeManagement.dto.CustomUserDetail;
import com.example.EmployeeManagement.model.User;
import com.example.EmployeeManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public CustomUserDetail loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndStatusIsTrue(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found with email: " + email));
        return new CustomUserDetail(user);
    }
}

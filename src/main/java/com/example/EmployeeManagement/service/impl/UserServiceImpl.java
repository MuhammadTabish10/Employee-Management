package com.example.EmployeeManagement.service.impl;

import com.example.EmployeeManagement.dto.UserDto;
import com.example.EmployeeManagement.exception.RecordNotFoundException;
import com.example.EmployeeManagement.exception.UserAlreadyExistAuthenticationException;
import com.example.EmployeeManagement.model.Role;
import com.example.EmployeeManagement.model.User;
import com.example.EmployeeManagement.repository.RoleRepository;
import com.example.EmployeeManagement.repository.UserRepository;
import com.example.EmployeeManagement.service.UserService;
import javax.transaction.Transactional;

import com.example.EmployeeManagement.util.EmailUtils;
import com.example.EmployeeManagement.util.Helper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailUtils emailUtils;
    private final Helper helper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, EmailUtils emailUtils, BCryptPasswordEncoder bCryptPasswordEncoder, Helper helper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.emailUtils = emailUtils;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.helper = helper;
    }

    @Override
    @Transactional
    public UserDto registerUser(UserDto userDto) {
        User user = toEntity(userDto);
        String password = user.getPassword();

        Optional<User> existingUser = userRepository.findByEmailAndStatusIsTrue(user.getEmail());
        if(existingUser.isPresent()){
            throw new UserAlreadyExistAuthenticationException("User Already Exist");
        }

        user.setStatus(true);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roleList = new HashSet<>();
        for(Role role: user.getRoles()){
            roleRepository.findById(role.getId())
                    .orElseThrow(()-> new RecordNotFoundException("Role not found"));
            roleList.add(role);
        }
        user.setRoles(roleList);
        userRepository.save(user);
        emailUtils.sendWelcomeEmail(user, password);
        return toDto(user);
    }

    @Override
    public List<UserDto> getAllUser(Boolean status) {
        List<User> userList = userRepository.findAllByStatusOrderByIdDesc(status);
        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : userList) {
            UserDto userDto = toDto(user);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User Not found at id => %d", id)));
        return toDto(user);
    }

    @Override
    @Transactional
    public UserDto update(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User Not found at id => %d", id)));

        existingUser.setName(userDto.getName());
        return toDto(userRepository.save(existingUser));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User Not found at id => %d", id)));
        userRepository.setStatusWhereId(user.getId(), false);
    }

    @Override
    @Transactional
    public void setToActive(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User Not found at id => %d", id)));
        userRepository.setStatusWhereId(user.getId(), true);
    }

    @Override
    public UserDto getCurrentUser() {
        User user = helper.getCurrentUser();
        return toDto(user);
    }

    public UserDto toDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .status(user.getStatus())
                .roles(user.getRoles())
                .build();
    }

    public User toEntity(UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .password(userDto.getPassword())
                .status(userDto.getStatus())
                .roles(userDto.getRoles())
                .build();
    }
}

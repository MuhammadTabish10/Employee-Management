package com.example.EmployeeManagement.service.impl;

import com.example.EmployeeManagement.dto.UserDto;
import com.example.EmployeeManagement.exception.RecordNotFoundException;
import com.example.EmployeeManagement.exception.UserAlreadyExistAuthenticationException;
import com.example.EmployeeManagement.model.Role;
import com.example.EmployeeManagement.model.User;
import com.example.EmployeeManagement.repository.RoleRepository;
import com.example.EmployeeManagement.repository.UserRepository;
import com.example.EmployeeManagement.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public UserDto registerUser(UserDto userDto) {
        User user = toEntity(userDto);

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
        return toDto(user);

    }

    @Override
    public List<UserDto> getAllUser(Boolean status) {
        List<User> userList = userRepository.findAllByStatus(status);
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
    public String delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User Not found at id => %d", id)));
        userRepository.setStatusToFalseWhereId(user.getId());
        return String.format("User Deleted at id => %d", id);
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
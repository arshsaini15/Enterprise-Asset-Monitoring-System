package com.eams.user.service;

import com.eams.common.config.PasswordConfig;
import com.eams.user.dto.RegisterRequestDTO;
import com.eams.user.enums.Role;
import com.eams.user.mapper.UserMapper;
import com.eams.user.model.User;
import com.eams.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordConfig passwordConfig;


    UserService(UserRepository userRepository, UserMapper userMapper, PasswordConfig passwordConfig) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordConfig = passwordConfig;
    }

    public User register(RegisterRequestDTO dto) {

        String email = dto.getEmail().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        User user = userMapper.toEntity(dto);

        if(email.endsWith("@eams.com")) {
            user.setRole(Role.MANAGER);
        } else {
            user.setRole(Role.OPERATOR);
        }

        user.setEmail(email);
        user.setPassword(passwordConfig.encodePassword().encode(dto.getPassword()));

        return userRepository.save(user);

    }
}

package com.eams.user.service;

import com.eams.common.exception.DuplicateEmailException;
import com.eams.common.exception.InvalidCredentialsException;
import com.eams.user.dto.LoginRequestDTO;
import com.eams.user.dto.LoginResponseDTO;
import com.eams.user.dto.RegisterRequestDTO;
import com.eams.user.enums.Role;
import com.eams.user.mapper.UserMapper;
import com.eams.user.model.User;
import com.eams.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequestDTO dto) {
        String email = dto.getEmail().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("Email already exists");
        }

        User user = userMapper.toEntity(dto);

        if(email.endsWith("@eams.com")) {
            user.setRole(Role.MANAGER);
        } else {
            user.setRole(Role.OPERATOR);
        }

        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        String email = dto.getEmail().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        return new LoginResponseDTO(
                "Login successful",
                user.getEmail(),
                user.getRole()
        );
    }
}

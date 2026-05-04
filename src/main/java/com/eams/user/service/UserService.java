package com.eams.user.service;

import com.eams.common.exception.DuplicateEmailException;
import com.eams.common.exception.InvalidCredentialsException;
import com.eams.common.util.JwtUtil;
import com.eams.user.dto.LoginRequestDTO;
import com.eams.user.dto.LoginResponseDTO;
import com.eams.user.dto.RegisterRequestDTO;
import com.eams.user.enums.Role;
import com.eams.user.mapper.UserMapper;
import com.eams.user.model.User;
import com.eams.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponseDTO register(RegisterRequestDTO dto) {

        String email = dto.getEmail().toLowerCase();

        log.info("Attempting registration for email={}", email);

        if (userRepository.existsByEmail(email)) {
            log.warn("Duplicate email detected for email={}", email);
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

        log.info("User registered successfully for email={}", email);
        String token = jwtUtil.generateToken(email);

        return new LoginResponseDTO(token);
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {

        String email = dto.getEmail().toLowerCase();

        log.info("Login attempt for email={}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Login failed - user not found for email={}", email);
                    return new InvalidCredentialsException("Invalid credentials");
                });

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            log.warn("Login failed - invalid password for email={}", email);
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        log.info("Login successful for email={}", email);

        return new LoginResponseDTO(token);
    }
}

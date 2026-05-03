package com.eams.user.controller;

import com.eams.user.dto.LoginRequestDTO;
import com.eams.user.dto.LoginResponseDTO;
import com.eams.user.dto.RegisterRequestDTO;
import com.eams.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/auth")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDTO dto) {

        log.info("Register request received for email={}", dto.getEmail());

        userService.register(dto);

        log.info("User registered successfully for email={}", dto.getEmail());

        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {

        log.info("Login request received for email={}", dto.getEmail());

        LoginResponseDTO response = userService.login(dto);

        log.info("Login successful for email={}", dto.getEmail());

        return ResponseEntity.ok(response);
    }

}

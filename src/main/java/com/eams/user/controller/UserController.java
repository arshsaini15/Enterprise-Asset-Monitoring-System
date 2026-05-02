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

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDTO dto) {
        userService.register(dto);
        return ResponseEntity.ok().body("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        LoginResponseDTO response = userService.login(dto);
        return ResponseEntity.ok(response);
    }

}

package com.example.controller;

import com.example.DTO.AuthDTO;
import com.example.DTO.AuthResponseDTO;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}
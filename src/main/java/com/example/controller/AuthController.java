package com.example.controller;

import com.example.DTO.AuthDTO;
import com.example.DTO.AuthResponseDTO;
import com.example.DTO.ProfileDto;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
    @GetMapping("/regis")
    public ResponseEntity<ProfileDto> registration(@RequestBody ProfileDto dto){
        return ResponseEntity.ok(authService.registration(dto));
    }

}

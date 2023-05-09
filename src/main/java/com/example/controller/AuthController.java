package com.example.controller;

import com.example.DTO.registration.RegistrationDTO;
import com.example.DTO.registration.RegistrationResponseDTO;
import com.example.DTO.auth.AuthDTO;
import com.example.DTO.auth.AuthResponseDTO;
import com.example.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
    @GetMapping("/public/regis")
    public ResponseEntity<RegistrationResponseDTO> registration(@RequestBody @Valid RegistrationDTO dto){
        return ResponseEntity.ok(authService.registration(dto));
    }
    @GetMapping("/email/verification/{jwt}")
    public ResponseEntity<RegistrationResponseDTO> emailVerification(@PathVariable("jwt") String jwt) {
        return ResponseEntity.ok(authService.emailVerification(jwt));
    }
}

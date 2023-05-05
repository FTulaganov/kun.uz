package com.example.DTO.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO {
    @Email(message = "email required")
    private String email;
    @NotBlank(message = "password required")
    private String password;
}

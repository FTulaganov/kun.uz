package com.example.DTO.auth;

import com.example.enums.ProfileRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDTO {
    @NotBlank(message = "name required")
    private String name;
    @NotBlank(message = "surname required")
    private String surname;
    @NotNull(message = "jwt required")
    @Size(max = 250, message = "jwt must be between 10 and 250 characters")
    private String jwt;
    @NotBlank(message = "role required")
    private ProfileRole role;
}

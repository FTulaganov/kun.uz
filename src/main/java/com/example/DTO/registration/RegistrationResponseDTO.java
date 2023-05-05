package com.example.DTO.registration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationResponseDTO {
    @NotBlank(message = "message required")
    @Size(max = 225, message = "Original name must be between 10 and 225 characters")
    private String message;

    public RegistrationResponseDTO(String message) {
        this.message = message;
    }
}

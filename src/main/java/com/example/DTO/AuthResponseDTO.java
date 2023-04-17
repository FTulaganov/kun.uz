package com.example.DTO;

import com.example.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDTO {
    private String name;
    private String surname;
    private String jwt;
    private ProfileRole role;
}

package com.example.DTO.profile;

import com.example.enums.ProfileRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto {
    private Integer id;
    @NotBlank(message = "name required")
    private String name;
    @NotBlank(message = "surname required")
    private String surname;
    @Email(message = "email required")
    private String email;
    @NotBlank(message = "phone required")
    private String phone;
    @NotBlank(message = "password required")
    private String password;
    private ProfileRole role;
}

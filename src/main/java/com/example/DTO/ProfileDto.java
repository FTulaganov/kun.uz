package com.example.DTO;

import com.example.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private ProfileRole role;
}

package com.example.DTO;

import com.example.enums.ProfileRole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtDTO {
    @NotBlank(message = "id required")
    private Integer id;
    @NotBlank(message = "role required")
    private ProfileRole role;


}

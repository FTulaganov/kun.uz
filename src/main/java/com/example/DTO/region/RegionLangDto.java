package com.example.DTO.region;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionLangDto {
    @NotNull(message = "id required")
    private Integer id;
    @NotBlank(message = "name required")
    private String name;
}

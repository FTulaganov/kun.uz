package com.example.DTO.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryLangDto {
    @NotNull(message = "id required")
    private Integer id;
    @NotBlank(message = "name required")
    private String name;
}

package com.example.DTO.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private Integer id;
    @NotNull(message = "nameUz required")
    @Size(max = 225, message = "Title must be between 10 and 225 characters")
    private String nameUz;
    @NotNull(message = "nameRu required")
    @Size(max = 225, message = "Title must be between 10 and 225 characters")private
    String nameRu;
    @NotNull(message = "nameEn required")
    @Size(max = 225, message = "Title must be between 10 and 225 characters")
    private String nameEn;
}

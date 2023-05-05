package com.example.DTO.articleType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleTypeFullDto {
    @NotNull(message = "id required")
    private Integer id;
    @NotNull(message = "nameUz required")
    @Size(max = 225, message = "Title must be between 10 and 225 characters")
    private String nameUz;
    @NotNull(message = "nameRu required")
    @Size(max = 225, message = "Title must be between 10 and 225 characters")
    private String nameRu;
    @NotNull(message = "nameEn required")
    @Size(max = 225, message = "Title must be between 10 and 225 characters")
    private String nameEn;
    @NotNull(message = "CreatedDate required")
    private LocalDateTime created_date;
    @NotNull(message = "visible required")
    private Boolean visible;
}

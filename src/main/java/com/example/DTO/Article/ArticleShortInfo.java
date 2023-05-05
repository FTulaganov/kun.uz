package com.example.DTO.Article;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleShortInfo {
    @NotNull(message = "id required")
    private String id;
    @NotNull(message = "title required")
    @Size(max = 225, message = "Title must be between 10 and 225 characters")
    private String title;
    @NotBlank(message = "Field must have some value")
    private String description;
    @NotNull(message = " Attach required")
    private Integer attachId;
    @NotNull(message = "publishedDate required")
    private LocalDateTime publishedDate;
}

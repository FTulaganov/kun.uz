package com.example.DTO.Article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleRequestDTO {
    @NotNull(message = "title required")
    @Size(max = 225, message = "Title must be between 10 and 225 characters")
    private String title;
    @NotBlank(message = "Field must have some value")
    private String description;
    @NotEmpty(message = "Content qani")
    private String content;
    @NotNull(message = " Attach required")
    private String attachId;
    @NotNull(message = " Region required")
    private Integer regionId;
    @NotNull(message = " Category required")
    private Integer categoryId;
    @NotNull(message = " articleType required")
    private Integer articleTypeId;
//    @NotEmpty(message = "Should provide value")
//    private List<Integer> typeList;
}

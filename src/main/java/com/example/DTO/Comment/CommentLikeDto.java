package com.example.DTO.Comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentLikeDto {
    @NotNull(message = "article can't be null")
    private String articleId;
}

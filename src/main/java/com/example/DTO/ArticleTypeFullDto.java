package com.example.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleTypeFullDto {
    private Integer id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private LocalDateTime created_date;
    private Boolean visible;
}

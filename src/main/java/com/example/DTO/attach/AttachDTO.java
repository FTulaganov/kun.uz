package com.example.DTO.attach;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AttachDTO {
    @NotNull(message = "id required")
    private String id;
    @NotNull(message = "Original name required")
    @Size(max = 225, message = "Original name must be between 10 and 225 characters")
    private String originalName;
    @NotNull(message = "Path required")
    @Size(max = 225, message = "Path must be between 10 and 225 characters")
    private String path;
    @NotNull(message = "Size required")
    private Long size;
    @NotNull(message = "Extension required")
    @Size(max = 225, message = "Extension must be between 10 and 225 characters")
    private String extension;
    @NotNull(message = "CreatedDate required")
    private LocalDateTime createdDate;
    private String url;
}


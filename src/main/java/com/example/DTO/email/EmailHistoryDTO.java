package com.example.DTO.email;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmailHistoryDTO {
    private Integer id;
    private String message;
    private String email;
    private LocalDateTime created_date;

}

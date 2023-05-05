package com.example.DTO.email;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmailDate {
    @NotNull(message = "start date null")
    private LocalDateTime startDate;
    @NotNull(message = "start date null")
    private LocalDateTime endDate;
}



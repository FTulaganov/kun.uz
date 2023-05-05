package com.example.controller;

import com.example.exps.AppBadRequestExeption;
import com.example.exps.ItemNotFoundExeption;
import com.example.exps.MethodNotAllowedExeption;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceController {
    @ExceptionHandler({AppBadRequestExeption.class,
            ItemNotFoundExeption.class,
            MethodNotAllowedExeption.class})
    public ResponseEntity<String> handleException(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

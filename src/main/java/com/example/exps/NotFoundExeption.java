package com.example.exps;

public class NotFoundExeption extends RuntimeException{
    public NotFoundExeption(String message) {
        super(message);
    }
}

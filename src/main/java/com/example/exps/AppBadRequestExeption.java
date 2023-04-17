package com.example.exps;

public class AppBadRequestExeption extends RuntimeException {
    public AppBadRequestExeption(String message) {
        super(message);
    }
}

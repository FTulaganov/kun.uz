package com.example.exps;

public class ItemNotFoundExeption extends RuntimeException{
    public ItemNotFoundExeption(String message) {
        super(message);
    }
}

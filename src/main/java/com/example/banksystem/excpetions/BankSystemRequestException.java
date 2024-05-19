package com.example.banksystem.excpetions;

public class BankSystemRequestException extends RuntimeException {
    public BankSystemRequestException(String message){
        super(message);
    }

    public BankSystemRequestException(String message, Throwable throwable){
        super(message, throwable);
    }
}

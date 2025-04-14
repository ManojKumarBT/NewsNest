package com.cts.newsnest.user.exception;

public class InvalidInputException extends RuntimeException{
    public  InvalidInputException(String message){
        super(message);
    }
}
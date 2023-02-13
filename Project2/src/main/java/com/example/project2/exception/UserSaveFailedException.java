package com.example.project2.exception;

public class UserSaveFailedException extends RuntimeException {
    public UserSaveFailedException(String message){
        super(String.format(message));
    }
}

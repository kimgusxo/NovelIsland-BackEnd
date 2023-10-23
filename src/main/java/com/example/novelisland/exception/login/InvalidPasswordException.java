package com.example.novelisland.exception.login;

import com.example.novelisland.description.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidPasswordException extends RuntimeException{
    private final ErrorCode errorCode;
}

package com.example.novelisland.exception.user;

import com.example.novelisland.description.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotExistUserException extends NullPointerException {
    private final ErrorCode errorCode;
}
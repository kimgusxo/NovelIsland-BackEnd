package com.example.novelisland.exception.auth;

import com.example.novelisland.description.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TimeOutJwtException extends RuntimeException {
    private ErrorCode errorCode;
}

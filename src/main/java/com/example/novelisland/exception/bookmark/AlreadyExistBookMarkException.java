package com.example.novelisland.exception.bookmark;

import com.example.novelisland.description.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AlreadyExistBookMarkException extends RuntimeException {
    private final ErrorCode errorCode;
}

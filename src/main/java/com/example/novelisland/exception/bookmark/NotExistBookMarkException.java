package com.example.novelisland.exception.bookmark;

import com.example.novelisland.description.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotExistBookMarkException extends NullPointerException {
    private final ErrorCode errorCode;
}

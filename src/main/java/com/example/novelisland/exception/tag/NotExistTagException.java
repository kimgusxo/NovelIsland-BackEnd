package com.example.novelisland.exception.tag;

import com.example.novelisland.description.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotExistTagException extends NullPointerException {
    private final ErrorCode errorCode;
}

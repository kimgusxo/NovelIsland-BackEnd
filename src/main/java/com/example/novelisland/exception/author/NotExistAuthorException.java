package com.example.novelisland.exception.author;

import com.example.novelisland.description.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotExistAuthorException extends NullPointerException {
    private final ErrorCode errorCode;
}

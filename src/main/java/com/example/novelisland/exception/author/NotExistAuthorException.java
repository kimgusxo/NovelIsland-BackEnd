package com.example.novelisland.exception.author;

import com.example.novelisland.description.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public class NotExistAuthorException extends NullPointerException {
    private final ErrorCode errorCode;
}

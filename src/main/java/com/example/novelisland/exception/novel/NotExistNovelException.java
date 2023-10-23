package com.example.novelisland.exception.novel;

import com.example.novelisland.description.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotExistNovelException extends NullPointerException {
    private final ErrorCode errorCode;

}

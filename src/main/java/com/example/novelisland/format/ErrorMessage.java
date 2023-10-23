package com.example.novelisland.format;

import com.example.novelisland.description.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorMessage {
    private int statusCode;
    private String message;

    public static ErrorMessage of(ErrorCode errorCode){
        return ErrorMessage.builder()
                .message(errorCode.getDetail())
                .statusCode(errorCode.getHttpStatus().value())
                .build();
    }
}

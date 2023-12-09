package com.example.novelisland.format;

import com.example.novelisland.description.ErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorMessage {
    @ApiModelProperty(example = "상태코드")
    private int statusCode;
    @ApiModelProperty(example = "메세지")
    private String message;

    public static ErrorMessage of(ErrorCode errorCode){
        return ErrorMessage.builder()
                .message(errorCode.getDetail())
                .statusCode(errorCode.getHttpStatus().value())
                .build();
    }
}

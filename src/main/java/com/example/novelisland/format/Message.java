package com.example.novelisland.format;

import com.example.novelisland.description.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message<T> {

    @ApiModelProperty(example = "성공여부")
    private final boolean success;
    @ApiModelProperty(example = "상태코드")
    private final int statusCode;
    @ApiModelProperty(example = "메세지")
    private final String message;
    @ApiModelProperty(example = "응답데이터")
    private final T data;

    public static <T> Message<T> success(T data) {
        return Message.<T>builder()
                .success(true)
                .statusCode(HttpStatus.OK.value())
                .message("요청이 성공했습니다.")
                .data(data)
                .build();
    }

    public static <T> Message<T> success(String message, T data) {
        return Message.<T>builder()
                .success(true)
                .statusCode(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .build();
    }

    public static Message<Void> success(String message) {
        return Message.<Void>builder()
                .success(true)
                .statusCode(HttpStatus.OK.value())
                .message(message)
                .build();
    }

    public static Message<Void> failure(ErrorCode errorCode) {
        return Message.<Void>builder()
                .success(false)
                .statusCode(errorCode.getHttpStatus().value())
                .message(errorCode.getDetail())
                .build();
    }
}

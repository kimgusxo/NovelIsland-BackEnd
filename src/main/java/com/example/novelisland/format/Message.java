package com.example.novelisland.format;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Message {

    @ApiModelProperty(example = "상태코드")
    private int statusCode;
    @ApiModelProperty(example = "메세지")
    private String message;
    @ApiModelProperty(example = "응답데이터")
    private Object data;

    public static Message of(String message, int statusCode, Object data){
        return Message.builder()
                .message(message)
                .statusCode(statusCode)
                .data(data)
                .build();
    }

    public static Message of(String message, int statusCode){
        return Message.builder()
                .message(message)
                .statusCode(statusCode)
                .build();
    }
}

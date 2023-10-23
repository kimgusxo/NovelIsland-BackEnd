package com.example.novelisland.description;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 100번대: 클라이언트의 요청까지는 처리된 임시 응답, 정보 제공

    // 200번대: 성공

    // 300번대: 완전한 처리를 위해선 추가 동작이 필요한 경우, 리다이렉션

    // 400번대: 잘못된 요청이 들어온 경우, 클라이언트 에러
    DUPLICATE_ID_TOKEN(HttpStatus.BAD_REQUEST, "이미 아이디가 존재합니다."),
    INVALID_ID_TOKEN(HttpStatus.BAD_REQUEST, "아이디가 잘못되었습니다."),
    INVALID_PASSWORD_TOKEN(HttpStatus.BAD_REQUEST, "비밀번호가 잘못되었습니다."),
    NOT_EXIST_USER_TOKEN(HttpStatus.BAD_REQUEST, "유저가 존재하지 않습니다."),
    NOT_EXIST_NOVEL_TOKEN(HttpStatus.BAD_REQUEST, "소설이 존재하지 않습니다."),
    NOT_EXIST_AUTHOR_TOKEN(HttpStatus.BAD_REQUEST, "검색하신 작가가 존재하지 않습니다."),
    NOT_EXIST_BOOKMARK_TOKEN(HttpStatus.BAD_REQUEST, "북마크가 존재하지 않습니다."),
    AlREADY_EXIST_BOOKMARK_TOKEN(HttpStatus.BAD_REQUEST, "이미 북마크가 존재합니다."),
    NOT_VALIDATION_TOKEN(HttpStatus.BAD_REQUEST, "값이 없으면 안됩니다.");
    
    // 500번대: 서버 측 부하 및 DB 관련 문제, 서버 에러
    private final HttpStatus httpStatus;
    private final String detail;

}

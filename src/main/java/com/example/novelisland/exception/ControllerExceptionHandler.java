package com.example.novelisland.exception;

import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.exception.author.NotExistAuthorException;
import com.example.novelisland.exception.bookmark.AlreadyExistBookMarkException;
import com.example.novelisland.exception.bookmark.NotExistBookMarkException;
import com.example.novelisland.exception.login.ConcurrentlySignUpException;
import com.example.novelisland.exception.login.DuplicateIdException;
import com.example.novelisland.exception.login.InvalidIdException;
import com.example.novelisland.exception.login.InvalidPasswordException;
import com.example.novelisland.exception.novel.NotExistNovelException;
import com.example.novelisland.exception.tag.NotExistTagException;
import com.example.novelisland.exception.user.NotExistUserException;
import com.example.novelisland.format.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    private ResponseEntity<Message<?>> buildErrorResponse(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(Message.failure(errorCode));
    }

    @ExceptionHandler(ConcurrentlySignUpException.class)
    public ResponseEntity<Message<?>> handleConcurrentlySignUpException(ConcurrentlySignUpException ex) {
        log.warn("[ConcurrentlySignUpException] 동시 회원가입 예외 발생", ex);
        return buildErrorResponse(ErrorCode.CONCURRENTLY_SIGNUP_TOKEN);
    }

    @ExceptionHandler(DuplicateIdException.class)
    public ResponseEntity<Message<?>> handleDuplicatedIdException(DuplicateIdException ex) {
        log.warn("[DuplicateIdException] 아이디 중복 예외 발생", ex);
        return buildErrorResponse(ErrorCode.DUPLICATE_ID_TOKEN);
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<Message<?>> handleInvalidIdException(InvalidIdException ex) {
        log.warn("[InvalidIdException] 잘못된 아이디 예외 발생", ex);
        return buildErrorResponse(ErrorCode.INVALID_ID_TOKEN);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Message<?>> handleInvalidPasswordException(InvalidPasswordException ex) {
        log.warn("[InvalidPasswordException] 잘못된 비밀번호 예외 발생", ex);
        return buildErrorResponse(ErrorCode.INVALID_PASSWORD_TOKEN);
    }

    @ExceptionHandler(NotExistUserException.class)
    public ResponseEntity<Message<?>> handleNotExistUserException(NotExistUserException ex) {
        log.warn("[NotExistUserException] 유저 미존재 예외 발생", ex);
        return buildErrorResponse(ErrorCode.NOT_EXIST_USER_TOKEN);
    }

    @ExceptionHandler(NotExistNovelException.class)
    public ResponseEntity<Message<?>> handleNotExistNovelException(NotExistNovelException ex) {
        log.warn("[NotExistNovelException] 소설 미존재 예외 발생", ex);
        return buildErrorResponse(ErrorCode.NOT_EXIST_NOVEL_TOKEN);
    }

    @ExceptionHandler(NotExistAuthorException.class)
    public ResponseEntity<Message<?>> handleNotExistAuthorException(NotExistAuthorException ex) {
        log.warn("[NotExistAuthorException] 작가 미존재 예외 발생", ex);
        return buildErrorResponse(ErrorCode.NOT_EXIST_AUTHOR_TOKEN);
    }

    @ExceptionHandler(NotExistBookMarkException.class)
    public ResponseEntity<Message<?>> handleNotExistBookMarkException(NotExistBookMarkException ex) {
        log.warn("[NotExistBookMarkException] 북마크 미존재 예외 발생", ex);
        return buildErrorResponse(ErrorCode.NOT_EXIST_BOOKMARK_TOKEN);
    }

    @ExceptionHandler(NotExistTagException.class)
    public ResponseEntity<Message<?>> handleNotExistTagException(NotExistTagException ex) {
        log.warn("[NotExistTagException] 태그 미존재 예외 발생", ex);
        return buildErrorResponse(ErrorCode.NOT_EXIST_TAG_TOKEN);
    }

    @ExceptionHandler(AlreadyExistBookMarkException.class)
    public ResponseEntity<Message<?>> handleAlreadyExistBookMarkException(AlreadyExistBookMarkException ex) {
        log.warn("[AlreadyExistBookMarkException] 이미 존재하는 북마크 예외 발생", ex);
        return buildErrorResponse(ErrorCode.AlREADY_EXIST_BOOKMARK_TOKEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Message<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("[MethodArgumentNotValidException] 요청 값 유효성 검증 실패", ex);
        return buildErrorResponse(ErrorCode.NOT_VALIDATION_TOKEN);
    }

}

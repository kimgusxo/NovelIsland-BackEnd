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
import com.example.novelisland.format.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ConcurrentlySignUpException.class)
    public ResponseEntity<ErrorMessage> handleConcurrentlySignUpException() {
        return new ResponseEntity<>(ErrorMessage.of(ErrorCode.CONCURRENTLY_SIGNUP_TOKEN), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateIdException.class)
    public ResponseEntity<ErrorMessage> handleDuplicatedIdException() {
        return new ResponseEntity<>(ErrorMessage.of(ErrorCode.DUPLICATE_ID_TOKEN), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ErrorMessage> handleInvalidIdException() {
        return new ResponseEntity<>(ErrorMessage.of(ErrorCode.INVALID_ID_TOKEN), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorMessage> handleInvalidPasswordException() {
        return new ResponseEntity<>(ErrorMessage.of(ErrorCode.INVALID_PASSWORD_TOKEN), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistUserException.class)
    public ResponseEntity<ErrorMessage> handleNotExistUserException() {
        return new ResponseEntity<>(ErrorMessage.of(ErrorCode.NOT_EXIST_USER_TOKEN), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistNovelException.class)
    public ResponseEntity<ErrorMessage> handleNotExistNovelException() {
        return new ResponseEntity<>(ErrorMessage.of(ErrorCode.NOT_EXIST_NOVEL_TOKEN), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistAuthorException.class)
    public ResponseEntity<ErrorMessage> handleNotExistAuthorException() {
        return new ResponseEntity<>(ErrorMessage.of(ErrorCode.NOT_EXIST_AUTHOR_TOKEN), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistBookMarkException.class)
    public ResponseEntity<ErrorMessage> handleNotExistBookMarkException() {
        return new ResponseEntity<>(ErrorMessage.of(ErrorCode.NOT_EXIST_BOOKMARK_TOKEN), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistTagException.class)
    public ResponseEntity<ErrorMessage> handleNotExistTagException() {
        return new ResponseEntity<>(ErrorMessage.of(ErrorCode.NOT_EXIST_TAG_TOKEN), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistBookMarkException.class)
    public ResponseEntity<ErrorMessage> handleAlreadyExistBookMarkException() {
        return new ResponseEntity<>(ErrorMessage.of(ErrorCode.AlREADY_EXIST_BOOKMARK_TOKEN), HttpStatus.BAD_REQUEST);
    }

    // 유효성 실패했을때의 예외처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException() {
        return new ResponseEntity<>(ErrorMessage.of(ErrorCode.NOT_VALIDATION_TOKEN), HttpStatus.BAD_REQUEST);
    }
}

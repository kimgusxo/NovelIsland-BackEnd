package com.example.novelisland.exception;

import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.exception.author.NotExistAuthorException;
import com.example.novelisland.exception.bookmark.NotExistBookMarkException;
import com.example.novelisland.exception.login.DuplicateIdException;
import com.example.novelisland.exception.login.InvalidIdException;
import com.example.novelisland.exception.login.InvalidPasswordException;
import com.example.novelisland.exception.novel.NotExistNovelException;
import com.example.novelisland.exception.user.NotExistUserException;
import com.example.novelisland.format.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DuplicateIdException.class)
    public ResponseEntity<ErrorMessage> handleDuplicatedIdException(DuplicateIdException de) {
        return new ResponseEntity<>(ErrorMessage.of(ErrorCode.DUPLICATE_ID_TOKEN), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ErrorMessage> handleInvalidIdException(InvalidIdException ie) {
        return new ResponseEntity<>(ErrorMessage.of(ErrorCode.INVALID_ID_TOKEN), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorMessage> handleInvalidPasswordException(InvalidPasswordException ie) {
        return new ResponseEntity<>(ErrorMessage.of(ErrorCode.INVALID_PASSWORD_TOKEN), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistUserException.class)
    public ResponseEntity<ErrorMessage> handleNotExistUserException(NotExistUserException ne) {
        return new ResponseEntity<>(ErrorMessage.of(ErrorCode.NOT_EXIST_USER_TOKEN), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistNovelException.class)
    public ResponseEntity<ErrorMessage> handleNotExistNovelException(NotExistNovelException ne) {
        return new ResponseEntity<>(ErrorMessage.of(ErrorCode.NOT_EXIST_NOVEL_TOKEN), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistAuthorException.class)
    public ResponseEntity<ErrorMessage> handleNotExistAuthorException(NotExistAuthorException ne) {
        return new ResponseEntity<>(ErrorMessage.of(ErrorCode.NOT_EXIST_AUTHOR_TOKEN), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistBookMarkException.class)
    public ResponseEntity<ErrorMessage> handleNotExistBookMarkException() {
        return new ResponseEntity<>(ErrorMessage.of(ErrorCode.NOT_EXIST_BOOKMARK_TOKEN), HttpStatus.BAD_REQUEST);
    }

    // 유효성 실패했을때의 예외처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException() {
        return new ResponseEntity<>(ErrorMessage.of(ErrorCode.NOT_VALIDATION_TOKEN), HttpStatus.BAD_REQUEST);
    }
}

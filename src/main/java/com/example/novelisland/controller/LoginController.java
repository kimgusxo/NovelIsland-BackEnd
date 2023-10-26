package com.example.novelisland.controller;

import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.dto.LoginDTO;
import com.example.novelisland.exception.login.DuplicateIdException;
import com.example.novelisland.exception.login.InvalidIdException;
import com.example.novelisland.exception.login.InvalidPasswordException;
import com.example.novelisland.format.ErrorMessage;
import com.example.novelisland.format.Message;
import com.example.novelisland.service.LoginService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/signUp")
    @ApiOperation(value = "회원가입", notes = "유저가 회원가입을 한다.")
    public ResponseEntity<Message> signUp(@RequestBody @Valid LoginDTO loginDTO) {
        log.info("signUp: {}", loginDTO);
        return new ResponseEntity<>(Message.of("회원가입 성공", HttpStatus.OK.value(), loginService.signUp(loginDTO)), HttpStatus.OK);
    }

    @PostMapping("/signIn")
    @ApiOperation(value = "로그인", notes = "유저가 로그인을 한다.")
    public ResponseEntity<Message> signIn(@RequestBody @Valid LoginDTO loginDTO) {
        log.info("signIn: {}", loginDTO);
        return new ResponseEntity<>(Message.of("로그인 성공", HttpStatus.OK.value(), loginService.signIn(loginDTO)), HttpStatus.OK);
    }
}

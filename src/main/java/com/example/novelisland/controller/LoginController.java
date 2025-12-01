package com.example.novelisland.controller;

import com.example.novelisland.dto.LoginDTO;
import com.example.novelisland.format.Message;
import com.example.novelisland.service.LoginService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/duplicateCheck")
    @ApiOperation(value = "아이디 중복확인", notes = "가입된 아이디가 있는지 확인한다.")
    public ResponseEntity<Message<?>> duplicateCheck(@RequestParam("userId") String userId) {
        log.info("[GET /login/duplicateCheck] 아이디 중복확인 요청 - userId={}", userId);

        return ResponseEntity.ok(
                Message.success(
                        "아이디 중복확인 성공",
                        loginService.duplicateCheck(userId)
                )
        );
    }

    @PostMapping("/signUp")
    @ApiOperation(value = "회원가입", notes = "유저가 회원가입을 한다.")
    public ResponseEntity<Message<?>> signUp(@RequestBody @Valid LoginDTO loginDTO) {
        log.info("[POST /login/signUp] 회원가입 요청 - loginDTO={}", loginDTO);

        return ResponseEntity.ok(
                Message.success(
                        "회원가입 성공",
                        loginService.signUp(loginDTO)
                )
        );
    }


    @PostMapping("/signIn")
    @ApiOperation(value = "로그인", notes = "유저가 로그인을 한다.")
    public ResponseEntity<Message<?>> signIn(@RequestBody @Valid LoginDTO loginDTO) {
        log.info("[POST /login/signIn] 로그인 요청 - loginDTO={}", loginDTO);

        return ResponseEntity.ok(
                Message.success(
                        "로그인 성공",
                        loginService.signIn(loginDTO)
                )
        );
    }
}

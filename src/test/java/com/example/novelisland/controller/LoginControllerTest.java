package com.example.novelisland.controller;

import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.dto.LoginDTO;
import com.example.novelisland.dto.TokenDTO;
import com.example.novelisland.exception.login.DuplicateIdException;
import com.example.novelisland.exception.login.InvalidIdException;
import com.example.novelisland.exception.login.InvalidPasswordException;
import com.example.novelisland.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    private LoginDTO loginDTO;

    @BeforeEach
    void setUp() {
        log.info("테스트 DTO 생성");
        
        loginDTO = LoginDTO.builder()
                .userId("test1")
                .userPassword("123")
                .build();

        log.info("테스트 DTO 생성완료");
    }

    @Test
    @DisplayName("새로운 사용자로 회원가입 테스트")
    void testSingUp_새로운사용자() throws Exception {
        log.info("새로운 사용자로 회원가입 테스트 시작");

        // given
        given(loginService.signUp(loginDTO)).willReturn(new TokenDTO());

        // when & then
        mockMvc.perform(post("/login/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"test\",\"userPassword\":\"123\"}"))
                .andExpect(status().isOk());

        log.info("새로운 사용자로 회원가입 테스트 종료");
    }

    @Test
    @DisplayName("중복 사용자로 회원가입 테스트")
    void testSignUp_중복사용자() throws Exception {
        log.info("중복 사용자로 회원가입 테스트 시작");

        // given
        given(loginService.signUp(any(LoginDTO.class))).willThrow(new DuplicateIdException(ErrorCode.DUPLICATE_ID_TOKEN));

        // when & then
        mockMvc.perform(post("/login/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"test\",\"userPassword\":\"123\"}"))
                .andExpect(status().isBadRequest());

        log.info("중복 사용자로 회원가입 테스트 종료");
    }


    @Test
    @DisplayName("로그인 테스트 성공")
    void testSignIn_성공() throws Exception {
        log.info("로그인 테스트 시작");

        // given
        given(loginService.signIn(loginDTO)).willReturn(new TokenDTO());

        // when & then
        mockMvc.perform(post("/login/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"test\",\"userPassword\":\"123\"}"))
                .andExpect(status().isOk());

        log.info("로그인 테스트 종료");
    }

    @Test
    @DisplayName("잘못된 아이디로 로그인 테스트")
    void testSingIn_잘못된아이디() throws Exception {
        log.info("잘못된 아이디로 로그인 테스트 시작");

        // given
        given(loginService.signIn(any(LoginDTO.class))).willThrow(new InvalidIdException(ErrorCode.INVALID_ID_TOKEN));

        // when & then
        mockMvc.perform(post("/login/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"test\",\"userPassword\":\"123\"}"))
                .andExpect(status().isBadRequest());

        log.info("잘못된 아이디로 로그인 테스트 종료");
    }

    @Test
    @DisplayName("잘못된 비밀번호로 로그인 테스트")
    void testSingIn() throws Exception {
        log.info("잘못된 비밀번호로 로그인 테스트 시작");

        // given
        given(loginService.signIn(any(LoginDTO.class))).willThrow(new InvalidPasswordException(ErrorCode.INVALID_PASSWORD_TOKEN));

        // when & then
        mockMvc.perform(post("/login/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"test\",\"userPassword\":\"123\"}"))
                .andExpect(status().isBadRequest());

        log.info("잘못된 비밀번호로 로그인 테스트 종료");
    }

}
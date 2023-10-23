package com.example.novelisland.service;

import com.example.novelisland.Jwt.JwtTokenProvider;
import com.example.novelisland.domain.User;
import com.example.novelisland.dto.LoginDTO;
import com.example.novelisland.dto.TokenDTO;
import com.example.novelisland.dto.UserDTO;
import com.example.novelisland.exception.login.DuplicateIdException;
import com.example.novelisland.exception.login.InvalidIdException;
import com.example.novelisland.exception.login.InvalidPasswordException;
import com.example.novelisland.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith({MockitoExtension.class})
class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private LoginService loginService;

    private LoginDTO loginDTO;

    @BeforeEach
    public void setUp() {
        log.info("테스트 DTO 생성");

        loginDTO = LoginDTO.builder()
                .userId("test1")
                .userPassword("123")
                .build();

        log.info("테스트 DTO 생성완료");
    }

    @Test
    @DisplayName("새로운 사용자로 회원가입 테스트")
    void testSignUp_새로운사용자() {
        log.info("새로운 사용자로 회원가입 테스트 시작");

        // given
        User resultUser = User.builder()
                .userIndex(1L)
                .userId(loginDTO.getUserId())
                .userPassword(loginDTO.getUserPassword())
                .build();

        when(userRepository.existsByUserId(loginDTO.getUserId())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(resultUser);

        when(jwtTokenProvider.createToken(anyString(), anyList())).thenReturn("jwt-test");

        // when
        TokenDTO tokenDTO = loginService.signUp(loginDTO);

        // then
        assertThat(tokenDTO).isNotNull();

        log.info("새로운 사용자로 회원가입 테스트 종료");
    }

    @Test
    @DisplayName("중복된 사용자로 회원가입 테스트")
    void testSignUp_중복사용자() {
        log.info("중복된 사용자로 회원가입 테스트 시작");

        // given
        when(userRepository.existsByUserId(loginDTO.getUserId())).thenReturn(true);

        // when

        // then
        assertThatThrownBy(() -> loginService.signUp(loginDTO))
                .isInstanceOf(DuplicateIdException.class);

        log.info("중복된 사용자로 회원가입 테스트 종료");
    }

    @Test
    @DisplayName("로그인 테스트 성공")
    void testSignIn_성공() {
        log.info("로그인 테스트 시작");

        // given
        when(userRepository.existsByUserId(loginDTO.getUserId())).thenReturn(true);

        User resultUser = User.builder()
                .userIndex(16973L)
                .userId(loginDTO.getUserId())
                .userPassword(loginDTO.getUserPassword())
                .build();

        when(userRepository.findByUserId(loginDTO.getUserId())).thenReturn(resultUser);

        when(jwtTokenProvider.createToken(anyString(), anyList())).thenReturn("jwt-test");

        // when
        TokenDTO tokenDTO = loginService.signIn(loginDTO);

        // then
        assertThat(tokenDTO).isNotNull();

        log.info("로그인 테스트 종료");
    }

    @Test
    @DisplayName("잘못된 아이디로 로그인 테스트")
    void testSignIn_잘못된아이디() {
        log.info("잘못된 아이디로 로그인 테스트 시작");

        // given
        when(userRepository.existsByUserId(loginDTO.getUserId())).thenReturn(false);

        // then
        assertThatThrownBy(() -> loginService.signIn(loginDTO))
                .isInstanceOf(InvalidIdException.class);

        log.info("잘못된 아이디로 로그인 테스트 종료");
    }

    @Test
    @DisplayName("잘못된 비밀번호로 로그인 테스트")
    void testSignIn_잘못된비밀번호() {
        log.info("잘못된 비밀번호로 로그인 테스트 시작");

        // given
        when(userRepository.existsByUserId(loginDTO.getUserId())).thenReturn(true);

        User resultUser = User.builder()
                .userIndex(1L)
                .userId(loginDTO.getUserId())
                .userPassword("1234")
                .build();

        when(userRepository.findByUserId(loginDTO.getUserId())).thenReturn(resultUser);

        // when

        // then
        assertThatThrownBy(() -> loginService.signIn(loginDTO))
                .isInstanceOf(InvalidPasswordException.class);

        log.info("잘못된 비밀번호로 로그인 테스트 종료");
    }

}
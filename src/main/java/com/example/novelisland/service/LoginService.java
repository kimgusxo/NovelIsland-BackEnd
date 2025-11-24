package com.example.novelisland.service;

import com.example.novelisland.Jwt.JwtTokenProvider;
import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.domain.User;
import com.example.novelisland.dto.LoginDTO;
import com.example.novelisland.dto.TokenDTO;
import com.example.novelisland.exception.login.ConcurrentlySignUpException;
import com.example.novelisland.exception.login.DuplicateIdException;
import com.example.novelisland.exception.login.InvalidIdException;
import com.example.novelisland.exception.login.InvalidPasswordException;
import com.example.novelisland.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 멀티 쓰레드에 대해 Lock을 걸기 위하여 선언
    private final Map<String, ReentrantLock> userLocks = new ConcurrentHashMap<>();


    @Autowired
    public LoginService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public Boolean duplicateCheck(String userId) {
        Boolean token = userRepository.existsByUserId(userId);
        return token;
    }

    @Transactional
    public TokenDTO signUp(LoginDTO loginDTO) {

        // userId에 대한 락을 가져옴
        ReentrantLock userLock = userLocks.computeIfAbsent(loginDTO.getUserId(), k -> new ReentrantLock());

        // userId에 대한 락을 시도
        if (!userLock.tryLock()) {
            // 이미 다른 쓰레드에서 lock이 걸려 있다면 예외를 던짐
            throw new ConcurrentlySignUpException(ErrorCode.CONCURRENTLY_SIGNUP_TOKEN);
        }

        try {
            // userId가 이미 존재하는지 확인
            Boolean token = userRepository.existsByUserId(loginDTO.getUserId());

            if (token) {
                // 이미 userId가 존재하면 예외를 던지거나 해당 상황을 처리
                throw new DuplicateIdException(ErrorCode.DUPLICATE_ID_TOKEN);
            } else {
                // userId가 존재하지 않으면 가입 프로세스 진행
                User user = User.builder()
                        .userId(loginDTO.getUserId())
                        .userPassword(loginDTO.getUserPassword())
                        .build();

                String jwtToken = jwtTokenProvider.createToken(user.getUserId(), Collections.singletonList("ROLE_USER"));
                String refreshJwtToken = jwtTokenProvider.createRefreshToken(user.getUserId(), Collections.singletonList("ROLE_USER"));

                user.setRefreshToken(refreshJwtToken);

                userRepository.save(user);

                return TokenDTO.builder()
                        .userIndex(user.getUserIndex())
                        .userId(user.getUserId())
                        .userPassword(user.getUserPassword())
                        .jwtToken(jwtToken)
                        .refreshJwtToken(refreshJwtToken)
                        .build();
            }
        } finally {
            // userId에 대한 락을 해제
            userLock.unlock();
        }
    }

    @Transactional
    public TokenDTO signIn(LoginDTO loginDTO) {

        Boolean token = userRepository.existsByUserId(loginDTO.getUserId());

        if(token) {
            User user = userRepository.findByUserId(loginDTO.getUserId());
            if(user.getUserPassword().equals(loginDTO.getUserPassword())) {

                String jwtToken = jwtTokenProvider.createToken(user.getUserId(), Collections.singletonList("ROLE_USER"));
                String refreshJwtToken = jwtTokenProvider.createRefreshToken(user.getUserId(), Collections.singletonList("ROLE_USER"));

                user.setRefreshToken(refreshJwtToken);

                userRepository.save(user);

                return TokenDTO.builder()
                        .userIndex(user.getUserIndex())
                        .userId(user.getUserId())
                        .userPassword(user.getUserPassword())
                        .jwtToken(jwtToken)
                        .refreshJwtToken(refreshJwtToken)
                        .build();
            } else {
                // 비밀번호가 잘못된 예외처리.
                throw new InvalidPasswordException(ErrorCode.INVALID_PASSWORD_TOKEN);
            }
        } else {
            // 아이디가 잘못된 예외처리
            throw new InvalidIdException(ErrorCode.INVALID_PASSWORD_TOKEN);
        }
    }
}


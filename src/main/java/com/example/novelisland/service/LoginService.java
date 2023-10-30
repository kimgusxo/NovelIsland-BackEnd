package com.example.novelisland.service;

import com.example.novelisland.Jwt.JwtTokenProvider;
import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.domain.User;
import com.example.novelisland.dto.LoginDTO;
import com.example.novelisland.dto.TokenDTO;
import com.example.novelisland.exception.login.DuplicateIdException;
import com.example.novelisland.exception.login.InvalidIdException;
import com.example.novelisland.exception.login.InvalidPasswordException;
import com.example.novelisland.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


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

        Boolean token = userRepository.existsByUserId(loginDTO.getUserId());

        if(token) {
            // 아이디 중복 예외처리
            throw new DuplicateIdException(ErrorCode.DUPLICATE_ID_TOKEN);
        } else {
            User user = userRepository.save(loginDTO.toEntity());

            String jwtToken = jwtTokenProvider.createToken(user.getUserId(), Collections.singletonList("ROLE_USER"));

            return TokenDTO.builder()
                    .userIndex(user.getUserIndex())
                    .userId(user.getUserId())
                    .userPassword(user.getUserPassword())
                    .jwtToken(jwtToken)
                    .build();
        }
    }

    @Transactional
    public TokenDTO signIn(LoginDTO loginDTO) {

        Boolean token = userRepository.existsByUserId(loginDTO.getUserId());

        if(token) {
            User user = userRepository.findByUserId(loginDTO.getUserId());
            if(user.getUserPassword().equals(loginDTO.getUserPassword())) {
                String jwtToken = jwtTokenProvider.createToken(user.getUserId(), Collections.singletonList("ROLE_USER"));

                return TokenDTO.builder()
                        .userIndex(user.getUserIndex())
                        .userId(user.getUserId())
                        .userPassword(user.getUserPassword())
                        .jwtToken(jwtToken)
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

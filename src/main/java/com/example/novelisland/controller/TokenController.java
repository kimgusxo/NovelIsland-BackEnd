package com.example.novelisland.controller;

import com.example.novelisland.Jwt.JwtTokenProvider;
import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.domain.User;
import com.example.novelisland.format.Message;
import com.example.novelisland.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tokens")
public class TokenController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @GetMapping("/refresh")
    public ResponseEntity<Message<?>> refreshToken(@RequestParam("userId") String userId,
                                                   @RequestParam("refreshToken") String refreshToken) {
        log.warn("[GET /tokens/refresh] 토큰 재발행 요청 - userId={}", userId);

        Optional<User> optionalUser = userRepository.findByUserId(userId);

        // 유저 존재 여부 검증
        if (optionalUser.isEmpty()) {
            log.warn("[GET /tokens/refresh] 사용자 정보 없음 - userId={}", userId);
            return ResponseEntity
                    .status(ErrorCode.NOT_EXIST_USER_TOKEN.getHttpStatus())
                    .body(Message.failure(ErrorCode.NOT_EXIST_USER_TOKEN));
        }

        User user = optionalUser.get();
        String userRefreshToken = user.getRefreshToken();

        // 토큰 존재 여부 검증
        if (userRefreshToken == null) {
            log.warn("[GET /tokens/refresh] DB에 저장된 리프레시 토큰 없음 - userId={}", userId);
            return ResponseEntity
                    .status(ErrorCode.TIMEOUT_JWT_TOKEN.getHttpStatus())
                    .body(Message.failure(ErrorCode.TIMEOUT_JWT_TOKEN));
        }

        // 토큰 일치하는지 안하는지 검증
        if (!userRefreshToken.equals(refreshToken)) {
            log.warn("[GET /tokens/refresh] 리프레시 토큰 불일치 - userId={}", userId);
            return ResponseEntity
                    .status(ErrorCode.TIMEOUT_JWT_TOKEN.getHttpStatus())
                    .body(Message.failure(ErrorCode.TIMEOUT_JWT_TOKEN));
        }

        // 새 액세스 토큰 발급
        String newAccessToken = jwtTokenProvider.createToken(
                userId,
                Collections.singletonList("ROLE_USER")
        );

        Authentication auth = jwtTokenProvider.getAuthentication(newAccessToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        log.info("[GET /tokens/refresh] 토큰 재발행 성공 - userId={}", userId);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + newAccessToken)
                .body(Message.success("토큰 재발행 성공", newAccessToken));
    }
}

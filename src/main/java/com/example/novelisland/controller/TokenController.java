package com.example.novelisland.controller;

import com.example.novelisland.Jwt.JwtTokenProvider;
import com.example.novelisland.domain.User;
import com.example.novelisland.format.Message;
import com.example.novelisland.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Slf4j
@RestController
public class TokenController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public TokenController(JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @GetMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(@RequestParam("userId") String userId,
                                          @RequestParam("refreshToken") String refreshToken) {
        log.warn("refreshToken: {}", refreshToken);

        User user = userRepository.findByUserId(userId);

        // 새로고침 시에 db에서 가져오는 값이 null??
        String userRefreshToken = user.getRefreshToken();

        if (userRefreshToken.equals(refreshToken)) {
            String newAccessToken = jwtTokenProvider.createToken(
                    userId,
                    Collections.singletonList("ROLE_USER")
            );

            Authentication auth = jwtTokenProvider.getAuthentication(newAccessToken);
            SecurityContextHolder.getContext().setAuthentication(auth);

            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + newAccessToken)
                    .body(newAccessToken);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("forbidden exception");
    }
}

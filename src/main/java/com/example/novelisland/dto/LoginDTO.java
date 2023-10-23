package com.example.novelisland.dto;

import com.example.novelisland.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class LoginDTO {
    @NotBlank
    private String userId;
    @NotBlank
    private String userPassword;

    @Builder
    public LoginDTO(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .userPassword(userPassword)
                .build();
    }
}

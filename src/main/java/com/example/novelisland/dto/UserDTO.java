package com.example.novelisland.dto;

import com.example.novelisland.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UserDTO {
    private Long userIndex;

    @NotBlank
    private String userId;
    @NotBlank
    private String userPassword;

    @Builder
    public UserDTO(Long userIndex, String userId, String userPassword) {
        this.userIndex = userIndex;
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public User toEntity() {
        return User.builder()
                .userIndex(userIndex)
                .userId(userId)
                .userPassword(userPassword)
                .build();
    }
}

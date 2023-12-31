package com.example.novelisland.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenDTO {
    private Long userIndex;
    private String userId;
    private String userPassword;
    private String jwtToken;
    private String refreshJwtToken;

    @Builder
    public TokenDTO(Long userIndex, String userId, String userPassword, String jwtToken, String refreshJwtToken) {
        this.userIndex = userIndex;
        this.userId = userId;
        this.userPassword = userPassword;
        this.jwtToken = jwtToken;
        this.refreshJwtToken = refreshJwtToken;
    }

}

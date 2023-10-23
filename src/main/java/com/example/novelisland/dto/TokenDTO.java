package com.example.novelisland.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenDTO {
    private Long userIndex;
    private String jwtToken;

    @Builder
    public TokenDTO(Long userIndex, String jwtToken) {
        this.userIndex = userIndex;
        this.jwtToken = jwtToken;
    }

}

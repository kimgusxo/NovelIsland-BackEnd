package com.example.novelisland.dto;

import com.example.novelisland.domain.BookMark;
import com.example.novelisland.domain.Novel;
import com.example.novelisland.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookMarkDTO {
    private Long bookMarkId;
    private Long userIndex;
    private Long novelId;

    @Builder
    public BookMarkDTO(Long bookMarkId, Long userIndex, Long novelId) {
        this.bookMarkId = bookMarkId;
        this.userIndex = userIndex;
        this.novelId = novelId;
    }

    public BookMark toEntity() {
        return BookMark.builder()
                .bookMarkId(bookMarkId)
                .user(User.builder()
                        .userIndex(userIndex)
                        .build())
                .novel(Novel.builder()
                        .novelId(novelId)
                        .build())
                .build();
    }
}

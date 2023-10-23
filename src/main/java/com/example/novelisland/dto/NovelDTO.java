package com.example.novelisland.dto;

import com.example.novelisland.domain.Author;
import com.example.novelisland.domain.Novel;
import com.example.novelisland.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NovelDTO {
    private Long novelId;
    private Long authorId;
    private Long tagId;
    private String novelName;
    private String novelThumbnail;
    private String novelExplanation;

    @Builder
    public NovelDTO(Long novelId, Long authorId, Long tagId,
                    String novelName, String novelThumbnail, String novelExplanation) {
        this.novelId = novelId;
        this.authorId = authorId;
        this.tagId = tagId;
        this.novelName = novelName;
        this.novelThumbnail = novelThumbnail;
        this.novelExplanation = novelExplanation;
    }

    public Novel toEntity() {
        return Novel.builder()
                .novelId(novelId)
                .author(Author.builder()
                        .authorId(authorId)
                        .build())
                .novelName(novelName)
                .novelThumbNail(novelThumbnail)
                .novelExplanation(novelExplanation)
                .build();
    }
}

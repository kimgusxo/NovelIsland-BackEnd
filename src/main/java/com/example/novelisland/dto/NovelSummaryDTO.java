package com.example.novelisland.dto;

import com.example.novelisland.domain.Author;
import com.example.novelisland.domain.Novel;
import com.example.novelisland.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NovelSummaryDTO {
    private Long novelId;
    private String authorName;
    private String tagClassification;
    private String novelName;
    private String novelThumbnail;
    private String novelExplanation;

    @Builder
    public NovelSummaryDTO(Long novelId, String authorName, String tagClassification,
                    String novelName, String novelThumbnail, String novelExplanation) {
        this.novelId = novelId;
        this.authorName = authorName;
        this.tagClassification = tagClassification;
        this.novelName = novelName;
        this.novelThumbnail = novelThumbnail;
        this.novelExplanation = novelExplanation;
    }

}

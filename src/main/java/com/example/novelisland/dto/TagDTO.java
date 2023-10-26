package com.example.novelisland.dto;

import com.example.novelisland.domain.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagDTO {

    private Long tagId;
    private String tagClassification;

    @Builder
    public TagDTO(Long tagId, String tagClassification) {
        this.tagId = tagId;
        this.tagClassification = tagClassification;
    }

    public Tag toEntity() {
        return Tag.builder()
                .tagId(tagId)
                .tagClassification(tagClassification)
                .build();
    }
}

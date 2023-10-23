package com.example.novelisland.dto;

import com.example.novelisland.domain.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchDTO {
    private String novelName;
    private List<Long> tagIdList;

    @Builder
    public SearchDTO(String novelName, List<Long> tagIdList) {
        this.novelName = novelName;
        this.tagIdList = tagIdList;
    }
}

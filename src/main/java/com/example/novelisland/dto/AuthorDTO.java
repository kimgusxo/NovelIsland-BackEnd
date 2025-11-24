package com.example.novelisland.dto;

import com.example.novelisland.domain.Author;
import com.example.novelisland.domain.Novel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class AuthorDTO {
    private Long authorId;
    private String authorName;
    private List<NovelDTO> novelDTOList;

    @Builder
    public AuthorDTO(Long authorId, String authorName, List<NovelDTO> novelDTOList) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.novelDTOList = novelDTOList;
    }

    public Author toEntity() {
        List<Novel> novelList = new ArrayList<>();

        novelDTOList.stream().forEach(n ->
                novelList.add(n.toEntity()));

        return Author.builder()
                .authorId(authorId)
                .authorName(authorName)
                .novelList(novelList)
                .build();
    }
}

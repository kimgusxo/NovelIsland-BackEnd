package com.example.novelisland.domain;

import com.example.novelisland.dto.NovelDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="NOVEL_TB")
public class Novel {

    @Id
    @GeneratedValue
    @Column(name = "NOVEL_ID")
    private Long novelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID")
    private Author author;

    @NotNull
    @Column(name = "NOVEL_NAME")
    private String novelName;

    @Column(name = "NOVEL_THUMBNAIL")
    private String novelThumbNail;

    @NotNull
    @Column(name = "NOVEL_EXPLANATION", columnDefinition = "TEXT")
    private String novelExplanation;

    @OneToMany(mappedBy = "novel")
    private List<BookMark> bookMarkList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    public Novel(Long novelId, Author author, String novelName,
                 String novelThumbNail, String novelExplanation, Tag tag) {
        this.novelId = novelId;
        this.author = author;
        this.novelName = novelName;
        this.novelThumbNail = novelThumbNail;
        this.novelExplanation = novelExplanation;
        this.tag = tag;
    }

    public NovelDTO toDTO() {
        return NovelDTO.builder()
                .novelId(novelId)
                .authorId(author.getAuthorId())
                .tagId(tag.getTagId())
                .novelName(novelName)
                .novelThumbnail(novelThumbNail)
                .novelExplanation(novelExplanation)
                .build();
    }

}

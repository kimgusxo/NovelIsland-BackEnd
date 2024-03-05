package com.example.novelisland.domain;

import com.example.novelisland.dto.AuthorDTO;
import com.example.novelisland.dto.NovelDTO;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "AUTHOR_TB")
public class Author {

    @Id
    @GeneratedValue
    @Column(name = "AUTHOR_ID")
    private Long authorId;

    @NotNull
    @Column(name = "AUTHOR_NAME",
            unique = true)
    private String authorName;

    @OneToMany(mappedBy = "author",
            cascade = CascadeType.PERSIST)
    private List<Novel> novelList;


    public AuthorDTO toDTO() {

        List<NovelDTO> novelDTOList = new ArrayList<>();

        novelList.forEach(novel ->
                novelDTOList.add(novel.toDTO()));

        return AuthorDTO.builder()
                .authorId(authorId)
                .authorName(authorName)
                .novelDTOList(novelDTOList)
                .build();
    }

}
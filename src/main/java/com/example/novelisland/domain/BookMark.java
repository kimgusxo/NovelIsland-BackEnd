package com.example.novelisland.domain;

import com.example.novelisland.dto.BookMarkDTO;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "BOOKMARK_TB")
public class BookMark {

    @Id
    @GeneratedValue
    @Column(name = "BOOKMARK_ID")
    private Long bookMarkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_INDEX")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOVEL_ID")
    private Novel novel;

    public BookMarkDTO toDTO() {
        return BookMarkDTO.builder()
                .bookMarkId(bookMarkId)
                .userIndex(user.getUserIndex())
                .novelId(novel.getNovelId())
                .build();
    }

}

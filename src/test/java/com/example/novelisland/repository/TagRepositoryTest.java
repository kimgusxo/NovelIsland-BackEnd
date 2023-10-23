package com.example.novelisland.repository;

import com.example.novelisland.domain.Novel;
import com.example.novelisland.projection.NovelSummary;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TagRepositoryTest {

    private final TagRepository tagRepository;

    private Pageable pageable;

    @Autowired
    public TagRepositoryTest(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 10);
    }

//    @Test
//    @DisplayName("태그의 존재유무 확인")
//    void existsByTagId() {
//        // given
//        Long tagId = 1L;
//
//        // when
//
//        // then
//    }

    @Test
    @DisplayName("태그 아이디로 태그 검색")
    void findNovelListById() {
        // given
        Long tagId = 4764L;

        // when
        boolean token = tagRepository.existsById(tagId);

        // then
        if(token) {
            List<NovelSummary> novelList = tagRepository.findNovelsByTagId(tagId, pageable);

            novelList.forEach(n ->
                            System.out.println(
                                    "소설 인덱스: " + n.getNovelId() + "\n" +
                                    "소설 제목: " + n.getNovelName() + "\n" +
                                    "작가 이름: " + n.getAuthorName() + "\n" +
                                    "소설 썸네일: " + n.getNovelThumbnail() + "\n" +
                                    "소설 설명: " + n.getNovelExplanation() + "\n" +
                                    "태그 분류: " + n.getTagClassification() + "\n"
                            )
                    );
        } else {
            fail("태그가 존재하지 않습니다.");
        }
    }

    @Test
    @DisplayName("태그 이름으로 태그 검색")
    void findNovelListByClassification() {
        // given
        String tagClassification = "판타지";

        // when
        Boolean token = tagRepository.existsByTagClassification(tagClassification);

        // then
        if(token) {
            List<NovelSummary> novelList = tagRepository.findNovelsByTagClassification(tagClassification, pageable);

            novelList.forEach(n ->
                    System.out.println(
                            "소설 인덱스: " + n.getNovelId() + "\n" +
                            "소설 제목: " + n.getNovelName() + "\n" +
                            "작가 이름: " + n.getAuthorName() + "\n" +
                            "소설 썸네일: " + n.getNovelThumbnail() + "\n" +
                            "소설 설명: " + n.getNovelExplanation() + "\n" +
                            "태그 분류: " + n.getTagClassification() + "\n"
                    )
            );
        } else {
            fail("태그가 존재하지 않습니다.");
        }

    }
}

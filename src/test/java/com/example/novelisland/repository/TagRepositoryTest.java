//package com.example.novelisland.repository;
//
//import com.example.novelisland.domain.Novel;
//import com.example.novelisland.domain.Tag;
//import com.example.novelisland.projection.NovelSummary;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@Slf4j
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class TagRepositoryTest {
//
//    private final TagRepository tagRepository;
//
//    private Pageable pageable;
//
//    @Autowired
//    public TagRepositoryTest(TagRepository tagRepository) {
//        this.tagRepository = tagRepository;
//    }
//
//    @BeforeEach
//    void setUp() {
//        log.info("페이지 설정");
//
//        pageable = PageRequest.of(0, 10);
//
//        log.info("페이지 설정 완료");
//    }
//
//    @Test
//    @DisplayName("태그 아이디로 태그의 존재유무 테스트")
//    void testExistsByTagId() {
//        log.info("태그 아이디로 태그의 존재유무 테스트 시작");
//
//        // given
//        Long tagId = 4764L;
//
//        // when
//        Boolean token = tagRepository.existsByTagId(tagId);
//
//        // then
//        assertThat(token)
//                .as("태그가 존재하지 않습니다.")
//                .isTrue();
//
//        log.info("태그 아이디로 태그의 존재유무 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("태그 이름으로 태그의 존재유무 테스트")
//    void testExistsByTagClassification() {
//        log.info("태그 아이디로 태그의 존재유무 테스트 시작");
//
//        // given
//        String tagClassification = "판타지";
//
//        // when
//        Boolean token = tagRepository.existsByTagClassification(tagClassification);
//
//        // then
//        assertThat(token)
//                .as("태그가 존재하지 않습니다.")
//                .isTrue();
//
//
//        log.info("태그 아이디로 태그의 존재유무 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("태그 아이디로 태그 검색 테스트")
//    void testFindByTagId() {
//        log.info("태그 아이디로 태그 검색 테스트 시작");
//
//        // given
//        Long tagId = 4764L;
//
//        // when
//        Tag tag = tagRepository.findByTagId(tagId);
//
//        // then
//        assertThat(tag)
//                .as("태그가 존재하지 않습니다.")
//                .isNotNull();
//
//        System.out.println(tag.getTagId());
//        System.out.println(tag.getTagClassification());
//
//        log.info("태그 아이디로 태그 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("태그 이름으로 태그 검색 테스트")
//    void testFindByTagClassification() {
//        log.info("태그 이름으로 태그 검색 테스트 시작");
//
//        // given
//        String tagClassification = "판타지";
//
//        // when
//        Tag tag = tagRepository.findByTagClassification(tagClassification);
//
//        // then
//        assertThat(tag)
//                .as("태그가 존재하지 않습니다.")
//                .isNotNull();
//
//        System.out.println(tag.getTagId());
//        System.out.println(tag.getTagClassification());
//
//        log.info("태그 이름으로 태그 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("태그 아이디로 태그 검색 테스트")
//    void testFindNovelsById() {
//        log.info("태그 아이디로 태그 검색 테스트 시작");
//
//        // given
//        Long tagId = 4764L;
//
//        // when
//        List<NovelSummary> novelList = tagRepository.findNovelsByTagId(tagId, pageable);
//
//        // then
//        assertThat(novelList)
//                .as("해당 이름과 관련된 소설이 없습니다.")
//                .isNotEmpty();
//
//        novelList.forEach(n ->
//                        System.out.println(
//                                "소설 인덱스: " + n.getNovelId() + "\n" +
//                                        "소설 제목: " + n.getNovelName() + "\n" +
//                                        "작가 이름: " + n.getAuthorName() + "\n" +
//                                        "소설 썸네일: " + n.getNovelThumbnail() + "\n" +
//                                        "소설 설명: " + n.getNovelExplanation() + "\n" +
//                                        "태그 분류: " + n.getTagClassification() + "\n"
//                        )
//        );
//
//        log.info("태그 아이디로 태그 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("태그 이름으로 태그 검색 테스트")
//    void testFindNovelsByClassification() {
//        log.info("태그 이름으로 태그 검색 테스트 시작");
//
//        // given
//        String tagClassification = "판타지";
//
//        // when
//        List<NovelSummary> novelList = tagRepository.findNovelsByTagClassification(tagClassification, pageable);
//
//        // then
//        assertThat(novelList)
//                .as("해당 이름과 관련된 소설이 없습니다.")
//                .isNotEmpty();
//
//        novelList.forEach(n ->
//                System.out.println(
//                        "소설 인덱스: " + n.getNovelId() + "\n" +
//                                "소설 제목: " + n.getNovelName() + "\n" +
//                                "작가 이름: " + n.getAuthorName() + "\n" +
//                                "소설 썸네일: " + n.getNovelThumbnail() + "\n" +
//                                "소설 설명: " + n.getNovelExplanation() + "\n" +
//                                "태그 분류: " + n.getTagClassification() + "\n"
//                )
//        );
//
//        log.info("태그 이름으로 태그 검색 테스트 종료");
//    }
//}

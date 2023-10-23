package com.example.novelisland.repository;

import com.example.novelisland.domain.Novel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NovelRepositoryTest {

    private final NovelRepository novelRepository;

    private Pageable pageable;

    @Autowired
    public NovelRepositoryTest(NovelRepository novelRepository) {
        this.novelRepository = novelRepository;
    }

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 10);
    }

    @Test
    @DisplayName("소설 아이디로 존재여부 확인 테스트")
    void testExistsNovelById() {
        log.info("소설 아이디로 존재여부 확인 테스트 시작");
        
        // given
        Long novelId = 6497L;

        // when
        Boolean token = novelRepository.existsByNovelId(novelId);

        // then
        assertThat(token)
                .as("소설이 존재하지 않습니다.")
                .isTrue();

        log.info("소설 아이디로 존재여부 확인 테스트 종료");
    }

    @Test
    @DisplayName("소설 아이디로 소설 검색 테스트")
    void testFindNovelById() {
        log.info("소설 아이디로 소설 검색 테스트 시작");
        
        // given
        Long novelId = 6497L;

        // when
        Novel novel = novelRepository.findByNovelId(novelId);

        // then
        assertThat(novel)
                .as("소설이 존재하지 않습니다.")
                .isNotNull();

        System.out.println("소설 인덱스: " + novel.getNovelId());
        System.out.println("소설 제목: " + novel.getNovelName());
        System.out.println("작가 이름 : " + novel.getAuthor().getAuthorName());
        System.out.println("소설 썸네일: " + novel.getNovelThumbNail());
        System.out.println("소설 설명: " + novel.getNovelExplanation());
        System.out.println("태그 분류: " + novel.getTag().getTagClassification());
            
        log.info("소설 아이디로 소설 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설이름이 포함되는 소설 목록 검색 테스트")
    void testFindNovelListByName() {
        log.info("소설이름이 포함되는 소설 목록 검색 테스트 시작");
        
        // given
        String novelContainingName = "화산";

        // when
        List<Novel> novelList = novelRepository.findByNovelNameContaining(novelContainingName, pageable);

        // then
        assertThat(novelList)
                .as("해당 이름과 관련된 소설이 없습니다.")
                .isNotEmpty();
        
        novelList.forEach(n ->
                System.out.println(
                        "소설 인덱스: " + n.getNovelId() + "\n" +
                        "소설 제목: " + n.getNovelName() + "\n" +
                        "작가 이름: " + n.getAuthor().getAuthorName() + "\n" +
                        "소설 썸네일: " + n.getNovelThumbNail() + "\n" +
                        "소설 설명: " + n.getNovelExplanation() + "\n" +
                        "태그 분류: " + n.getTag().getTagClassification() + "\n"
                ));

        log.info("소설이름이 포함되는 소설 목록 검색 테스트 종료");
    }

    @Test
    @DisplayName("여러개의 태그와 소설이름이 포함되는 소설 목록 검색 테스트")
    void testFindNovelListByNovelNameAndTagIdList() {
        log.info("여러개의 태그와 소설이름이 포함되는 소설 목록 검색 테스트 시작");
        
        // given
        String novelName = "가";
        List<Long> tagIdList = new ArrayList<>();
        tagIdList.add(4766L);
        tagIdList.add(4765L);

        // when
        List<Novel> novelList = novelRepository.findByNovelNameContainingAndTagIdList(novelName, tagIdList, pageable);

        // then
        assertThat(novelList)
                .as("해당 이름과 관련된 소설이 없습니다.")
                .isNotEmpty();

        novelList.forEach(n ->
                System.out.println(
                        "소설 인덱스: " + n.getNovelId() + "\n" +
                                "소설 제목: " + n.getNovelName() + "\n" +
                                "작가 이름: " + n.getAuthor().getAuthorName() + "\n" +
                                "소설 썸네일: " + n.getNovelThumbNail() + "\n" +
                                "소설 설명: " + n.getNovelExplanation() + "\n" +
                                "태그 분류: " + n.getTag().getTagClassification() + "\n"
                ));
        
        log.info("여러개의 태그와 소설이름이 포함되는 소설 목록 검색 테스트 종료");
    }

}
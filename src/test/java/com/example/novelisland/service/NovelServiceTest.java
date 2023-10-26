package com.example.novelisland.service;

import com.example.novelisland.domain.Author;
import com.example.novelisland.domain.Novel;
import com.example.novelisland.domain.Tag;
import com.example.novelisland.dto.NovelDTO;
import com.example.novelisland.exception.novel.NotExistNovelException;
import com.example.novelisland.repository.NovelRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class NovelServiceTest {

    @Mock
    private NovelRepository novelRepository;

    @InjectMocks
    private NovelService novelService;

    private Novel novel;
    private Pageable pageable;

    private int page;
    private int size;

    @BeforeEach
    void setUp() {
        log.info("테스트 DTO 생성");
        
        Long novelId = 1L;
        Long authorId = 1L;
        Long tagId = 1L;
        String novelName = "test";
        String novelThumbNail = "url";
        String novelExplanation = "테스트입니다";

        novel = Novel.builder()
                .novelId(novelId)
                .author(Author.builder()
                        .authorId(authorId)
                        .build())
                .tag(Tag.builder()
                        .tagId(tagId)
                        .build())
                .novelName(novelName)
                .novelThumbNail(novelThumbNail)
                .novelExplanation(novelExplanation)
                .build();

        page = 0;
        size = 10;

        pageable = PageRequest.of(page, size);

        log.info("테스트 DTO 생성완료");
    }

    @Test
    @DisplayName("소설 아이디로 소설 검색 테스트 성공")
    void testGetNovelByNovelId_성공() {
        log.info("소설 아이디로 소설 검색 테스트 시작");

        // given
        when(novelRepository.existsByNovelId(novel.getNovelId())).thenReturn(true);
        when(novelRepository.findByNovelId(novel.getNovelId())).thenReturn(novel);

        // when
        NovelDTO novelDTO = novelService.getNovelByNovelId(novel.getNovelId());

        // then
        assertThat(novelDTO).isNotNull();

        log.info("소설 아이디로 소설 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 아이디로 소설 검색 테스트 실패")
    void testGetNovelByNovelId_실패() {
        log.info("소설 아이디로 소설 검색 테스트 시작");

        // given
        when(novelRepository.existsByNovelId(novel.getNovelId())).thenReturn(false);

        // when

        // then
        assertThatThrownBy(() -> novelService.getNovelByNovelId(novel.getNovelId()))
                .isInstanceOf(NotExistNovelException.class);

        log.info("소설 아이디로 소설 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 이름으로 소설 리스트 검색 테스트 성공")
    void testGetNovelsByNovelName_성공() {
        log.info("소설 이름으로 소설 리스트 검색 테스트 시작");

        // given
        List<Novel> novelList = new ArrayList<>();
        novelList.add(novel);

        when(novelRepository.findByNovelNameContaining(novel.getNovelName(), pageable)).thenReturn(novelList);

        // when
        List<NovelDTO> novelDTOList = novelService.getNovelsByNovelName(novel.getNovelName(), page, size);

        // then
        assertThat(novelDTOList).isNotNull();

        log.info("소설 이름으로 소설 리스트 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 이름으로 소설 리스트 검색 테스트 실패")
    void testGetNovelsByNovelName_실패() {
        log.info("소설 이름으로 소설 리스트 검색 테스트 시작");

        // given
        when(novelRepository.findByNovelNameContaining(novel.getNovelName(), pageable)).thenReturn(new ArrayList<>());

        // when

        // then
        assertThatThrownBy(() -> novelService.getNovelsByNovelName(novel.getNovelName(), page, size))
                .isInstanceOf(NotExistNovelException.class);

        log.info("소설 이름으로 소설 리스트 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 성공")
    void testGetNovelsByNovelContainingAndTagIdList_성공() {
        log.info("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 시작");

        // given
        String novelName = "test";

        List<Long> tagIdList = new ArrayList<>();
        tagIdList.add(novel.getTag().getTagId());

        List<Novel> novelList = new ArrayList<>();
        novelList.add(novel);

        when(novelRepository.findByNovelNameContainingAndTagIdList(novelName, tagIdList, pageable)).thenReturn(novelList);

        // when
        List<NovelDTO> novelDTOList = novelService.getNovelsByNovelNameContainingAndTagIdList(novelName, tagIdList, page, size);

        // then
        assertThat(novelDTOList).isNotNull();

        log.info("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 실패")
    void testGetNovelsByNovelContainingAndTagIdList_실패() {
        log.info("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 시작");

        // given
        String novelName = "test";

        List<Long> tagIdList = new ArrayList<>();
        tagIdList.add(novel.getTag().getTagId());

        when(novelRepository.findByNovelNameContainingAndTagIdList(novelName, tagIdList, pageable)).thenReturn(new ArrayList<>());

        // when

        // then
        assertThatThrownBy(() -> novelService.getNovelsByNovelNameContainingAndTagIdList(novelName, tagIdList, page, size))
                .isInstanceOf(NotExistNovelException.class);

        log.info("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 종료");
    }
}
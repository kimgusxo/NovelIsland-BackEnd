package com.example.novelisland.service;

import com.example.novelisland.domain.Author;
import com.example.novelisland.domain.Novel;
import com.example.novelisland.domain.Tag;
import com.example.novelisland.dto.NovelSummaryDTO;
import com.example.novelisland.exception.novel.NotExistNovelException;
import com.example.novelisland.projection.NovelSummary;
import com.example.novelisland.repository.TagRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    private Pageable pageable;

    private int page;
    private int size;

    @BeforeEach
    void setUp() {
        log.info("테스트 페이지네이션 생성");
        
        page = 0;
        size = 10;

        pageable = PageRequest.of(page, size);

        log.info("테스트 페이지네이션 생성완료");
    }

    @Test
    @DisplayName("태그 아이디로 소설 리스트 검색 테스트 성공")
    void testGetNovelsByTagId_성공() {
        log.info("태그 아이디로 소설 리스트 검색 테스트 시작");

        // given
        Long tagId = 1L;
        when(tagRepository.existsByTagId(tagId)).thenReturn(true);
        when(tagRepository.findNovelsByTagId(tagId, pageable)).thenReturn(new ArrayList<>());

        // when
        List<NovelSummaryDTO> novelSummaryDTOList = tagService.getNovelsByTagId(tagId, page, size);

        // then
        assertThat(novelSummaryDTOList).isNotNull();

        log.info("태그 아이디로 소설 리스트 검색 테스트 종료");
    }

    @Test
    @DisplayName("태그 아이디로 소설 리스트 검색 테스트 실패")
    void testGetNovelsByTagId_실패() {
        log.info("태그 아이디로 소설 리스트 검색 테스트 시작");

        // given
        Long tagId = 1L;
        when(tagRepository.existsByTagId(tagId)).thenReturn(false);

        // when

        // then
        assertThatThrownBy(() -> tagService.getNovelsByTagId(tagId, page, size))
                .isInstanceOf(NotExistNovelException.class);

        log.info("태그 아이디로 소설 리스트 검색 테스트 종료");
    }

    @Test
    @DisplayName("태그 이름으로 소설 리스트 검색 테스트 성공")
    void testGetNovelsByTagClassification_성공() {
        log.info("태그 이름으로 소설 리스트 검색 테스트 시작");

        // given
        String tagClassification = "판타지";

        when(tagRepository.existsByTagClassification(tagClassification)).thenReturn(true);
        when(tagRepository.findNovelsByTagClassification(tagClassification, pageable)).thenReturn(new ArrayList<>());

        // when
        List<NovelSummaryDTO> novelSummaryDTOList = tagService.getNovelsByTagClassification(tagClassification, page, size);

        // then
        assertThat(novelSummaryDTOList).isNotNull();

        log.info("태그 이름으로 소설 리스트 검색 테스트 종료");
    }

    @Test
    @DisplayName("태그 이름으로 소설 리스트 검색 테스트 실패")
    void testGetNovelsByTagClassification_실패() {
        log.info("태그 이름으로 소설 리스트 검색 테스트 시작");

        // given
        String tagClassification = "판타지";

        when(tagRepository.existsByTagClassification(tagClassification)).thenReturn(false);

        // when

        // then
        assertThatThrownBy(() -> tagService.getNovelsByTagClassification(tagClassification, page, size))
                .isInstanceOf(NotExistNovelException.class);

        log.info("태그 이름으로 소설 리스트 검색 테스트 종료");
    }
}
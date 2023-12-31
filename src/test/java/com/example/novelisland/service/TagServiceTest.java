//package com.example.novelisland.service;
//
//import com.example.novelisland.domain.Author;
//import com.example.novelisland.domain.Novel;
//import com.example.novelisland.domain.Tag;
//import com.example.novelisland.dto.NovelSummaryDTO;
//import com.example.novelisland.dto.TagDTO;
//import com.example.novelisland.exception.novel.NotExistNovelException;
//import com.example.novelisland.exception.tag.NotExistTagException;
//import com.example.novelisland.projection.NovelSummary;
//import com.example.novelisland.repository.TagRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@Slf4j
//@ExtendWith(MockitoExtension.class)
//class TagServiceTest {
//
//    @Mock
//    private TagRepository tagRepository;
//
//    @InjectMocks
//    private TagService tagService;
//
//    private Tag tag;
//
//    private Pageable pageable;
//    private Sort sort;
//
//    private int page;
//    private int size;
//
//    @BeforeEach
//    void setUp() {
//        log.info("페이지 설정");
//
//        page = 0;
//        size = 32;
//
//        sort = Sort.by(Sort.Order.asc("tagClassification"));
//        pageable = PageRequest.of(page, size);
//
//        log.info("페이지 설정 완료");
//
//        log.info("테스트 DTO 생성");
//
//        Long tagId = 1L;
//        String tagClassification = "test";
//        List<Novel> novelList = new ArrayList<>();
//
//        tag = Tag.builder()
//                .tagId(tagId)
//                .tagClassification(tagClassification)
//                .novelList(novelList)
//                .build();
//
//        log.info("테스트 DTO 생성 완료");
//    }
//
//    @Test
//    @DisplayName("정렬된 태그 리스트 검색 테스트 성공")
//    void testGetSortingTags_성공() {
//        log.info("정렬된 태그 리스트 검색 테스트 시작");
//
//        // given
//        List<Tag> tagList = new ArrayList<>();
//        tagList.add(tag);
//
//        when(tagRepository.findAll(sort)).thenReturn(tagList);
//
//        // when
//        List<TagDTO> tagDTOList = tagService.getSortingTags();
//
//        // then
//        assertThat(tagDTOList)
//                .isNotEmpty();
//
//        log.info("정렬된 태그 리스트 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("태그 아이디로 태그 검색 테스트 성공")
//    void testGetTagByTagId_성공() {
//        log.info("태그 아이디로 태그 검색 테스트 시작");
//
//        // given
//        when(tagRepository.existsByTagId(tag.getTagId())).thenReturn(true);
//        when(tagRepository.findByTagId(tag.getTagId())).thenReturn(tag);
//
//        // when
//        TagDTO tagDTO = tagService.getTagByTagId(tag.getTagId());
//
//        // then
//        assertThat(tagDTO)
//                .isNotNull();
//
//        log.info("태그 아이디로 태그 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("태그 아이디로 태그 검색 테스트 실패")
//    void testGetTagByTagId_실패() {
//        log.info("태그 아이디로 태그 검색 테스트 시작");
//
//        // given
//        when(tagRepository.existsByTagId(tag.getTagId())).thenReturn(false);
//
//        // when
//
//        // then
//        assertThatThrownBy(() -> tagService.getTagByTagId(tag.getTagId()))
//                .isInstanceOf(NotExistTagException.class);
//
//        log.info("태그 아이디로 태그 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("태그 아이디로 소설 리스트 검색 테스트 성공")
//    void testGetNovelsByTagId_성공() {
//        log.info("태그 아이디로 소설 리스트 검색 테스트 시작");
//
//        // given
//        Long tagId = 1L;
//        when(tagRepository.existsByTagId(tagId)).thenReturn(true);
//        when(tagRepository.findNovelsByTagId(tagId, pageable)).thenReturn(new ArrayList<>());
//
//        // when
//        List<NovelSummaryDTO> novelSummaryDTOList = tagService.getNovelsByTagId(tagId, page, size);
//
//        // then
//        assertThat(novelSummaryDTOList).isNotNull();
//
//        log.info("태그 아이디로 소설 리스트 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("태그 아이디로 소설 리스트 검색 테스트 실패")
//    void testGetNovelsByTagId_실패() {
//        log.info("태그 아이디로 소설 리스트 검색 테스트 시작");
//
//        // given
//        Long tagId = 1L;
//        when(tagRepository.existsByTagId(tagId)).thenReturn(false);
//
//        // when
//
//        // then
//        assertThatThrownBy(() -> tagService.getNovelsByTagId(tagId, page, size))
//                .isInstanceOf(NotExistNovelException.class);
//
//        log.info("태그 아이디로 소설 리스트 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("태그 이름으로 소설 리스트 검색 테스트 성공")
//    void testGetNovelsByTagClassification_성공() {
//        log.info("태그 이름으로 소설 리스트 검색 테스트 시작");
//
//        // given
//        String tagClassification = "판타지";
//
//        when(tagRepository.existsByTagClassification(tagClassification)).thenReturn(true);
//        when(tagRepository.findNovelsByTagClassification(tagClassification, pageable)).thenReturn(new ArrayList<>());
//
//        // when
//        List<NovelSummaryDTO> novelSummaryDTOList = tagService.getNovelsByTagClassification(tagClassification, page, size);
//
//        // then
//        assertThat(novelSummaryDTOList).isNotNull();
//
//        log.info("태그 이름으로 소설 리스트 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("태그 이름으로 소설 리스트 검색 테스트 실패")
//    void testGetNovelsByTagClassification_실패() {
//        log.info("태그 이름으로 소설 리스트 검색 테스트 시작");
//
//        // given
//        String tagClassification = "판타지";
//
//        when(tagRepository.existsByTagClassification(tagClassification)).thenReturn(false);
//
//        // when
//
//        // then
//        assertThatThrownBy(() -> tagService.getNovelsByTagClassification(tagClassification, page, size))
//                .isInstanceOf(NotExistNovelException.class);
//
//        log.info("태그 이름으로 소설 리스트 검색 테스트 종료");
//    }
//}
//package com.example.novelisland.service;
//
//import com.example.novelisland.domain.Author;
//import com.example.novelisland.domain.Novel;
//import com.example.novelisland.domain.Tag;
//import com.example.novelisland.dto.NovelDTO;
//import com.example.novelisland.exception.novel.NotExistNovelException;
//import com.example.novelisland.repository.NovelRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//import static org.mockito.Mockito.when;
//
//@Slf4j
//@ExtendWith(MockitoExtension.class)
//class NovelServiceTest {
//
//    @Mock
//    private NovelRepository novelRepository;
//
//    @InjectMocks
//    private NovelService novelService;
//
//    private Novel novel;
//    private Pageable pageable;
//    private Pageable novelNameSortingPageable;
//    private Pageable novelIdSortingPageable;
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
//        Sort novelNameSort = Sort.by(Sort.Order.asc("novelName"));
//        Sort novelIdSort = Sort.by(Sort.Order.asc("novelId"));
//
//        pageable = PageRequest.of(page, size);
//        novelNameSortingPageable = PageRequest.of(page, size, novelNameSort);
//        novelIdSortingPageable = PageRequest.of(page, size, novelIdSort);
//
//        log.info("페이지 설정 완료");
//
//        log.info("테스트 DTO 생성");
//
//        Long novelId = 1L;
//        Long authorId = 1L;
//        Long tagId = 1L;
//        String novelName = "test";
//        String novelThumbNail = "url";
//        String novelExplanation = "테스트입니다";
//
//        novel = Novel.builder()
//                .novelId(novelId)
//                .author(Author.builder()
//                        .authorId(authorId)
//                        .build())
//                .tag(Tag.builder()
//                        .tagId(tagId)
//                        .build())
//                .novelName(novelName)
//                .novelThumbNail(novelThumbNail)
//                .novelExplanation(novelExplanation)
//                .build();
//
//        log.info("테스트 DTO 생성완료");
//    }
//
//    @Test
//    @DisplayName("랜덤 소설 검색 테스트 성공")
//    void testGetRandomNovels_성공() {
//        log.info("랜덤 소설 검색 테스트 시작");
//
//        // given
//        List<Novel> novelList = new ArrayList<>();
//        novelList.add(novel);
//
//        when(novelRepository.findThreeNovelsByRandom()).thenReturn(novelList);
//
//        // when
//        List<NovelDTO> novelDTOList = novelService.getRandomNovels();
//
//        // then
//        assertThat(novelDTOList)
//                .isNotEmpty();
//
//        log.info("랜덤 소설 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("랭킹 소설 검색 테스트 성공")
//    void testGetRankingNovels_성공() {
//        log.info("랭킹 소설 검색 테스트 시작");
//
//        // given
//        List<Novel> novelList = new ArrayList<>();
//        novelList.add(novel);
//
//        Page<Novel> novelPage = new PageImpl<>(novelList);
//
//        when(novelRepository.findAll(novelIdSortingPageable)).thenReturn(novelPage);
//
//        // when
//        List<NovelDTO> novelDTOList = novelService.getRankingNovels(page, size);
//
//        // then
//        assertThat(novelDTOList)
//                .isNotEmpty();
//
//        log.info("랭킹 소설 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("정렬된 소설 검색 테스트 성공")
//    void testGetSortingNovels_성공() {
//        log.info("정렬된 소설 검색 테스트 시작");
//
//        // given
//        List<Novel> novelList = new ArrayList<>();
//        novelList.add(novel);
//
//        Page<Novel> novelPage = new PageImpl<>(novelList);
//
//        when(novelRepository.findAll(novelNameSortingPageable)).thenReturn(novelPage);
//
//        // when
//        List<NovelDTO> novelDTOList = novelService.getSortingNovels(page, size);
//
//        // then
//        assertThat(novelDTOList)
//                .isNotEmpty();
//
//        log.info("정렬된 소설 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("소설 아이디로 소설 검색 테스트 성공")
//    void testGetNovelByNovelId_성공() {
//        log.info("소설 아이디로 소설 검색 테스트 시작");
//
//        // given
//        when(novelRepository.existsByNovelId(novel.getNovelId())).thenReturn(true);
//        when(novelRepository.findByNovelId(novel.getNovelId())).thenReturn(novel);
//
//        // when
//        NovelDTO novelDTO = novelService.getNovelByNovelId(novel.getNovelId());
//
//        // then
//        assertThat(novelDTO).isNotNull();
//
//        log.info("소설 아이디로 소설 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("소설 아이디로 소설 검색 테스트 실패")
//    void testGetNovelByNovelId_실패() {
//        log.info("소설 아이디로 소설 검색 테스트 시작");
//
//        // given
//        when(novelRepository.existsByNovelId(novel.getNovelId())).thenReturn(false);
//
//        // when
//
//        // then
//        assertThatThrownBy(() -> novelService.getNovelByNovelId(novel.getNovelId()))
//                .isInstanceOf(NotExistNovelException.class);
//
//        log.info("소설 아이디로 소설 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("소설 아이디 리스트로 소설 리스트 검색 테스트 성공")
//    void testGetNovelsByNovelIdList_성공() {
//        log.info("소설 아이디 리스트로 소설 리스트 검색 테스트 시작");
//
//        // given
//        List<Long> novelIdList = new ArrayList<>();
//        novelIdList.add(novel.getNovelId());
//
//        when(novelRepository.existsByNovelId(novel.getNovelId())).thenReturn(true);
//        when(novelRepository.findByNovelId(novel.getNovelId())).thenReturn(novel);
//
//        // when
//        List<NovelDTO> novelDTOList = novelService.getNovelsByNovelIdList(novelIdList);
//
//        // then
//        assertThat(novelDTOList)
//                .isNotEmpty();
//
//        log.info("소설 아이디 리스트로 소설 리스트 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("소설 아이디 리스트로 소설 리스트 검색 테스트 실패")
//    void testGetNovelsByNovelIdList_실패() {
//        log.info("소설 아이디 리스트로 소설 리스트 검색 테스트 시작");
//
//        // given
//        List<Long> novelIdList = new ArrayList<>();
//        novelIdList.add(novel.getNovelId());
//
//        when(novelRepository.existsByNovelId(novel.getNovelId())).thenReturn(false);
//
//        // when
//        List<NovelDTO> novelDTOList = novelService.getNovelsByNovelIdList(novelIdList);
//
//        // then
//        assertThat(novelDTOList)
//                .isNull();
//
//        log.info("소설 아이디 리스트로 소설 리스트 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("소설 이름으로 소설 리스트 검색 테스트 성공")
//    void testGetNovelsByNovelName_성공() {
//        log.info("소설 이름으로 소설 리스트 검색 테스트 시작");
//
//        // given
//        List<Novel> novelList = new ArrayList<>();
//        novelList.add(novel);
//
//        when(novelRepository.findByNovelNameContaining(novel.getNovelName(), novelIdSortingPageable)).thenReturn(novelList);
//
//        // when
//        List<NovelDTO> novelDTOList = novelService.getNovelsByNovelName(novel.getNovelName(), page, size);
//
//        // then
//        assertThat(novelDTOList).isNotNull();
//
//        log.info("소설 이름으로 소설 리스트 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("소설 이름으로 소설 리스트 검색 테스트 실패")
//    void testGetNovelsByNovelName_실패() {
//        log.info("소설 이름으로 소설 리스트 검색 테스트 시작");
//
//        // given
//        when(novelRepository.findByNovelNameContaining(novel.getNovelName(), novelIdSortingPageable)).thenReturn(new ArrayList<>());
//
//        // when
//
//        // then
//        assertThatThrownBy(() -> novelService.getNovelsByNovelName(novel.getNovelName(), page, size))
//                .isInstanceOf(NotExistNovelException.class);
//
//        log.info("소설 이름으로 소설 리스트 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("작가 아이디로 소설 리스트 검색 테스트 성공")
//    void testGetNovelsByAuthorId_성공() {
//        log.info("작가 아이디로 소설 리스트 검색 테스트 시작");
//
//        // given
//        List<Novel> novelList = new ArrayList<>();
//        novelList.add(novel);
//
//        when(novelRepository.findByAuthor_AuthorId(novel.getAuthor().getAuthorId(), pageable))
//                .thenReturn(novelList);
//
//        // when
//        List<NovelDTO> novelDTOList = novelService.getNovelsByAuthorId(novel.getAuthor().getAuthorId(), page, size);
//
//        // then
//        assertThat(novelDTOList)
//                .isNotEmpty();
//
//        log.info("작가 아이디로 소설 리스트 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("작가 아이디로 소설 리스트 검색 테스트 실패")
//    void testGetNovelsByAuthorId_실패() {
//        log.info("작가 아이디로 소설 리스트 검색 테스트 시작");
//
//        // given
//        List<Novel> novelList = new ArrayList<>();
//
//        when(novelRepository.findByAuthor_AuthorId(novel.getAuthor().getAuthorId(), pageable))
//                .thenReturn(novelList);
//
//        // when
//
//        // then
//        assertThatThrownBy(() -> novelService.getNovelsByAuthorId(novel.getAuthor().getAuthorId(), page, size))
//                .isInstanceOf(NotExistNovelException.class);
//
//        log.info("작가 아이디로 소설 리스트 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 성공")
//    void testGetNovelsByNovelContainingAndTagIdList_성공() {
//        log.info("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 시작");
//
//        // given
//        String novelName = "test";
//
//        List<Long> tagIdList = new ArrayList<>();
//        tagIdList.add(novel.getTag().getTagId());
//
//        List<Novel> novelList = new ArrayList<>();
//        novelList.add(novel);
//
//        when(novelRepository.findByNovelNameContainingAndTagIdList(novelName, tagIdList, novelIdSortingPageable)).thenReturn(novelList);
//
//        // when
//        List<NovelDTO> novelDTOList = novelService.getNovelsByNovelNameContainingAndTagIdList(novelName, tagIdList, page, size);
//
//        // then
//        assertThat(novelDTOList).isNotNull();
//
//        log.info("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 실패")
//    void testGetNovelsByNovelContainingAndTagIdList_실패() {
//        log.info("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 시작");
//
//        // given
//        String novelName = "test";
//
//        List<Long> tagIdList = new ArrayList<>();
//        tagIdList.add(novel.getTag().getTagId());
//
//        when(novelRepository.findByNovelNameContainingAndTagIdList(novelName, tagIdList, novelIdSortingPageable)).thenReturn(new ArrayList<>());
//
//        // when
//
//        // then
//        assertThatThrownBy(() -> novelService.getNovelsByNovelNameContainingAndTagIdList(novelName, tagIdList, page, size))
//                .isInstanceOf(NotExistNovelException.class);
//
//        log.info("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 종료");
//    }
//}
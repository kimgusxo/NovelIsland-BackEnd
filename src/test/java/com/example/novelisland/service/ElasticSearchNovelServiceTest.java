//package com.example.novelisland.service;
//
//import com.example.novelisland.document.ElasticSearchNovel;
//import com.example.novelisland.domain.Author;
//import com.example.novelisland.domain.Novel;
//import com.example.novelisland.domain.Tag;
//import com.example.novelisland.dto.NovelDTO;
//import com.example.novelisland.exception.novel.NotExistNovelException;
//import com.example.novelisland.repository.ElasticSearchNovelRepository;
//import com.example.novelisland.repository.NovelRepository;
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
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//import static org.mockito.Mockito.when;
//
//
//@Slf4j
//@ExtendWith(MockitoExtension.class)
//public class ElasticSearchNovelServiceTest {
//
//    @Mock
//    private ElasticSearchNovelRepository elasticSearchNovelRepository;
//    @Mock
//    private NovelRepository novelRepository;
//
//    @InjectMocks
//    private ElasticSearchNovelService elasticSearchNovelService;
//
//    private Pageable pageable;
//    private ElasticSearchNovel elasticSearchNovel;
//    private Novel novel;
//
//    private Integer page;
//    private Integer size;
//
//    @BeforeEach
//    void setUp() {
//        log.info("페이지 설정");
//
//        page = 0;
//        size = 9;
//
//        pageable = PageRequest.of(page, size);
//
//        log.info("페이지 설정 완료");
//
//        log.info("테스트 DTO 생성");
//
//        Long novelId = 1L;
//        Long authorId = 1L;
//        String authorName = "test";
//        Long tagId = 1L;
//        String novelName = "test";
//        String novelThumbNail = "url";
//        String novelExplanation = "테스트입니다";
//
//        elasticSearchNovel = ElasticSearchNovel.builder()
//                .novelId(novelId)
//                .novelName(novelName)
//                .novelExplanation(novelExplanation)
//                .build();
//
//        novel = Novel.builder()
//                    .novelId(novelId)
//                    .author(Author.builder()
//                            .authorId(authorId)
//                            .authorName(authorName)
//                            .build())
//                    .tag(Tag.builder()
//                            .tagId(tagId)
//                            .build())
//                    .novelName(novelName)
//                    .novelThumbNail(novelThumbNail)
//                    .novelExplanation(novelExplanation)
//                    .build();
//
//        log.info("테스트 DTO 생성 완료");
//
//    }
//
//    @Test
//    @DisplayName("문장형 검색어로 검색어와 유사한 소설 검색 테스트 성공")
//    void testGetElasticNovelsByNovelExplanation_성공() {
//        log.info("문장형 검색어로 검색어와 유사한 소설 검색 테스트 시작");
//
//        // given
//        List<ElasticSearchNovel> elasticSearchNovelList = new ArrayList<>();
//        elasticSearchNovelList.add(elasticSearchNovel);
//
//        when(elasticSearchNovelRepository.findElasticNovelsByNovelExplanation(novel.getNovelExplanation(), pageable)).thenReturn(elasticSearchNovelList);
//        when(novelRepository.findByNovelId(novel.getNovelId())).thenReturn(novel);
//
//        // when
//        List<NovelDTO> novelDTOList = elasticSearchNovelService.getElasticNovelsByNovelExplanation(novel.getNovelExplanation());
//
//        // then
//        assertThat(novelDTOList).isNotNull();
//
//        log.info("문장형 검색어로 검색어와 유사한 소설 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("문장형 검색어로 검색어와 유사한 소설 검색 테스트 실패")
//    void testGetElasticNovelsByNovelExplanation_실패() {
//        log.info("문장형 검색어로 검색어와 유사한 소설 검색 테스트 시작");
//
//        // given
//        when(elasticSearchNovelRepository.findElasticNovelsByNovelExplanation(novel.getNovelExplanation(), pageable)).thenReturn(new ArrayList<>());
//
//        // when
//
//        // then
//        assertThatThrownBy(() -> elasticSearchNovelService.getElasticNovelsByNovelExplanation(novel.getNovelExplanation()))
//                .isInstanceOf(NotExistNovelException.class);
//
//        log.info("문장형 검색어로 검색어와 유사한 소설 검색 테스트 종료");
//    }
//
//}

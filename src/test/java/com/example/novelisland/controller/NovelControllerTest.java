package com.example.novelisland.controller;

import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.dto.NovelDTO;
import com.example.novelisland.exception.novel.NotExistNovelException;
import com.example.novelisland.service.NovelService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(NovelController.class)
@AutoConfigureMockMvc(addFilters = false)
class NovelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NovelService novelService;

    private Integer page;
    private Integer size;

    @BeforeEach
    void setUp() {
        log.info("페이지 변수 설정");

        page = 0;
        size = 32;

        log.info("페이지 변수 설정");
    }

    @Test
    @DisplayName("랜덤 소설 검색 테스트 성공")
    void testRandomNovels_성공() throws Exception {
        log.info("랜덤 소설 검색 테스트 시작");

        // given
        given(novelService.getRandomNovels()).willReturn(new ArrayList<>());

        // when & then
        mockMvc.perform(get("/novel/get/random")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        log.info("랜덤 소설 검색 테스트 종료");
    }

    @Test
    @DisplayName("랭킹 소설 검색 테스트 성공")
    void testRankingNovels_성공() throws Exception {
        log.info("랭킹 소설 검색 테스트 시작");

        // given
        given(novelService.getRankingNovels(page, size)).willReturn(new ArrayList<>());

        // when & then
        mockMvc.perform(get("/novel/get/ranking")
                        .param("page", "0")
                        .param("size", "32")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        log.info("랭킹 소설 검색 테스트 종료");
    }

    @Test
    @DisplayName("정렬된 소설 검색 테스트 성공")
    void testSortingNovels_성공() throws Exception {
        log.info("정렬된 소설 검색 테스트 시작");

        // given
        given(novelService.getSortingNovels(page, size)).willReturn(new ArrayList<>());

        // when & then
        mockMvc.perform(get("/novel/get/sorting")
                        .param("page", "0")
                        .param("size", "32")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        log.info("정렬된 소설 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 아이디로 소설 검색 테스트 성공")
    void testGetNovelByNovelId_성공() throws Exception {
        log.info("소설 아이디로 소설 검색 테스트 시작");

        // given
        Long novelId = 1L;
        given(novelService.getNovelByNovelId(novelId)).willReturn(new NovelDTO());

        // when & then
        mockMvc.perform(get("/novel/find/novelId")
                    .param("novelId", "1")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        log.info("소설 아이디로 소설 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 아이디로 소설 검색 테스트_실패")
    void testGetNovelByNovelId_실패() throws Exception {
        log.info("소설 아이디로 소설 검색 테스트 시작");

        // given
        Long novelId = 1L;
        given(novelService.getNovelByNovelId(novelId)).willThrow(new NotExistNovelException(ErrorCode.NOT_EXIST_NOVEL_TOKEN));

         // when & then
        mockMvc.perform(get("/novel/find/novelId")
                        .param("novelId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        log.info("소설 아이디로 소설 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 아이디 리스트로 소설 리스트 검색 테스트 성공")
    void testGetNovelsByNovelIdList_성공() throws Exception {
        log.info("소설 아이디 리스트로 소설 리스트 검색 테스트 시작");

        // given
        Long novelId = 1L;

        List<Long> novelIdList = new ArrayList<>();
        novelIdList.add(novelId);

        given(novelService.getNovelsByNovelIdList(novelIdList)).willReturn(new ArrayList<>());

        // when & then
        mockMvc.perform(get("/novel/find/novelIdList")
                        .param("novelIdList", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        log.info("소설 아이디 리스트로 소설 리스트 검색 테스트 시작");
    }

    @Test
    @DisplayName("소설 아이디 리스트로 소설 리스트 검색 테스트 실패")
    void testGetNovelsByNovelIdList_실패() throws Exception {
        log.info("소설 아이디 리스트로 소설 리스트 검색 테스트 시작");

        // given
        Long novelId = 1L;

        List<Long> novelIdList = new ArrayList<>();
        novelIdList.add(novelId);

        given(novelService.getNovelsByNovelIdList(novelIdList)).willThrow(new NotExistNovelException(ErrorCode.NOT_EXIST_NOVEL_TOKEN));

        // when & then
        mockMvc.perform(get("/novel/find/novelIdList")
                        .param("novelIdList", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        log.info("소설 아이디 리스트로 소설 리스트 검색 테스트 시작");
    }

    @Test
    @DisplayName("소설 이름으로 소설 리스트 검색 테스트 성공")
    void testGetNovelsByNovelName_성공() throws Exception {
        log.info("소설 이름으로 소설 리스트 검색 테스트 시작");

        // given
        String novelName = "test";
        given(novelService.getNovelsByNovelName(novelName, page, size)).willReturn(new ArrayList<>());

        // when & then
        mockMvc.perform(get("/novel/find/novelName")
                        .param("novelName", "test")
                        .param("page", "0")
                        .param("size", "32")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        log.info("소설 이름으로 소설 리스트 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 이름으로 소설 리스트 검색 테스트 실패")
    void testGetNovelsByNovelName_실패() throws Exception {
        log.info("소설 이름으로 소설 리스트 검색 테스트 시작");

        // given
        String novelName = "test";
        given(novelService.getNovelsByNovelName(novelName, page, size)).willThrow(new NotExistNovelException(ErrorCode.NOT_EXIST_NOVEL_TOKEN));

        // when & then
        mockMvc.perform(get("/novel/find/novelName")
                        .param("novelName", "test")
                        .param("page", "0")
                        .param("size", "32")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        log.info("소설 이름으로 소설 리스트 검색 테스트 종료");
    }

    @Test
    @DisplayName("작가 아이디로 소설 리스트 검색 테스트 성공")
    void testGetNovelsByAuthorId_성공() throws Exception {
        log.info("작가 아이디로 소설 리스트 검색 테스트 시작");

        // given
        Long authorId = 1L;
        given(novelService.getNovelsByAuthorId(authorId, page, size)).willReturn(new ArrayList<>());

        // when & then
        mockMvc.perform(get("/novel/find/authorId")
                        .param("authorId", "1")
                        .param("page", "0")
                        .param("size", "32")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        log.info("작가 아이디로 소설 리스트 검색 테스트 종료");
    }

    @Test
    @DisplayName("작가 아이디로 소설 리스트 검색 테스트 실패")
    void testGetNovelsByAuthorId_실패() throws Exception {
        log.info("작가 아이디로 소설 리스트 검색 테스트 시작");

        // given
        Long authorId = 1L;
        given(novelService.getNovelsByAuthorId(authorId, page, size)).willThrow(new NotExistNovelException(ErrorCode.NOT_EXIST_NOVEL_TOKEN));

        // when & then
        mockMvc.perform(get("/novel/find/authorId")
                        .param("authorId", "1")
                        .param("page", "0")
                        .param("size", "32")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        log.info("작가 아이디로 소설 리스트 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 성공")
    void testGetNovelsByNovelNameContainingAndTagIdList_성공() throws Exception {
        log.info("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 시작");

        String novelName ="가";
        List<Long> tagIdList = new ArrayList<>();
        tagIdList.add(0L);

        // given
        given(novelService.getNovelsByNovelNameContainingAndTagIdList(novelName, tagIdList, page, size)).willReturn(new ArrayList<>());

        // when & then
        mockMvc.perform(get("/novel/find/novelName/and/tagId")
                        .param("novelName", "가")
                        .param("tagIdList", "0")
                        .param("page", "0")
                        .param("size", "32")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        log.info("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 실패")
    void testGetNovelsByNovelNameContainingAndTagIdList_실패() throws Exception {
        log.info("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 시작");

        String novelName ="가";
        List<Long> tagIdList = new ArrayList<>();
        tagIdList.add(0L);

        // given
        given(novelService.getNovelsByNovelNameContainingAndTagIdList(novelName, tagIdList, page, size))
                .willThrow(new NotExistNovelException(ErrorCode.NOT_EXIST_NOVEL_TOKEN));

        // when & then
        mockMvc.perform(get("/novel/find/novelName/and/tagId")
                        .param("novelName", "가")
                        .param("tagIdList", "0")
                        .param("page", "0")
                        .param("size", "32")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        log.info("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 종료");
    }
}
package com.example.novelisland.controller;

import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.dto.AuthorDTO;
import com.example.novelisland.exception.author.NotExistAuthorException;
import com.example.novelisland.service.AuthorService;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(AuthorController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    private Integer page;
    private Integer size;
    
    @BeforeEach
    void setUp() {
        log.info("페이지 변수 설정");
        
        page = 0;
        size = 32;
        
        log.info("페이지 변수 설정 완료");
    }
    
    @Test
    @DisplayName("정렬된 작가 검색 테스트 성공")
    void testGetSortingAuthor_성공() throws Exception {
        log.info("정렬된 작가 검색 테스트 시작");

        // given
        List<AuthorDTO> authorDTOList = new ArrayList<>();
        authorDTOList.add(new AuthorDTO());

        given(authorService.getSortingAuthor(page, size)).willReturn(authorDTOList);

        // when & then
        mockMvc.perform(get("/author/get/sorting")
                        .param("page", "0")
                        .param("size", "32")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        log.info("정렬된 작가 검색 테스트 종료");
    }

    @Test
    @DisplayName("정렬된 작가 검색 테스트 실패")
    void testGetSortingAuthor_실패() throws Exception {
        log.info("정렬된 작가 검색 테스트 시작");

        // given
        given(authorService.getSortingAuthor(page, size)).willThrow(new NotExistAuthorException(ErrorCode.NOT_EXIST_AUTHOR_TOKEN));

        // when & then
        mockMvc.perform(get("/author/get/sorting")
                        .param("page", "0")
                        .param("size", "32")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


        log.info("정렬된 작가 검색 테스트 종료");
    }


    @Test
    @DisplayName("작가 아이디로 작가 검색 테스트 성공")
    void testGetAuthorByAuthorId_성공() throws Exception {
        log.info("작가 아이디로 작가 검색 테스트 시작");

        // given
        Long authorId = 1L;
        given(authorService.getAuthorByAuthorId(authorId)).willReturn(new AuthorDTO());

        // when & then
        mockMvc.perform(get("/author/find/authorId")
                        .param("authorId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        log.info("작가 아이디로 작가 검색 테스트 종료");
    }

    @Test
    @DisplayName("작가 아이디로 작가 검색 테스트 실패")
    void testGetAuthorByAuthorId_실패() throws Exception {
        log.info("작가 아이디로 작가 검색 테스트 시작");

        // given
        Long authorId = 1L;
        given(authorService.getAuthorByAuthorId(authorId)).willThrow(new NotExistAuthorException(ErrorCode.NOT_EXIST_AUTHOR_TOKEN));

        // when & then
        mockMvc.perform(get("/author/find/authorId")
                        .param("authorId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        log.info("작가 아이디로 작가 검색 테스트 종료");
    }

    @Test
    @DisplayName("작가 이름으로 작가 검색 테스트 성공")
    void testGetAuthorByAuthorName_성공() throws Exception {
        log.info("작가 이름으로 작가 검색 테스트 시작");

        // given
        String authorName = "test";

        List<AuthorDTO> authorDTOList = new ArrayList<>();
        authorDTOList.add(new AuthorDTO());

        given(authorService.getAuthorByAuthorName(authorName, page, size)).willReturn(authorDTOList);

        // when & then
        mockMvc.perform(get("/author/find/authorName")
                        .param("authorName", "test")
                        .param("page", "0")
                        .param("size", "32")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        log.info("작가 이름으로 작가 검색 테스트 종료");
    }

    @Test
    @DisplayName("작가 이름으로 작가 검색 테스트 실패")
    void testGetAuthorByAuthorName_실패() throws Exception {
        log.info("작가 이름으로 작가 검색 테스트 시작");

        // given
        String authorName = "test";
        given(authorService.getAuthorByAuthorName(authorName, page, size)).willThrow(new NotExistAuthorException(ErrorCode.NOT_EXIST_AUTHOR_TOKEN));

        // when & then
        mockMvc.perform(get("/author/find/authorName")
                        .param("authorName", "test")
                        .param("page", "0")
                        .param("size", "32")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        log.info("작가 이름으로 작가 검색 테스트 종료");
    }

}
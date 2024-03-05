//package com.example.novelisland.controller;
//
//import com.example.novelisland.description.ErrorCode;
//import com.example.novelisland.exception.novel.NotExistNovelException;
//import com.example.novelisland.service.ElasticSearchNovelService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//
//import static org.mockito.BDDMockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@Slf4j
//@WebMvcTest(ElasticSearchNovelController.class)
//@AutoConfigureMockMvc(addFilters = false)
//public class ElasticSearchNovelControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ElasticSearchNovelService elasticSearchNovelService;
//
//
//    @Test
//    @DisplayName("문장형 검색어로 검색어와 유사한 소설 검색 테스트 성공")
//    void testGetElasticNovelsByNovelExplanation_성공() throws Exception {
//        log.info("문장형 검색어로 검색어와 유사한 소설 검색 테스트 시작");
//
//        // given
//        String novelExplanation = "테스트";
//        given(elasticSearchNovelService.getElasticNovelsByNovelExplanation(novelExplanation)).willReturn(new ArrayList<>());
//
//        // when & then
//        mockMvc.perform(get("/elastic/find/result")
//                        .param("novelExplanation", "테스트")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        log.info("문장형 검색어로 검색어와 유사한 소설 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("문장형 검색어로 검색어와 유사한 소설 검색 테스트 실패")
//    void testGetElasticNovelsByNovelExplanation_실패() throws Exception {
//        log.info("문장형 검색어로 검색어와 유사한 소설 검색 테스트 시작");
//
//        // given
//        String novelExplanation = "예외";
//        given(elasticSearchNovelService.getElasticNovelsByNovelExplanation(novelExplanation)).willThrow(new NotExistNovelException(ErrorCode.NOT_EXIST_NOVEL_TOKEN));
//
//        // when & then
//        mockMvc.perform(get("/elastic/find/result")
//                .param("novelExplanation", "예외")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//
//        log.info("문장형 검색어로 검색어와 유사한 소설 검색 테스트 종료");
//    }
//
//}

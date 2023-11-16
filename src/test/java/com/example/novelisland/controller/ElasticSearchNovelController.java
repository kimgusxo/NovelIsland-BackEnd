package com.example.novelisland.controller;

import com.example.novelisland.service.ElasticSearchNovelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@WebMvcTest(BookMarkController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ElasticSearchNovelController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ElasticSearchNovelService elasticSearchNovelService;

}

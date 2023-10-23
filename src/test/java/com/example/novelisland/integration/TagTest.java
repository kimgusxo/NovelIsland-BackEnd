package com.example.novelisland.integration;

import com.example.novelisland.format.Message;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TagTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Integer page;
    private Integer size;

    @BeforeEach
    void setUp() {
        log.info("페이지 변수 설정");

        page = 0;
        size = 10;

        log.info("페이지 변수 설정완료");
    }

    @Test
    @DisplayName("태그 아이디로 소설 리스트 검색 테스트")
    void testGetNovelsByTagId() {
        log.info("태그 아이디로 소설 리스트 검색 테스트 시작");

        Long tagId = 4764L;

        ResponseEntity<Message> response = restTemplate.getForEntity(
                createURLWithPort("/tag/find/tagId?tagId=" + tagId + "&page=" + page + "&size=" + size),
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("태그 아이디로 소설 리스트 검색 테스트 시작");
    }

    @Test
    @DisplayName("태그 이름으로 소설 리스트 검색 테스트")
    void testGetNovelsByTagName() {
        log.info("태그 이름으로 소설 리스트 검색 테스트 시작");

        String tagClassification = "판타지";

        ResponseEntity<Message> response = restTemplate.getForEntity(
                createURLWithPort("/tag/find/tagClassification?tagClassification=" + tagClassification + "&page=" + page + "&size=" + size),
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("태그 이름으로 소설 리스트 검색 테스트 시작");
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}

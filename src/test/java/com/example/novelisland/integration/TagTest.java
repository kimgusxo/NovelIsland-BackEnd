//package com.example.novelisland.integration;
//
//import com.example.novelisland.format.ErrorMessage;
//import com.example.novelisland.format.Message;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@Slf4j
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class TagTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    private Integer page;
//    private Integer size;
//
//    @BeforeEach
//    void setUp() {
//        log.info("페이지 변수 설정");
//
//        page = 0;
//        size = 32;
//
//        log.info("페이지 변수 설정완료");
//    }
//
//    @Test
//    @DisplayName("정렬된 태그 리스트 검색 테스트 성공")
//    void testGetSortingTags_성공() {
//        log.info("정렬된 태그 리스트 검색 테스트 시작");
//
//        ResponseEntity<Message> response = restTemplate.getForEntity(
//                createURLWithPort("/tag/get/sorting"),
//                Message.class
//        );
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        Message message = response.getBody();
//
//        assert message != null;
//        System.out.println(message.getMessage());
//
//        log.info("정렬된 태그 리스트 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("태그 아이디로 태그 검색 테스트 성공")
//    void testGetTagByTagId_성공() {
//        log.info("태그 아이디로 태그 검색 테스트 시작");
//
//        Long tagId = 17879L;
//
//        ResponseEntity<Message> response = restTemplate.getForEntity(
//                createURLWithPort("/tag/find/tagId?tagId=" + tagId),
//                Message.class
//        );
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        Message message = response.getBody();
//
//        assert message != null;
//        System.out.println(message.getMessage());
//
//        log.info("태그 아이디로 태그 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("태그 아이디로 태그 검색 테스트 실패")
//    void testGetTagByTagId_실패() {
//        log.info("태그 아이디로 태그 검색 테스트 시작");
//
//        Long tagId = 1L;
//
//        ResponseEntity<ErrorMessage> response = restTemplate.getForEntity(
//                createURLWithPort("/tag/find/tagId?tagId=" + tagId),
//                ErrorMessage.class
//        );
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//
//        ErrorMessage errorMessage = response.getBody();
//
//        assert errorMessage != null;
//        System.out.println(errorMessage.getMessage());
//
//        log.info("태그 아이디로 태그 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("태그 아이디로 소설 리스트 검색 테스트 성공")
//    void testGetNovelsByTagId_성공() {
//        log.info("태그 아이디로 소설 리스트 검색 테스트 시작");
//
//        Long tagId = 17879L;
//
//        ResponseEntity<Message> response = restTemplate.getForEntity(
//                createURLWithPort("/tag/find/tagId?tagId=" + tagId + "&page=" + page + "&size=" + size),
//                Message.class
//        );
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        Message message = response.getBody();
//
//        assert message != null;
//        System.out.println(message.getMessage());
//
//        log.info("태그 아이디로 소설 리스트 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("태그 아이디로 소설 리스트 검색 테스트 실패")
//    void testGetNovelsByTagId_실패() {
//        log.info("태그 아이디로 소설 리스트 검색 테스트 시작");
//
//        Long tagId = 1L;
//
//        ResponseEntity<Message> response = restTemplate.getForEntity(
//                createURLWithPort("/tag/find/tagId?tagId=" + tagId + "&page=" + page + "&size=" + size),
//                Message.class
//        );
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//
//        Message message = response.getBody();
//
//        assert message != null;
//        System.out.println(message.getMessage());
//
//        log.info("태그 아이디로 소설 리스트 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("태그 이름으로 소설 리스트 검색 테스트 성공")
//    void testGetNovelsByTagName_성공() {
//        log.info("태그 이름으로 소설 리스트 검색 테스트 시작");
//
//        String tagClassification = "판타지";
//
//        ResponseEntity<Message> response = restTemplate.getForEntity(
//                createURLWithPort("/tag/find/tagClassification/novels?tagClassification=" + tagClassification + "&page=" + page + "&size=" + size),
//                Message.class
//        );
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        Message message = response.getBody();
//
//        assert message != null;
//        System.out.println(message.getMessage());
//
//        log.info("태그 이름으로 소설 리스트 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("태그 이름으로 소설 리스트 검색 테스트 실패")
//    void testGetNovelsByTagName_실패() {
//        log.info("태그 이름으로 소설 리스트 검색 테스트 시작");
//
//        String tagClassification = "틀린태그";
//
//        ResponseEntity<ErrorMessage> response = restTemplate.getForEntity(
//                createURLWithPort("/tag/find/tagClassification/novels?tagClassification=" + tagClassification + "&page=" + page + "&size=" + size),
//                ErrorMessage.class
//        );
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//
//        ErrorMessage errorMessage = response.getBody();
//
//        assert errorMessage != null;
//        System.out.println(errorMessage.getMessage());
//
//        log.info("태그 이름으로 소설 리스트 검색 테스트 종료");
//    }
//
//    private String createURLWithPort(String uri) {
//        return "http://localhost:" + port + uri;
//    }
//}

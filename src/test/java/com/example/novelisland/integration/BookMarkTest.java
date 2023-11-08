//package com.example.novelisland.integration;
//
//import com.example.novelisland.dto.LoginDTO;
//import com.example.novelisland.format.ErrorMessage;
//import com.example.novelisland.format.Message;
//import lombok.extern.slf4j.Slf4j;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.*;
//
//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
//
//@Slf4j
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class BookMarkTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    private String jwt;
//    private Integer page;
//    private Integer size;
//
//    @BeforeEach
//    void setUp() throws JSONException {
//        log.info("페이지 변수 설정");
//
//        page = 0;
//        size = 10;
//
//        log.info("페이지 변수 설정완료");
//
//        log.info("jwt 설정 완료");
//
//        LoginDTO loginDTO = new LoginDTO("Test1", "123");
//
//        ResponseEntity<Message> signInResponse = restTemplate.postForEntity(
//                createURLWithPort("/login/signIn"),
//                loginDTO,
//                Message.class
//        );
//
//        Message message = signInResponse.getBody();
//
//        assert message != null;
//        Object data = message.getData();
//
//        JSONObject jsonObject = new JSONObject(data.toString());
//        jwt = jsonObject.getString("jwtToken");
//
//        log.info("jwt 설정 완료");
//    }
//
//    @Test
//    @DisplayName("유저의 북마크 검색 테스트 성공")
//    void testGetBookMarkListByUserIndex_성공() {
//        log.info("유저의 북마크 검색 테스트 시작");
//
//        Long userIndex = 80327L;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + jwt);
//        HttpEntity<String> entity = new HttpEntity<>(null, headers);
//
//        ResponseEntity<Message> response = restTemplate.exchange(
//                createURLWithPort("/bookmark/find/userIndex?userIndex=" + userIndex + "&page=" + page + "&size=" + size),
//                HttpMethod.GET,
//                entity,
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
//        log.info("유저의 북마크 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("유저의 북마크 검색 테스트 실패")
//    void testGetBookMarkListByUserIndex_실패() {
//        log.info("유저의 북마크 검색 테스트 시작");
//
//        Long userIndex = 1L;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + jwt);
//        HttpEntity<String> entity = new HttpEntity<>(null, headers);
//
//        ResponseEntity<Message> response = restTemplate.exchange(
//                createURLWithPort("/bookmark/find/userIndex?userIndex=" + userIndex + "&page=" + page + "&size=" + size),
//                HttpMethod.GET,
//                entity,
//                Message.class
//        );
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        Message message = response.getBody();
//
//        assert message != null;
//        assertThat(message.getData())
//                .isNull();
//
//        log.info("유저의 북마크 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("유저의 북마크 등록 테스트 성공")
//    void testCreateBookMarkByBookMarkId_성공() {
//        log.info("유저의 북마크 등록 테스트 시작");
//
//        Long userIndex = 80327L;
//        Long novelId = 17888L;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + jwt);
//        HttpEntity<String> entity = new HttpEntity<>(null, headers);
//
//        ResponseEntity<Message> response = restTemplate.exchange(
//                createURLWithPort("/bookmark/create/userIndex/and/novelId?userIndex=" + userIndex + "&novelId=" + novelId),
//                HttpMethod.POST,
//                entity,
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
//        log.info("유저의 북마크 등록 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("유저의 북마크 등록 테스트 실패")
//    void testCreateBookMarkByBookMarkId_실패() {
//        log.info("유저의 북마크 등록 테스트 시작");
//
//        Long userIndex = 80327L;
//        Long novelId = 17888L;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + jwt);
//        HttpEntity<String> entity = new HttpEntity<>(null, headers);
//
//        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
//                createURLWithPort("/bookmark/create/userIndex/and/novelId?userIndex=" + userIndex + "&novelId=" + novelId),
//                HttpMethod.POST,
//                entity,
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
//        log.info("유저의 북마크 등록 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("유저의 북마크 해제 테스트 성공")
//    void testDeleteBookMarkByBookMarkId_성공() {
//        log.info("유저의 북마크 해제 테스트 시작");
//
//        Long bookMarkId = 80332L;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + jwt);
//        HttpEntity<String> entity = new HttpEntity<>(null, headers);
//
//        ResponseEntity<Message> response = restTemplate.exchange(
//                createURLWithPort("/bookmark/delete/bookMarkId?bookMarkId=" + bookMarkId),
//                HttpMethod.DELETE,
//                entity,
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
//        log.info("유저의 북마크 해제 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("유저의 북마크 해제 테스트 실패")
//    void testDeleteBookMarkByBookMarkId_실패() {
//        log.info("유저의 북마크 해제 테스트 시작");
//
//        Long bookMarkId = 1L;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + jwt);
//        HttpEntity<String> entity = new HttpEntity<>(null, headers);
//
//        ResponseEntity<ErrorMessage> response = restTemplate.exchange(
//                createURLWithPort("/bookmark/delete/bookMarkId?bookMarkId=" + bookMarkId),
//                HttpMethod.DELETE,
//                entity,
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
//        log.info("유저의 북마크 해제 테스트 종료");
//    }
//
//    private String createURLWithPort(String uri) {
//        return "http://localhost:" + port + uri;
//    }
//}

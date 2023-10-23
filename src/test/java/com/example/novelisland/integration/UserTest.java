package com.example.novelisland.integration;

import com.example.novelisland.dto.LoginDTO;
import com.example.novelisland.dto.UserDTO;
import com.example.novelisland.format.Message;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String jwt;

    @BeforeEach
    void setUp() throws JSONException {
        log.info("jwt 설정 완료");

        LoginDTO loginDTO = new LoginDTO("Test1", "123");

        ResponseEntity<Message> signInResponse = restTemplate.postForEntity(
                createURLWithPort("/login/signIn"),
                loginDTO,
                Message.class
        );

        Message message = signInResponse.getBody();

        assert message != null;
        Object data = message.getData();

        JSONObject jsonObject = new JSONObject(data.toString());
        jwt = jsonObject.getString("jwtToken");

        log.info("jwt 설정 완료");
    }

    @Test
    @DisplayName("유저 업데이트 테스트 성공")
    void testUpdateUser_성공() {
        log.info("유저 업데이트 테스트 시작");

        UserDTO userDTO = new UserDTO(16945L, "Test2", "updatePassword");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);


        ResponseEntity<Message> response = restTemplate.exchange(
                createURLWithPort("/user/update"),
                HttpMethod.PUT,
                new HttpEntity<>(userDTO),
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("유저 업데이트 테스트 종료");
    }
    
    @Test
    @DisplayName("유저 업데이트 테스트 실패")
    void testUpdateUser_실패() {
        log.info("유저 업데이트 테스트 시작");

        UserDTO userDTO = new UserDTO(1L, "Test2", "updatePassword");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);


        ResponseEntity<Message> response = restTemplate.exchange(
                createURLWithPort("/user/update"),
                HttpMethod.PUT,
                new HttpEntity<>(userDTO),
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("유저 업데이트 테스트 종료");
    }

    @Test
    @DisplayName("유저 삭제 테스트 성공")
    void testDeleteUser_성공() {
        log.info("유저 삭제 테스트 시작");

        Long userIndex = 16978L;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);


        ResponseEntity<Message> response = restTemplate.exchange(
                createURLWithPort("/user/delete?userIndex=" + userIndex),
                HttpMethod.DELETE,
                null,
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("유저 삭제 테스트 종료");
    }

    @Test
    @DisplayName("유저 삭제 테스트 실패")
    void testDeleteUser_실패() {
        log.info("유저 삭제 테스트 시작");

        Long userIndex = 1L;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);


        ResponseEntity<Message> response = restTemplate.exchange(
                createURLWithPort("/user/delete?userIndex=" + userIndex),
                HttpMethod.DELETE,
                null,
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("유저 삭제 테스트 종료");
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}

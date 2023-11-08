//package com.example.novelisland.integration;
//import com.example.novelisland.dto.LoginDTO;
//import com.example.novelisland.format.ErrorMessage;
//import com.example.novelisland.format.Message;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Slf4j
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class LoginTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    @DisplayName("중복 사용자가 있는지 체크 테스트 성공")
//    void testDuplicateCheck_성공() {
//        log.info("중복 사용자가 있는지 체크 테스트 시작");
//
//        String userId = "test";
//
//        ResponseEntity<Message> response = restTemplate.getForEntity(
//                createURLWithPort("/login/duplicateCheck?userId=" + userId),
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
//        log.info("중복 사용자가 있는지 체크 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("새로운 사용자로 회원가입 테스트")
//    void testSignUp_새로운사용자() {
//        log.info("새로운 사용자로 회원가입 테스트 시작");
//
//        LoginDTO loginDTO = new LoginDTO("testUser", "password123");
//
//        ResponseEntity<Message> response = restTemplate.postForEntity(
//                createURLWithPort("/login/signUp"),
//                loginDTO,
//                Message.class
//        );
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        Message message = response.getBody();
//
//        assertThat(message).isNotNull();
//        assertThat(message.getData()).isNotNull();
//
//        System.out.println(message.getData());
//
//        log.info("새로운 사용자로 회원가입 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("중복 사용자로 회원가입 테스트")
//    void testSignUp_중복사용자() {
//        log.info("중복 사용자로 회원가입 테스트 시작");
//
//        LoginDTO loginDTO = new LoginDTO("testUser", "password123");
//
//        // 중복 회원가입 시도
//        ResponseEntity<ErrorMessage> response2 = restTemplate.postForEntity(
//                createURLWithPort("/login/signUp"),
//                loginDTO,
//                ErrorMessage.class
//        );
//
//        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//
//        ErrorMessage errorMessage = response2.getBody();
//
//        assert errorMessage != null;
//        System.out.println(errorMessage.getMessage());
//
//        log.info("중복 사용자로 회원가입 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("로그인 테스트 성공")
//    void testSignIn_성공() {
//        log.info("로그인 테스트 시작");
//
//        LoginDTO loginDTO = new LoginDTO("testUser", "password123");
//
//        // 로그인 시도
//        ResponseEntity<Message> signInResponse = restTemplate.postForEntity(
//                createURLWithPort("/login/signIn"),
//                loginDTO,
//                Message.class
//        );
//
//        assertThat(signInResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        Message message = signInResponse.getBody();
//
//        assertThat(message).isNotNull();
//        assertThat(message.getData()).isNotNull();
//
//        System.out.println(message.getData());
//
//        log.info("로그인 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("잘못된 아이디로 로그인 테스트")
//    void testSignIn_잘못된아이디() {
//        log.info("잘못된 아이디로 로그인 테스트 시작");
//
//        LoginDTO loginDTO = new LoginDTO("nonExistentUser", "password123");
//
//        ResponseEntity<ErrorMessage> response = restTemplate.postForEntity(
//                createURLWithPort("/login/signIn"),
//                loginDTO,
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
//        log.info("잘못된 아이디로 로그인 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("잘못된 비밀번호로 로그인 테스트")
//    void testSignIn_잘못된비밀번호() {
//        log.info("잘못된 비밀번호로 로그인 테스트 시작");
//
//        LoginDTO loginDTO = new LoginDTO("testUser", "wrongPassword");
//
//        // 잘못된 비밀번호로 로그인 시도
//        ResponseEntity<ErrorMessage> response = restTemplate.postForEntity(
//                createURLWithPort("/login/signIn"),
//                loginDTO,
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
//        log.info("잘못된 비밀번호로 로그인 테스트 종료");
//    }
//
//    private String createURLWithPort(String uri) {
//        return "http://localhost:" + port + uri;
//    }
//}

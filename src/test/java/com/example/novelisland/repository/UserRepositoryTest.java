package com.example.novelisland.repository;

import com.example.novelisland.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    private User user;

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        log.info("테스트 유저 생성중");

        user = User.builder()
                .userId("Test1")
                .userPassword("123")
                .build();

        log.info("테스트 유저 생성완료");
    }

    @Test
    @DisplayName("유저 생성 테스트")
    void testCreateUser() {
        log.info("회원가입 테스트 시작");
        
        // given

        // when
        User savedUser = userRepository.save(user);

        // then
        assertThat(savedUser)
                .as("아이디가 일치하지 않습니다.")
                .hasFieldOrPropertyWithValue("userId", user.getUserId())
                .as("비밀번호가 일치하지 않습니다.")
                .hasFieldOrPropertyWithValue("userPassword", user.getUserPassword());

        log.info("회원가입 테스트 종료");
    }

    @Test
    @DisplayName("유저 아이디로 유저의 존재여부 확인 테스트")
    void testExistsByUserId() {
        log.info("유저 아이디로 유저의 존재여부 확인 테스트 시작");

        // given

        // when
        Boolean token = userRepository.existsByUserId(user.getUserId());

        // then
        assertThat(token)
                .as("유저가 존재하지 않습니다.")
                .isTrue();

        log.info("유저 아이디로 유저의 존재여부 확인 테스트 종료");
    }

    @Test
    @DisplayName("유저 아이디로 유저 검색 테스트")
    void testFindUserById() {
        log.info("유저 아이디로 유저 검색 테스트 시작");

        // given

        // when
        User findUser = userRepository.findByUserId(user.getUserId());

        // then
        assertThat(findUser)
                .as("유저가 존재하지 않습니다.")
                .isNotNull();

        System.out.println("유저 인덱스: " + findUser.getUserIndex());
        System.out.println("유저 아이디: " + findUser.getUserId());
        System.out.println("유저 비밀번호: " + findUser.getUserPassword());

        log.info("유저 아이디로 유저 검색 테스트 종료");
    }

    @Test
    @DisplayName("유저 아이디로 유저 삭제 테스트")
    void testDeleteUser() {
        log.info("유저 아이디로 유저 삭제 테스트 시작");

        // given
        Long userIndex = 16944L;

        // when
        userRepository.deleteById(userIndex);

        // then
        List<User> userList = userRepository.findAll();

        userList.forEach(u ->
                System.out.println(
                        "유저 번호: " + u.getUserIndex() + "\n" +
                                "유저 아이디: " + u.getUserId() + "\n" +
                                "유저 비밀번호: " + u.getUserPassword() + "\n"
                ));

        log.info("유저 아이디로 유저 삭제 테스트 종료");
    }

    @Test
    @DisplayName("유저 정보 업데이트 테스트")
    void testUpdateUser() {
        log.info("유저 정보 업데이트 테스트 시작");

        // given
        Long userIndex = 16945L;
        String updateUserPassword = "1234";

        user.setUserIndex(userIndex);
        user.setUserPassword(updateUserPassword);

        // when
        User updatedUser = userRepository.save(user);

        // then

        assertThat(updatedUser.getUserPassword())
                .as("비밀번호 변경에 실패하였습니다.")
                .isEqualTo(updateUserPassword);

        log.info("유저 정보 업데이트 테스트 종료");
    }

    @Test
    @DisplayName("유저 리스트 가져오기(관리자) 테스트")
    void testFindAll() {
        log.info("유저 리스트 가져오기 테스트 시작");

        // given

        // when
        List<User> userList = userRepository.findAll();

        // then
        assertThat(userList)
                .as("유저가 존재하지 않습니다.")
                .isNotEmpty();

        userList.forEach(u ->
                System.out.println(
                        "유저 번호: " + u.getUserIndex() + "\n" +
                        "유저 아이디: " + u.getUserId() + "\n" +
                        "유저 비밀번호: " + u.getUserPassword() + "\n"
                ));

        log.info("유저 리스트 가져오기 테스트 종료");
    }

}

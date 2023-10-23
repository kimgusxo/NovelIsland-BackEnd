package com.example.novelisland.service;

import com.example.novelisland.domain.User;
import com.example.novelisland.dto.UserDTO;
import com.example.novelisland.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        log.info("테스트 DTO 생성");
        
        Long userIndex = 1L;
        userDTO = UserDTO.builder()
                .userIndex(userIndex)
                .userId("test1")
                .userPassword("123")
                .build();
        
        log.info("테스트 DTO 생성완료");
    }

    @Test
    @DisplayName("유저 정보 수정 테스트")
    void testUpdateUser() {
        log.info("유저 정보 수정 테스트 시작");

        // given
        when(userRepository.existsByUserIndex(userDTO.getUserIndex())).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // when
        userDTO = userService.updateUser(userDTO);

        // then
        assertThat(userDTO).isNotNull();
        
        log.info("유저 정보 수정 테스트 종료");
    }

    @Test
    @DisplayName("유저 삭제 테스트")
    void testDeleteUser() {
        log.info("유저 삭제 테스트 시작");

        // given
        when(userRepository.existsByUserIndex(userDTO.getUserIndex())).thenReturn(true); // 해당 유저가 존재한다고 가정
        doNothing().when(userRepository).deleteById(userDTO.getUserIndex()); // deleteUser 메서드가 아무 동작도 수행하지 않는다고 가정

        // when
        userService.deleteUser(userDTO.getUserIndex());

        // then
        verify(userRepository).existsByUserIndex(userDTO.getUserIndex()); // existsByUserIndex 메서드가 호출되었는지 검증
        verify(userRepository).deleteById(userDTO.getUserIndex()); // deleteUser 메서드가 호출되었는지 검증

        log.info("유저 삭제 테스트 종료");
    }

}
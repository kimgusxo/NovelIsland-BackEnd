package com.example.novelisland.controller;

import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.dto.UserDTO;
import com.example.novelisland.exception.user.NotExistUserException;
import com.example.novelisland.service.BookMarkService;
import com.example.novelisland.service.UserService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        log.info("테스트 DTO 생성");
        
        userDTO = UserDTO.builder()
                .userIndex(1L)
                .userId("test")
                .userPassword("abc")
                .build();

        log.info("테스트 DTO 생성완료");
    }

    @Test
    @DisplayName("유저 업데이트 테스트 성공")
    void testUpdateUser_성공() throws Exception {
        log.info("유저 업데이트 테스트 시작");

        // given
        given(userService.updateUser(userDTO)).willReturn(userDTO);

        // when & then
        mockMvc.perform(put("/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userIndex\": \"1\", \"userId\": \"test\", \"userPassword\":  \"123\"}"))
                        .andExpect(status().isOk());
        
        log.info("유저 업데이트 테스트 종료");
    }

    @Test
    @DisplayName("유저 업데이트 테스트 실패")
    void testUpdateUser_실패() throws Exception {
        log.info("유저 업데이트 테스트 시작");

        // given
        given(userService.updateUser(any(UserDTO.class))).willThrow(new NotExistUserException(ErrorCode.NOT_EXIST_USER_TOKEN));

        // when & then
        mockMvc.perform(put("/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userIndex\": \"1\", \"userId\": \"test\", \"userPassword\":  \"123\"}"))
                .andExpect(status().isBadRequest());

        log.info("유저 업데이트 테스트 종료");
    }

    @Test
    @DisplayName("유저 삭제 테스트 성공")
    void testDeleteUser_성공() throws Exception {
        log.info("유저 삭제 테스트 시작");

        // given
        willDoNothing().given(userService).deleteUser(userDTO.getUserIndex());

        // when & then
        mockMvc.perform(delete("/user/delete")
                .param("userIndex", "1")
                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        log.info("유저 삭제 테스트 종료");
    }

    @Test
    @DisplayName("유저 삭제 테스트 실패")
    void testDeleteUser_실패() throws Exception {
        log.info("유저 삭제 테스트 시작");

        // given
        willThrow(new NotExistUserException(ErrorCode.NOT_EXIST_USER_TOKEN)).given(userService).deleteUser(userDTO.getUserIndex());

        // when & then
        mockMvc.perform(delete("/user/delete")
                        .param("userIndex", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        log.info("유저 삭제 테스트 종료");
    }
}
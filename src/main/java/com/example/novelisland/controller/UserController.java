package com.example.novelisland.controller;

import com.example.novelisland.dto.UserDTO;
import com.example.novelisland.format.Message;
import com.example.novelisland.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PutMapping
    @ApiOperation(value = "유저정보 수정", notes = "유저의 정보를 수정한다.")
    public ResponseEntity<Message<?>> updateUser(@RequestBody @Valid UserDTO userDTO) {
        log.info("[PUT /users] 회원정보 수정 요청 - userDTO={}", userDTO);

        return ResponseEntity.ok(
                Message.success(
                        "회원정보 수정 완료",
                        userService.updateUser(userDTO)
                )
        );
    }

    @DeleteMapping("/{userIndex}")
    @ApiOperation(value = "유저정보 삭제", notes = "유저의 정보를 삭제한다.")
    public ResponseEntity<Message<?>> deleteUser(@PathVariable("userIndex") Long userIndex) {
        log.info("[DELETE /users/{}] 회원정보 삭제 요청 - userIndex={}", userIndex, userIndex);

        userService.deleteUser(userIndex);

        return ResponseEntity.ok(
                Message.success("회원정보 삭제 완료")
        );
    }
}

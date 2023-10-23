package com.example.novelisland.controller;

import com.example.novelisland.dto.UserDTO;
import com.example.novelisland.format.Message;
import com.example.novelisland.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/update")
    @ApiOperation(value = "유저정보 수정", notes = "유저의 정보를 수정한다.")
    public ResponseEntity<Message> updateUser(@RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>(Message.of("회원정보 수정 완료", HttpStatus.OK.value(), userService.updateUser(userDTO)), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "유저정보 삭제", notes = "유저의 정보를 삭제한다.")
    public ResponseEntity<Message> deleteUser(@RequestParam("userIndex") Long userIndex) {
        userService.deleteUser(userIndex);
        return new ResponseEntity<>(Message.of("회원정보 삭제 완료", HttpStatus.OK.value()), HttpStatus.OK);
    }
}

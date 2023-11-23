package com.example.novelisland.service;

import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.domain.User;
import com.example.novelisland.dto.UserDTO;
import com.example.novelisland.exception.user.NotExistUserException;
import com.example.novelisland.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDTO updateUser(UserDTO userDTO) {
        Boolean token = userRepository.existsByUserIndex(userDTO.getUserIndex());

        if(token) {
            // 업데이트 시 리프레쉬 토큰 가져오기
            String refreshToken = userRepository.findByUserIndex(userDTO.getUserIndex()).getRefreshToken();

            User user = userDTO.toEntity();
            user.setRefreshToken(refreshToken);

            user = userRepository.save(user);

            return user.toDTO();
        } else {
            // 유저가 존재하지 않을 때 예외처리
            throw new NotExistUserException(ErrorCode.NOT_EXIST_USER_TOKEN);
        }
    }

    @Transactional
    public void deleteUser(Long userIndex) {
        Boolean token = userRepository.existsByUserIndex(userIndex);

        if(token) {
            userRepository.deleteById(userIndex);
        } else {
            // 유저가 존재하지 않을 때 예외처리
            throw new NotExistUserException(ErrorCode.NOT_EXIST_USER_TOKEN);
        }
    }
}

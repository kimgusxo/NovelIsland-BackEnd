package com.example.novelisland.domain;

import com.example.novelisland.dto.UserDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_TB")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "USER_INDEX")
    private Long userIndex;

    @NotNull
    @Column(name = "USER_ID")
    private String userId;

    @NotNull
    @Column(name = "USER_PASSWORD")
    private String userPassword;

    @OneToMany(mappedBy = "user")
    private List<BookMark> bookMarkList;

    public User(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public UserDTO toDTO() {
        return UserDTO.builder()
                .userIndex(userIndex)
                .userId(userId)
                .userPassword(userPassword)
                .build();
    }

}

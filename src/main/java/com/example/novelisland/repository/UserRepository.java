package com.example.novelisland.repository;

import com.example.novelisland.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUserIndex(Long userIndex);
    Boolean existsByUserId(String userId);
    User findByUserIndex(Long userIndex);
    User findByUserId(String userId);
}

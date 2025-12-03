package com.example.novelisland.repository;

import com.example.novelisland.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserId(String userId);

    Optional<User> findByUserIndex(Long userIndex);
    Optional<User> findByUserId(String userId);
}

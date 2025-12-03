package com.example.novelisland.repository;

import com.example.novelisland.domain.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    boolean existsByAuthorName(String authorName);

    Optional<Author> findByAuthorId(Long authorId);
    Optional<Author> findByAuthorName(String authorName);

    List<Author> findByAuthorNameContaining(String authorName, Pageable pageable);
}

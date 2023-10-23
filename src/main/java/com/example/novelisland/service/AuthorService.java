package com.example.novelisland.service;

import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.domain.Author;
import com.example.novelisland.dto.AuthorDTO;
import com.example.novelisland.exception.author.NotExistAuthorException;
import com.example.novelisland.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public AuthorDTO getAuthorByAuthorId(Long authorId) {
        Boolean token  = authorRepository.existsByAuthorId(authorId);

        if(token) {
            Author author = authorRepository.findByAuthorId(authorId);
            return author.toDTO();
        } else {
            throw new NotExistAuthorException(ErrorCode.NOT_EXIST_AUTHOR_TOKEN);
        }
    }

    @Transactional
    public AuthorDTO getAuthorByAuthorName(String authorName) {

        Boolean token = authorRepository.existsByAuthorName(authorName);

        if(token) {
            Author author = authorRepository.findByAuthorName(authorName);
            return author.toDTO();
        } else {
            throw new NotExistAuthorException(ErrorCode.NOT_EXIST_AUTHOR_TOKEN);
        }
    }

}

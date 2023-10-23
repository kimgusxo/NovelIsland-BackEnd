package com.example.novelisland.service;

import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.domain.Novel;
import com.example.novelisland.dto.NovelDTO;
import com.example.novelisland.dto.SearchDTO;
import com.example.novelisland.exception.novel.NotExistNovelException;
import com.example.novelisland.repository.NovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NovelService {

    private final NovelRepository novelRepository;

    @Autowired
    public NovelService(NovelRepository novelRepository) {
        this.novelRepository = novelRepository;
    }

    @Transactional
    public NovelDTO getNovelByNovelId(Long novelId) {

        Boolean token = novelRepository.existsByNovelId(novelId);

        if(token) {
            Novel novel = novelRepository.findByNovelId(novelId);
            return novel.toDTO();
        } else {
            // 해당하는 소설이 없을 때 예외처리
            throw new NotExistNovelException(ErrorCode.NOT_EXIST_NOVEL_TOKEN);
        }
    }

    @Transactional
    public List<NovelDTO> getNovelsByNovelName(String novelName, int page, int size) {
        // Paging 설정
        Pageable pageable = PageRequest.of(page, size);

        List<Novel> novelList = novelRepository.findByNovelNameContaining(novelName, pageable);

        List<NovelDTO> novelDTOList = new ArrayList<>();

        if(novelList.isEmpty()) {
            // 해당하는 소설이 없을 때 예외처리
            throw new NotExistNovelException(ErrorCode.NOT_EXIST_NOVEL_TOKEN);
        } else {
            for(Novel novel : novelList) {
                novelDTOList.add(novel.toDTO());
            }
            return novelDTOList;
        }
    }

    @Transactional
    public List<NovelDTO> getNovelsByNovelNameContainingAndTagIdList(SearchDTO searchDTO, int page, int size) {
        // paging 설정
        Pageable pageable = PageRequest.of(page, size);

        List<Novel> novelList = novelRepository.findByNovelNameContainingAndTagIdList(searchDTO.getNovelName(), searchDTO.getTagIdList(), pageable);

        List<NovelDTO> novelDTOList = new ArrayList<>();

        if(novelList.isEmpty()) {
            throw new NotExistNovelException(ErrorCode.NOT_EXIST_NOVEL_TOKEN);
        } else {
            for(Novel novel : novelList) {
                novelDTOList.add(novel.toDTO());
            }
            return novelDTOList;
        }
    }

}

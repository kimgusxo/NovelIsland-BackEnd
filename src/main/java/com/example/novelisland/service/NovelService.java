package com.example.novelisland.service;

import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.domain.Novel;
import com.example.novelisland.dto.NovelDTO;
import com.example.novelisland.exception.novel.NotExistNovelException;
import com.example.novelisland.repository.NovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public List<NovelDTO> getRandomNovels() {
        List<Novel> novelList = novelRepository.findThreeNovelsByRandom();

        List<NovelDTO> novelDTOList = new ArrayList<>();

        for(Novel novel : novelList) {
            novelDTOList.add(novel.toDTO());
        }

        return novelDTOList;
    }

    @Transactional
    public List<NovelDTO> getRankingNovels() {
        int page = 0;
        int size = 32;

        // Paging 설정
        Pageable pageable = PageRequest.of(page, size);

        Page<Novel> novelList = novelRepository.findAll(pageable);

        List<NovelDTO> novelDTOList = new ArrayList<>();

        for(Novel novel : novelList) {
            novelDTOList.add(novel.toDTO());
        }

        return novelDTOList;
    }

    @Transactional
    public List<NovelDTO> getSortingNovels() {
        int page = 0;
        int size = 32;

        // Paging 설정
        Sort sort = Sort.by(Sort.Order.asc("novelName"));
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Novel> novelList = novelRepository.findAll(pageable);

        List<NovelDTO> novelDTOList = new ArrayList<>();

        for(Novel novel : novelList) {
            novelDTOList.add(novel.toDTO());
        }

        return novelDTOList;
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
    public List<NovelDTO> getNovelsByNovelNameContainingAndTagIdList(String novelName, List<Long> tagIdList, int page, int size) {
        // paging 설정
        Pageable pageable = PageRequest.of(page, size);

        List<Novel> novelList = novelRepository.findByNovelNameContainingAndTagIdList(novelName, tagIdList, pageable);

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

package com.example.novelisland.controller;

import com.example.novelisland.format.Message;
import com.example.novelisland.service.NovelService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/novels")
public class NovelController {

    private final NovelService novelService;

    @GetMapping("/random")
    @ApiOperation(value = "랜덤 소설 데이터 받아오기", notes = "랜덤 소설을 가져온다.")
    public ResponseEntity<Message<?>> getRandomNovels() {
        log.info("[GET /novels/random] 랜덤 소설 조회 요청");

        return ResponseEntity.ok(
                Message.success(
                        "랜덤 소설 가져오기 완료",
                        novelService.getRandomNovels()
                )
        );
    }

    @GetMapping("/ranked")
    @ApiOperation(value = "인기순 소설 데이터 받아오기", notes = "소설을 인기순으로 가져온다.")
    public ResponseEntity<Message<?>> getRankingNovels(@RequestParam("page") Integer page,
                                                       @RequestParam("size") Integer size) {
        log.info("[GET /novels/ranked] 인기순 소설 조회 요청 - page={}, size={}", page, size);

        return ResponseEntity.ok(
                Message.success(
                        "인기순 소설 가져오기 완료",
                        novelService.getRankingNovels(page, size)
                )
        );
    }

    @GetMapping("/sorted")
    @ApiOperation(value = "정렬된 소설 데이터 받아오기", notes = "소설을 이름으로 정렬하여 가져온다.")
    public ResponseEntity<Message<?>> getSortingNovels(@RequestParam("page") Integer page,
                                                       @RequestParam("size") Integer size) {
        log.info("[GET /novels/sorted] 이름순 정렬된 소설 조회 요청 - page={}, size={}", page, size);

        return ResponseEntity.ok(
                Message.success(
                        "정렬된 소설 가져오기 완료",
                        novelService.getSortingNovels(page, size)
                )
        );
    }

    @GetMapping("/{novelId}")
    @ApiOperation(value = "아이디로 소설 검색", notes = "소설 아이디로 소설 1개를 검색한다.")
    public ResponseEntity<Message<?>> getNovelByNovelId(@PathVariable("novelId") Long novelId) {
        log.info("[GET /novels/{}] 소설 단건 조회 요청 - novelId={}", novelId, novelId);

        return ResponseEntity.ok(
                Message.success(
                        "소설 검색 완료",
                        novelService.getNovelByNovelId(novelId)
                )
        );
    }

    @GetMapping
    @ApiOperation(value = "아이디 리스트로 소설 목록 검색", notes = "소설 아이디 리스트로 소설 리스트를 검색한다.")
    public ResponseEntity<Message<?>> getNovelsByNovelIdList(@RequestParam("novelIdList") List<Long> novelIdList) {
        log.info("[GET /novels] 소설 아이디 리스트로 소설 목록 조회 요청 - novelIdList={}", novelIdList);

        return ResponseEntity.ok(
                Message.success(
                        "소설 검색 완료",
                        novelService.getNovelsByNovelIdList(novelIdList)
                )
        );
    }

    @GetMapping("/novelName")
    @ApiOperation(value = "이름으로 소설 목록 검색", notes = "소설 이름으로 소설 리스트를 검색한다.")
    public ResponseEntity<Message<?>> getNovelsByNovelName(@RequestParam("novelName") String novelName,
                                                           @RequestParam("page") Integer page,
                                                           @RequestParam("size") Integer size) {
        log.info("[GET /novels/novelName] 소설 이름으로 소설 목록 조회 요청 - novelName={}, page={}, size={}",
                novelName, page, size);

        return ResponseEntity.ok(
                Message.success(
                        "소설 검색 완료",
                        novelService.getNovelsByNovelName(novelName, page, size)
                )
        );
    }

    @GetMapping("/authorId")
    @ApiOperation(value = "작가 아이디로 작가의 소설 목록 검색", notes = "작가의 아이디로 작가의 소설 리스트를 검색한다.")
    public ResponseEntity<Message<?>> getNovelsByAuthorId(@RequestParam("authorId") Long authorId,
                                                          @RequestParam("page") Integer page,
                                                          @RequestParam("size") Integer size) {
        log.info("[GET /novels/authorId] 작가 아이디로 소설 목록 조회 요청 - authorId={}, page={}, size={}",
                authorId, page, size);

        return ResponseEntity.ok(
                Message.success(
                        "소설 검색 완료",
                        novelService.getNovelsByAuthorId(authorId, page, size)
                )
        );
    }

    @GetMapping("/novelName/and/tagId")
    @ApiOperation(value = "이름과 장르들로 소설 목록 검색", notes = "소설 이름과 태그 리스트로 소설 리스트를 검색한다.")
    public ResponseEntity<Message<?>> getNovelsByNovelNameContainingAndTagIdList(@RequestParam("novelName") String novelName,
                                                                              @RequestParam("tagIdList") List<Long> tagIdList,
                                                                              @RequestParam("page") Integer page,
                                                                              @RequestParam("size") Integer size) {
        log.info("[GET /novels/novelName/and/tagId] 이름+태그로 소설 목록 조회 요청 - novelName={}, tagIdList={}, page={}, size={}",
                novelName, tagIdList, page, size);

        return ResponseEntity.ok(
                Message.success(
                        "소설 검색 완료",
                        novelService.getNovelsByNovelNameContainingAndTagIdList(novelName, tagIdList, page, size)
                )
        );
    }
}

//package com.example.novelisland.repository;
//
//import com.example.novelisland.document.ElasticSearchNovel;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.elasticsearch.DataElasticsearchTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
//import org.testcontainers.elasticsearch.ElasticsearchContainer;
//import org.testcontainers.utility.DockerImageName;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Slf4j
//@DataElasticsearchTest
//public class ElasticSearchNovelRepositoryTest {
//
//    private static final ElasticsearchContainer container
//            = new ElasticsearchContainer(DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:7.17.0"))
//            .withExposedPorts(10000)
//            .withEnv("discovery.type","single-node");  // 포트 노출 설정 추가;
//
//    private final ElasticsearchOperations operations;
//    private final ElasticSearchNovelRepository elasticSearchNovelRepository;
//    private Pageable pageable;
//
//    @Autowired
//    public ElasticSearchNovelRepositoryTest(ElasticSearchNovelRepository elasticSearchNovelRepository,
//                                            ElasticsearchOperations operations) {
//        this.elasticSearchNovelRepository = elasticSearchNovelRepository;
//        this.operations = operations;
//    }
//
//    @BeforeAll
//    static void start() {
//        log.info("엘라스틱서치 가동");
//
//        container.start();
//
//        log.info("엘라스틱서치 가동 완료");
//
//    }
//
//    @BeforeEach
//    void setUp() {
//        log.info("엘라스틱서치 인덱스 매핑");
//
//        operations.indexOps(IndexCoordinates.of("novel")).delete();
//        operations.indexOps(IndexCoordinates.of("novel")).create();
//        operations.indexOps(IndexCoordinates.of("novel")).refresh();
//
//        log.info("엘라스틱서치 인덱스 매핑 완료");
//
//        log.info("테스트 데이터 생성");
//
//        ElasticSearchNovel elasticSearchNovel = ElasticSearchNovel.builder()
//                .novelId(1L)
//                .novelName("사피엔스")
//                .novelExplanation("인간이 왜 먹이사슬의 최상위 포식자가 되었는가의 대한 고찰")
//                .build();
//
//        elasticSearchNovelRepository.save(elasticSearchNovel);
//
//        log.info("테스트 데이터 생성 완료");
//
//        log.info("페이지네이션 설정");
//
//        pageable = PageRequest.of(0, 9);
//
//        log.info("페이지네이션 설정 완료");
//    }
//
//    @Test
//    @DisplayName("문장형 검색어로 검색어와 유사한 소설 검색 테스트 성공")
//    void testFindElasticNovelsByNovelExplanation() {
//        log.info("문장형 검색어로 검색어와 유사한 소설 검색 테스트 시작");
//
//        // given
//        String searchText = "인간";
//
//        // when
//        List<ElasticSearchNovel> elasticSearchNovelList
//                = elasticSearchNovelRepository
//                .findElasticNovelsByNovelExplanation(searchText, pageable);
//
//        // then
//        assertThat(elasticSearchNovelList)
//                .as("검색한 소설이 존재하지 않습니다.")
//                .isNotEmpty();
//
//        elasticSearchNovelList.forEach(elasticSearchNovel -> {
//            System.out.println(elasticSearchNovel.getNovelId());
//            System.out.println(elasticSearchNovel.getNovelName());
//            System.out.println(elasticSearchNovel.getNovelExplanation());
//        });
//
//        log.info("문장형 검색어로 검색어와 유사한 소설 검색 테스트 종료");
//    }
//
//    @AfterAll
//    static void tearDown() {
//        log.info("엘라스틱서치 종료");
//
//        container.stop();  // 이 줄 추가
//
//        log.info("엘라스틱서치 종료 완료");
//    }
//
//}

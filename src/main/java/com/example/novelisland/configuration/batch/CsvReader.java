package com.example.novelisland.configuration.batch;

import com.example.novelisland.domain.Novel;
import com.example.novelisland.vo.InsertDataVO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

@Configuration
@RequiredArgsConstructor
public class CsvReader {

    public FlatFileItemReader<InsertDataVO> csvFileItemReader() {
        FlatFileItemReader<InsertDataVO> flatFileItemReader
                = new FlatFileItemReader<>();

        // 읽어올 csv 파일의 경로
        flatFileItemReader.setResource(new PathResource("C:/Users/lenovo/Desktop/NovelsHobby_데이터수집/Test/작가수정버전_통합.csv"));
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setEncoding("UTF-8");

        DefaultLineMapper<InsertDataVO> defaultLineMapper = new DefaultLineMapper<>();

        // 다음 컬럼이어서 토크나이저는 ","
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(",");

        delimitedLineTokenizer.setNames("novelName", "authorName", "novelId",
                "novelThumbNail", "novelExplanation", "tagClassification");
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        BeanWrapperFieldSetMapper<InsertDataVO> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(InsertDataVO.class);

        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        // lineMapper 지정
        flatFileItemReader.setLineMapper(defaultLineMapper);

        return flatFileItemReader;
    }
}

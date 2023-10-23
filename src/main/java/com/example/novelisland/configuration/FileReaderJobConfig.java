package com.example.novelisland.configuration;

import com.example.novelisland.configuration.batch.CsvReader;
import com.example.novelisland.configuration.batch.CsvWriter;
import com.example.novelisland.vo.InsertDataVO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FileReaderJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CsvReader csvReader;
    private final CsvWriter csvWriter;

    private static final int chunkSize = 12172;

    @Bean
    public Job csvFileReaderJob() {
        return jobBuilderFactory.get("csvFileReaderJob")
                .start(csvFileReaderStep())
                .build();
    }

    @Bean
    public Step csvFileReaderStep() {
        CompositeItemWriter<InsertDataVO> compositeItemWriter = new CompositeItemWriter<>();
        compositeItemWriter.setDelegates(List.of(csvWriter.authorWriter(), csvWriter.tagWriter(), csvWriter.novelWriter()));

        return stepBuilderFactory.get("csvFileReaderStep")
                .<InsertDataVO, InsertDataVO>chunk(chunkSize)
                .reader(csvReader.csvFileItemReader())
                .writer(compositeItemWriter)
                .build();
    }
}

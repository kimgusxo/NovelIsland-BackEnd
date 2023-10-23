package com.example.novelisland;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class NovelIslandApplication {

	public static void main(String[] args) {
		SpringApplication.run(NovelIslandApplication.class, args);
	}

}

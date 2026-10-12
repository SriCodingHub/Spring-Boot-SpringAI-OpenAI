package com.springboot.SpringAI_OpenAI.controller;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public VectorStore vectorStore(EmbeddingModel embeddingModel) {
		return SimpleVectorStore.builder(embeddingModel).build();
	}

//	@Bean
//	public VectorStore vectorStore(JdbcTemplate jdbcTemplate, EmbeddingModel embeddingModel) {
//		return PgVectorStore.builder(jdbcTemplate, embeddingModel).dimensions(768).initializeSchema(true)
//				.removeExistingVectorStoreTable(true).build();
//	}

	
//	@Bean
//    public RedisClient redisClient() {
//        return RedisClient.create("redis://localhost:6379");
//    }
//
//    @Bean
//    public VectorStore vectorStore(
//            RedisClient redisClient,
//            EmbeddingModel embeddingModel) {
//
//        return RedisVectorStore.builder(redisClient, embeddingModel)
//                .indexName("product-index")
//                .initializeSchema(true)
//                .build();
//    }
}

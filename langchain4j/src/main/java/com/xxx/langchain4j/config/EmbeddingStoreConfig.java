package com.xxx.langchain4j.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddingStoreConfig {

    @Value("${spring.datasource.pgvector.username}")
    private String username;

    @Value("${spring.datasource.pgvector.password}")
    private String password;

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return PgVectorEmbeddingStore.builder()
                .table("xxx_embeddings")
                .database("xxx_picture")
                .port(5432)
                .host("localhost")
                .user(username)
                .password(password)
                .dimension(1024)
                .indexListSize(100)
                .useIndex(true)
                .createTable(true)
                .build();
    }
}
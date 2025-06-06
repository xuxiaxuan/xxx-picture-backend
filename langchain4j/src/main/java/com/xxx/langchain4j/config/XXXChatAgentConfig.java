package com.xxx.langchain4j.config;

import com.xxx.langchain4j.store.MongoChatMemoryStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

/**
 * @Description: // 类说明，在创建类时要填写
 * @ClassName: XXXChatAgentConfig    // 类名，会自动填充
 * @Author: 许夏轩          // 创建者
 * @Date: 04/06/2025 6:58 下午   // 时间
 * @Version: 1.0     // 版本
 */
@Configuration
public class XXXChatAgentConfig {
    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;


    @Bean
    public ChatMemoryProvider chatMemoryProviderXXX(){
        return memory-> MessageWindowChatMemory.builder()
                .id(memory)
                .chatMemoryStore(mongoChatMemoryStore)
                .maxMessages(20)
                .build();
    }

//    @Bean
//    ContentRetriever contentRetrieverXiaozhi() {
//        // 1. 加载知识文档（使用跨平台路径）
//        Document document= FileSystemDocumentLoader.loadDocument("E:\\code\\xxx_yp\\xxx-picture-backend\\langchain4j\\src\\main\\resources\\图片分类与标签建议指南.txt");
//        List<Document> documents = Arrays.asList(
//                document
//        );
//        // 2. 初始化内存向量存储
//        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
//        // 3. 自动分块并向量化存储
//        EmbeddingStoreIngestor.ingest(documents, embeddingStore);
//        // 4. 构建内容检索器
//        return EmbeddingStoreContentRetriever.from(embeddingStore);
//    }

    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private EmbeddingStore embeddingStore;

    @Bean
    ContentRetriever contentRetrieverXXX(){
        // 创建一个 EmbeddingStoreContentRetriever 对象，用于从嵌入存储中检索内容
        return EmbeddingStoreContentRetriever
                .builder()
                // 设置用于生成嵌入向量的嵌入模型
                .embeddingModel(embeddingModel)
                // 指定要使用的嵌入存储
                .embeddingStore(embeddingStore)
                // 设置最大检索结果数量，这里表示最多返回 3 条匹配结果
                .maxResults(3)
                // 设置最小得分阈值，只有得分大于等于 0.6 的结果才会被返回
                .minScore(0.6)
                // 构建最终的 EmbeddingStoreContentRetriever 实例
                .build();
    }

}

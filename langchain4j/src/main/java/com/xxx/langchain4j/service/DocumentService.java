package com.xxx.langchain4j.service;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private EmbeddingStoreIngestor embeddingStoreIngestor;

    public void ingestDocument(String filePath) {
        Document document = FileSystemDocumentLoader.loadDocument(filePath);
        processDocument(document);
    }

    public void ingestDocuments(List<String> filePaths) {
        filePaths.parallelStream().forEach(path -> {
            Document doc = FileSystemDocumentLoader.loadDocument(path);
            processDocument(doc);
        });
    }

    private void processDocument(Document document) {
        // 安全获取文件名并转换为String
        String fileName = document.metadata().getString("file_name");

        // 使用put添加元数据（而非add）
        document.metadata().put("source", fileName != null ? fileName : "unknown");
        document.metadata().put("timestamp", Instant.now().toString());

        embeddingStoreIngestor.ingest(document);
    }
}
package com.xxx.langchain4j.controller;

import com.xxx.langchain4j.assistant.XXXAgent;
import com.xxx.langchain4j.bean.ChatForm;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @Description: // 类说明，在创建类时要填写
 * @ClassName: AiController    // 类名，会自动填充
 * @Author: 许夏轩          // 创建者
 * @Date: 04/06/2025 7:05 下午   // 时间
 * @Version: 1.0     // 版本
 */
@Slf4j
@RestController
@RequestMapping(value = "/ai")
public class AiController
{

    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private EmbeddingStore<TextSegment> embeddingStore;

    @Autowired
    private XXXAgent xxxAgent;

    @PostMapping(value = "/xxx_agent",produces = "text/stream;charset=utf-8")
    public Flux<String> chat(@RequestBody ChatForm chatForm){
        return xxxAgent.chat(chatForm.getId(),chatForm.getMessage());
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadAndVectorizeFile(@RequestParam("file") MultipartFile file) {
        // 默认分块大小和重叠
        final int DEFAULT_CHUNK_SIZE = 200;
        final int DEFAULT_OVERLAP = 0;
        try {
            // 验证文件
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("文件不能为空");
            }
            // 读取文件内容
            String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
            // 创建文档并分割
            Document document = Document.from(fileContent);
            DocumentSplitter splitter = DocumentSplitters.recursive(DEFAULT_CHUNK_SIZE, DEFAULT_OVERLAP);
            List<TextSegment> segments = splitter.split(document);
            // 向量化并存储
            for (TextSegment segment : segments) {
                Embedding embedding = embeddingModel.embed(segment).content();
                embeddingStore.add(embedding, segment);
            }
            return ResponseEntity.ok("文件向量存储成功，共存储 " + segments.size() + " 个块");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("文件处理失败: " + e.getMessage());
        }
    }

}
